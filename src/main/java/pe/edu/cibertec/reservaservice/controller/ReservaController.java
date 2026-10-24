
package pe.edu.cibertec.reservaservice.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import pe.edu.cibertec.reservaservice.dto.ReservaRequest;
import pe.edu.cibertec.reservaservice.model.Reserva;
import pe.edu.cibertec.reservaservice.service.ReservaService;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService service;

    // Constructor
    public ReservaController(ReservaService service) {
        this.service = service;
    }

    // 1. Listar todas las reservas
    @GetMapping
    public List<Reserva> listar() {
        return service.listar();
    }

    // 2. Buscar reserva por ID
    @GetMapping("/{id}")
    public Reserva buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // 3. Registrar una nueva reserva
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reserva registrar(
            @Valid @RequestBody ReservaRequest request) {

        return service.registrar(request);
    }

    // 4. Cancelar una reserva sin eliminarla
    @PatchMapping("/{id}/cancelar")
    public Reserva cancelar(@PathVariable Long id) {
        return service.cancelar(id);
    }

    // 5. Eliminar una reserva definitivamente
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
