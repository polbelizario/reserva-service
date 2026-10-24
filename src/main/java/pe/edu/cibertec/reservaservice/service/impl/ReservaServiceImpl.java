package pe.edu.cibertec.reservaservice.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import pe.edu.cibertec.reservaservice.client.PasajeroClient;
import pe.edu.cibertec.reservaservice.client.ViajeClient;
import pe.edu.cibertec.reservaservice.dto.ReservaRequest;
import pe.edu.cibertec.reservaservice.dto.ViajeResponse;
import pe.edu.cibertec.reservaservice.model.Reserva;
import pe.edu.cibertec.reservaservice.repository.ReservaRepository;
import pe.edu.cibertec.reservaservice.service.ReservaService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository repository;
    private final PasajeroClient pasajeroClient;
    private final ViajeClient viajeClient;

    // Constructor
    public ReservaServiceImpl(
            ReservaRepository repository,
            PasajeroClient pasajeroClient,
            ViajeClient viajeClient) {

        this.repository = repository;
        this.pasajeroClient = pasajeroClient;
        this.viajeClient = viajeClient;
    }

    // 1. Listar todas las reservas
    @Override
    public List<Reserva> listar() {
        return repository.findAll();
    }

    // 2. Buscar reserva por ID
    @Override
    public Reserva buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Reserva no encontrada"
                        ));
    }

    // 3. Registrar una nueva reserva
    @Override
    @Transactional
    public Reserva registrar(ReservaRequest request) {

        // Validar cantidad de asientos
        if (request.cantidadAsientos() == null
                || request.cantidadAsientos() <= 0) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Debe reservar al menos un asiento"
            );
        }

        // Verificar que el pasajero exista
        pasajeroClient.buscarPorId(request.pasajeroId());

        // Obtener los datos del viaje
        ViajeResponse viaje =
                viajeClient.buscarPorId(request.viajeId());

        if (viaje == null || viaje.capacidad() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No se pudo obtener la capacidad del viaje"
            );
        }

        // Verificar el estado del viaje
        if (!"PROGRAMADO".equals(viaje.estado())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Solo se permiten reservas en viajes programados"
            );
        }

        // Verificar que la fecha de salida sea futura
        if (viaje.fechaSalida() == null
                || !viaje.fechaSalida().isAfter(LocalDateTime.now())) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No se puede reservar un viaje cuya salida ya pasó"
            );
        }

        // Calcular asientos ocupados
        Long ocupados =
                repository.contarAsientosReservados(
                        request.viajeId()
                );

        // Calcular asientos disponibles
        long disponibles =
                (long) viaje.capacidad() - ocupados;

        // Validar disponibilidad
        if (request.cantidadAsientos() > disponibles) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Asientos insuficientes. Disponibles: "
                            + Math.max(0, disponibles)
            );
        }

        // Crear la reserva
        Reserva reserva = new Reserva();

        reserva.setPasajeroId(request.pasajeroId());
        reserva.setViajeId(request.viajeId());
        reserva.setCantidadAsientos(
                request.cantidadAsientos()
        );
        reserva.setEstado("CONFIRMADA");

        return repository.save(reserva);
    }

    // 4. Cancelar una reserva
    @Override
    @Transactional
    public Reserva cancelar(Long id) {

        Reserva reserva = buscarPorId(id);

        if ("CANCELADA".equals(reserva.getEstado())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La reserva ya está cancelada"
            );
        }

        reserva.setEstado("CANCELADA");

        return repository.save(reserva);
    }

    // 5. Eliminar una reserva
    @Override
    @Transactional
    public void eliminar(Long id) {

        Reserva reserva = buscarPorId(id);

        repository.delete(reserva);
    }
}
