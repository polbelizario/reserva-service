
package pe.edu.cibertec.reservaservice.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long pasajeroId;

    @Column(nullable = false)
    private Long viajeId;

    @Column(nullable = false)
    private Integer cantidadAsientos;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private LocalDateTime fechaReserva;

    @PrePersist
    public void antesDeGuardar() {
        if (fechaReserva == null) {
            fechaReserva = LocalDateTime.now();
        }
        if (estado == null) {
            estado = "CONFIRMADA";
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPasajeroId() {
        return pasajeroId;
    }

    public void setPasajeroId(Long pasajeroId) {
        this.pasajeroId = pasajeroId;
    }

    public Long getViajeId() {
        return viajeId;
    }

    public void setViajeId(Long viajeId) {
        this.viajeId = viajeId;
    }

    public Integer getCantidadAsientos() {
        return cantidadAsientos;
    }

    public void setCantidadAsientos(Integer cantidadAsientos) {
        this.cantidadAsientos = cantidadAsientos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDateTime fechaReserva) {
        this.fechaReserva = fechaReserva;
    }
}
