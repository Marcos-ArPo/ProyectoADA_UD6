package parking.proyectoada_ud6.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "servicios_vip")
public class ServicioVip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio")
    private Long idServicio;

    @Column(name = "nombre_serv", nullable = false)
    private String nombreServ;

    private String descr;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal precio;

    private Boolean activo = true;

    // Constructores
    public ServicioVip() {
    }

    public ServicioVip(String nombreServ, String descr, BigDecimal precio) {
        this.nombreServ = nombreServ;
        this.descr = descr;
        this.precio = precio;
    }

    // Getters y Setters
    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public String getNombreServ() {
        return nombreServ;
    }

    public void setNombreServ(String nombreServ) {
        this.nombreServ = nombreServ;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
