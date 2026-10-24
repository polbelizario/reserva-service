
package pe.edu.cibertec.reservaservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import pe.edu.cibertec.reservaservice.dto.ViajeResponse;

@FeignClient(name = "viaje-service")
public interface ViajeClient {

    // Consultar un viaje por su ID
    @GetMapping("/api/viajes/{id}")
    ViajeResponse buscarPorId(@PathVariable("id") Long id);

}
