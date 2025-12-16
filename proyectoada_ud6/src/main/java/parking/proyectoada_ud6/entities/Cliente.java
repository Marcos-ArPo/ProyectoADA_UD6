package main.java.parking.proyectoada_ud6.entities;

import com.fasterxml.jackson.annotation.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idCliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long idCliente;

    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String nombre;

    @Size(max = 200, message = "Los apellidos no pueden tener más de 200 caracteres")
    private String apellidos;

    @Size(max = 10, message = "La matrícula no puede tener más de 10 caracteres")
    @Column(unique = true)
    private String matricula;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cliente")
    private TipoCliente tipoCliente = TipoCliente.NORMAL;

    @Column(name = "cuota_pagada")
    private Boolean cuotaPagada = false;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnoreProperties({ "cliente" })
    private List<Reserva> reservas = new ArrayList<>();

    // Enums
    public enum TipoCliente {
        NORMAL, VIP
    }

    // Constructores
    public Cliente() {
    }

    public Cliente(String nombre, String apellidos, String matricula, TipoCliente tipoCliente) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.matricula = matricula;
        this.tipoCliente = tipoCliente;
    }

    // Getters y Setters
    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public Boolean getCuotaPagada() {
        return cuotaPagada;
    }

    public void setCuotaPagada(Boolean cuotaPagada) {
        this.cuotaPagada = cuotaPagada;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }
}
