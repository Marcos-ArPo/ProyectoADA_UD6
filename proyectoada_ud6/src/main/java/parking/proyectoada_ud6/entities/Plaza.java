package parking.proyectoada_ud6.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "plazas")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idPlaza")
public class Plaza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plaza")
    private Long idPlaza;

    @Column(name = "numero_plaza", unique = true)
    private String numeroPlaza;

    @Enumerated(EnumType.STRING)
    private TipoPlaza tipo = TipoPlaza.NORMAL;

    @Enumerated(EnumType.STRING)
    private EstadoPlaza estado = EstadoPlaza.DISPONIBLE;

    private String planta;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @OneToMany(mappedBy = "plaza", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnoreProperties({ "plaza" })
    private List<Reserva> reservas = new ArrayList<>();

    // Enums
    public enum TipoPlaza {
        NORMAL, VIP, DISCAPACITADOS
    }

    public enum EstadoPlaza {
        DISPONIBLE, OCUPADA, RESERVADA, MANTENIMIENTO
    }

    // Constructores
    public Plaza() {
    }

    public Plaza(String numeroPlaza, TipoPlaza tipo, String planta) {
        this.numeroPlaza = numeroPlaza;
        this.tipo = tipo;
        this.planta = planta;
    }

    // Getters y Setters
    public Long getIdPlaza() {
        return idPlaza;
    }

    public void setIdPlaza(Long idPlaza) {
        this.idPlaza = idPlaza;
    }

    public String getNumeroPlaza() {
        return numeroPlaza;
    }

    public void setNumeroPlaza(String numeroPlaza) {
        this.numeroPlaza = numeroPlaza;
    }

    public TipoPlaza getTipo() {
        return tipo;
    }

    public void setTipo(TipoPlaza tipo) {
        this.tipo = tipo;
    }

    public EstadoPlaza getEstado() {
        return estado;
    }

    public void setEstado(EstadoPlaza estado) {
        this.estado = estado;
    }

    public String getPlanta() {
        return planta;
    }

    public void setPlanta(String planta) {
        this.planta = planta;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }
}
