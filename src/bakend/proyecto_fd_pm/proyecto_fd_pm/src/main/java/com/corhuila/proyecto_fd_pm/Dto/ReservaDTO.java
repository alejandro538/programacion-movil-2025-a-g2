package com.corhuila.proyecto_fd_pm.Dto;  // Paquete donde está el DTO

import com.corhuila.proyecto_fd_pm.models.EstadoReserva;  // Importa enum para estado de reserva
import com.corhuila.proyecto_fd_pm.models.Reserva;       // Importa la entidad Reserva

import java.time.LocalDateTime;  // Importa clase para fecha y hora

public class ReservaDTO {

    private Long id;                     // ID único de la reserva
    private LocalDateTime fechaInicio;   // Fecha y hora de inicio de la reserva
    private LocalDateTime fechaFin;      // Fecha y hora de fin de la reserva
    private EstadoReserva estado;        // Estado actual de la reserva (pendiente, aprobada, etc.)

    private Long usuarioId;              // ID del usuario que hizo la reserva
    private String nombreUsuario;        // Nombre del usuario (para mostrar fácilmente)

    private Long materialId;             // ID del material reservado
    private String nombreMaterial;       // Nombre del material reservado

    // Constructor completo con todos los campos
    public ReservaDTO(Long id, LocalDateTime fechaInicio, LocalDateTime fechaFin, EstadoReserva estado,
                      Long usuarioId, String nombreUsuario, Long materialId, String nombreMaterial) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.usuarioId = usuarioId;
        this.nombreUsuario = nombreUsuario;
        this.materialId = materialId;
        this.nombreMaterial = nombreMaterial;
    }

    // Constructor que crea un DTO desde la entidad Reserva (protege contra valores null)
    public ReservaDTO(Reserva reserva) {
        this.id = reserva.getId();
        this.fechaInicio = reserva.getFechaInicio();
        this.fechaFin = reserva.getFechaFin();
        this.estado = reserva.getEstado();

        if (reserva.getUsuario() != null) {              // Si la reserva tiene usuario
            this.usuarioId = reserva.getUsuario().getId();
            this.nombreUsuario = reserva.getUsuario().getNombre();
        } else {                                        // Si no tiene usuario, asigna null
            this.usuarioId = null;
            this.nombreUsuario = null;
        }

        if (reserva.getMaterial() != null) {             // Si la reserva tiene material
            this.materialId = reserva.getMaterial().getId();
            this.nombreMaterial = reserva.getMaterial().getNombre();
        } else {                                        // Si no tiene material, asigna null
            this.materialId = null;
            this.nombreMaterial = null;
        }
    }

    // Constructor vacío necesario para frameworks y serialización/deserialización
    public ReservaDTO() {
    }

    // Getters y setters para acceder y modificar los campos privados

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }
    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }
    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public EstadoReserva getEstado() {
        return estado;
    }
    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }
    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Long getMaterialId() {
        return materialId;
    }
    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getNombreMaterial() {
        return nombreMaterial;
    }
    public void setNombreMaterial(String nombreMaterial) {
        this.nombreMaterial = nombreMaterial;
    }
}
