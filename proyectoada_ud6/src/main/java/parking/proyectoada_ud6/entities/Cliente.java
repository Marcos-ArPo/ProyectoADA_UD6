package parking.proyectoada_ud6.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long idCliente;

    private String nombre;

    private String apellidos;

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
    @JsonIgnore
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
