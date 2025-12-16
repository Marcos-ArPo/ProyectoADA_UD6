package main.java.parking.proyectoada_ud6.entities;

import com.fasterxml.jackson.annotation.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "servicios_vip")
public class ServicioVip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio")
    private Long idServicio;

    @NotBlank(message = "El nombre del servicio es obligatorio")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    @Column(name = "nombre_serv")
    private String nombreServ;

    @Size(max = 500, message = "La descripción no puede tener más de 500 caracteres")
    private String descr;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    @Column(precision = 10, scale = 2)
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
