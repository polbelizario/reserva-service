
package pe.edu.cibertec.reservaservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ViajeResponse(
        Long id,
        String origen,
        String destino,
        LocalDateTime fechaSalida,
        BigDecimal precio,
        Integer capacidad,
        String estado
) {
}
