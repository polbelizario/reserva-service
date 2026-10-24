
package pe.edu.cibertec.reservaservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "pasajero-service")
public interface PasajeroClient {

    @GetMapping("/api/pasajeros/{id}")
    Object buscarPorId(@PathVariable("id") Long id);
}
