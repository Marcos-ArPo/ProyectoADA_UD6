package main.java.parking.proyectoada_ud6.entities;

import com.fasterxml.jackson.annotation.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reservas")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idReserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private Long idReserva;

    @NotNull(message = "La fecha es obligatoria")
    @Column(name = "fecha_res")
    private LocalDate fechaRes;

    @NotNull(message = "La hora de inicio es obligatoria")
    @Column(name = "hora_ini")
    private LocalTime horaIni;

    @NotNull(message = "La hora de fin es obligatoria")
    @Column(name = "hora_fin")
    private LocalTime horaFin;

    @Column(name = "es_vip")
    private Boolean esVip = false;

    @Enumerated(EnumType.STRING)
    private EstadoReserva estado = EstadoReserva.ACTIVA;

    @Column(precision = 10, scale = 2)
    private BigDecimal total;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    @JsonBackReference("cliente-reservas")
    @JsonIgnoreProperties({ "reservas" })
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_plaza")
    @JsonIgnoreProperties({ "reservas" })
    private Plaza plaza;

    // CAMBIO IMPORTANTE: Usar ReservaServicio en lugar de relación ManyToMany
    // directa
    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("reserva-servicios")
    private List<ReservaServicio> serviciosContratados = new ArrayList<>();

    // Enums
    public enum EstadoReserva {
        ACTIVA, CANCELADA, COMPLETADA
    }

    // Constructores
    public Reserva() {
    }

    public Reserva(LocalDate fechaRes, LocalTime horaIni, LocalTime horaFin, Cliente cliente, Plaza plaza) {
        this.fechaRes = fechaRes;
        this.horaIni = horaIni;
        this.horaFin = horaFin;
        this.cliente = cliente;
        this.plaza = plaza;
        this.esVip = plaza.getTipo() == Plaza.TipoPlaza.VIP;
    }

    // Getters y Setters
    public Long getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Long idReserva) {
        this.idReserva = idReserva;
    }

    public LocalDate getFechaRes() {
        return fechaRes;
    }

    public void setFechaRes(LocalDate fechaRes) {
        this.fechaRes = fechaRes;
    }

    public LocalTime getHoraIni() {
        return horaIni;
    }

    public void setHoraIni(LocalTime horaIni) {
        this.horaIni = horaIni;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public Boolean getEsVip() {
        return esVip;
    }

    public void setEsVip(Boolean esVip) {
        this.esVip = esVip;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Plaza getPlaza() {
        return plaza;
    }

    public void setPlaza(Plaza plaza) {
        this.plaza = plaza;
    }

    public List<ReservaServicio> getServiciosContratados() {
        return serviciosContratados;
    }

    public void setServiciosContratados(List<ReservaServicio> serviciosContratados) {
        this.serviciosContratados = serviciosContratados;
    }

    public void agregarServicio(ServicioVip servicio, Integer cantidad) {
        ReservaServicio reservaServicio = new ReservaServicio(this, servicio, cantidad);
        this.serviciosContratados.add(reservaServicio);
    }

    public void calcularTotal() {
        BigDecimal totalCalculado = BigDecimal.ZERO;

        long horas = java.time.Duration.between(horaIni, horaFin).toHours();
        BigDecimal tarifaHora = esVip ? new BigDecimal("8.00") : new BigDecimal("5.00");
        totalCalculado = totalCalculado.add(tarifaHora.multiply(new BigDecimal(horas)));

        if (serviciosContratados != null && !serviciosContratados.isEmpty()) {
            for (ReservaServicio reservaServicio : serviciosContratados) {
                reservaServicio.calcularSubtotal();
                totalCalculado = totalCalculado.add(reservaServicio.getSubtotal());
            }
        }

        this.total = totalCalculado;
    }

    @JsonIgnore
    public List<ServicioVip> getServicios() {
        List<ServicioVip> servicios = new ArrayList<>();
        if (serviciosContratados != null) {
            for (ReservaServicio rs : serviciosContratados) {
                servicios.add(rs.getServicio());
            }
        }
        return servicios;
    }
}
