
package pe.edu.cibertec.reservaservice.service;

import pe.edu.cibertec.reservaservice.dto.ReservaRequest;
import pe.edu.cibertec.reservaservice.model.Reserva;

import java.util.List;

public interface ReservaService {

    // 1. Listar todas las reservas
    List<Reserva> listar();

    // 2. Buscar reserva por ID
    Reserva buscarPorId(Long id);

    // 3. Registrar una nueva reserva
    Reserva registrar(ReservaRequest request);

    // 4. Cancelar una reserva
    Reserva cancelar(Long id);

    // 5. Eliminar una reserva
    void eliminar(Long id);
}
