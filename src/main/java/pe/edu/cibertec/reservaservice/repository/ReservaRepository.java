
package pe.edu.cibertec.reservaservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.edu.cibertec.reservaservice.model.Reserva;

public interface ReservaRepository
        extends JpaRepository<Reserva, Long> {

    // Sumar los asientos de las reservas confirmadas
    @Query("""
            SELECT COALESCE(SUM(r.cantidadAsientos), 0)
            FROM Reserva r
            WHERE r.viajeId = :viajeId
            AND r.estado = 'CONFIRMADA'
            """)
    Long contarAsientosReservados(
            @Param("viajeId") Long viajeId
    );
}
