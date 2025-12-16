package main.java.parking.proyectoada_ud6.entities;

import com.fasterxml.jackson.annotation.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "reserva_servicio", uniqueConstraints = @UniqueConstraint(columnNames = { "id_reserva", "id_servicio" }))
public class ReservaServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva_servicio")
    private Long idReservaServicio;

    @NotNull(message = "La reserva es obligatoria")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reserva", nullable = false)
    @JsonBackReference("reserva-servicios")
    private Reserva reserva;

    @NotNull(message = "El servicio es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_servicio", nullable = false)
    @JsonIgnoreProperties({ "reservas" })
    private ServicioVip servicio;

    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    @Column(nullable = false)
    private Integer cantidad = 1;

    @DecimalMin(value = "0.0", inclusive = false, message = "El subtotal debe ser mayor que 0")
    @Column(precision = 10, scale = 2)
    private BigDecimal subtotal;

    // Constructores
    public ReservaServicio() {
    }

    public ReservaServicio(Reserva reserva, ServicioVip servicio, Integer cantidad) {
        this.reserva = reserva;
        this.servicio = servicio;
        this.cantidad = cantidad;
        calcularSubtotal();
    }

    // Getters y Setters
    public Long getIdReservaServicio() {
        return idReservaServicio;
    }

    public void setIdReservaServicio(Long idReservaServicio) {
        this.idReservaServicio = idReservaServicio;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
        calcularSubtotal();
    }

    public ServicioVip getServicio() {
        return servicio;
    }

    public void setServicio(ServicioVip servicio) {
        this.servicio = servicio;
        calcularSubtotal();
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
        calcularSubtotal();
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    // Método para calcular subtotal
    public void calcularSubtotal() {
        if (servicio != null && cantidad != null) {
            this.subtotal = servicio.getPrecio().multiply(new BigDecimal(cantidad));
        }
    }

    // Método toString
    @Override
    public String toString() {
        return "ReservaServicio{" +
                "idReservaServicio=" + idReservaServicio +
                ", servicio=" + (servicio != null ? servicio.getNombreServ() : "null") +
                ", cantidad=" + cantidad +
                ", subtotal=" + subtotal +
                '}';
    }
}