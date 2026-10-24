
package pe.edu.cibertec.reservaservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ReservaRequest(

        @NotNull(message = "El pasajero es obligatorio")
        Long pasajeroId,

        @NotNull(message = "El viaje es obligatorio")
        Long viajeId,

        @NotNull(message = "La cantidad de asientos es obligatoria")
        @Min(value = 1, message = "Debe reservar al menos un asiento")
        Integer cantidadAsientos

) {
}
