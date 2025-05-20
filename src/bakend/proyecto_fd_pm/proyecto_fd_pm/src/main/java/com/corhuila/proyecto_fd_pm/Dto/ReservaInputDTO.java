package com.corhuila.proyecto_fd_pm.Dto;  // Paquete del DTO

import com.corhuila.proyecto_fd_pm.models.EstadoReserva;  // Enum para estado de reserva
import com.corhuila.proyecto_fd_pm.models.Reserva;       // Entidad Reserva
import java.time.LocalDateTime;                           // Fecha y hora

public class ReservaInputDTO {

    private Long id;                      // ID de la reserva
    private LocalDateTime fechaInicio;   // Fecha/hora inicio reserva
    private LocalDateTime fechaFin;      // Fecha/hora fin reserva
    private EstadoReserva estado;        // Estado de la reserva

    private UsuarioDTO usuario;          // DTO con datos del usuario asociado a la reserva
    private MaterialDTO material;        // DTO con datos del material reservado

    // Constructor vacío (necesario para frameworks que serializan/deserializan objetos)
    public ReservaInputDTO() {
    }

    // Constructor que convierte una entidad Reserva a este DTO
    public ReservaInputDTO(Reserva reserva) {
        this.id = reserva.getId();                      // Asigna id desde entidad
        this.fechaInicio = reserva.getFechaInicio();   // Asigna fecha de inicio
        this.fechaFin = reserva.getFechaFin();         // Asigna fecha de fin
        this.estado = reserva.getEstado();              // Asigna estado

        if (reserva.getUsuario() != null) {             // Si la reserva tiene usuario
            this.usuario = new UsuarioDTO(reserva.getUsuario());  // Crea UsuarioDTO a partir de entidad Usuario
        }

        if (reserva.getMaterial() != null) {            // Si la reserva tiene material
            this.material = new MaterialDTO(reserva.getMaterial());  // Crea MaterialDTO a partir de entidad Material
        }
    }

    // Constructor completo, útil para crear un DTO con todos los datos manualmente
    public ReservaInputDTO(Long id, LocalDateTime fechaInicio, LocalDateTime fechaFin, EstadoReserva estado,
                      UsuarioDTO usuario, MaterialDTO material) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.usuario = usuario;
        this.material = material;
    }

    // Getters y setters para cada campo privado

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDateTime getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDateTime fechaFin) { this.fechaFin = fechaFin; }

    public EstadoReserva getEstado() { return estado; }
    public void setEstado(EstadoReserva estado) { this.estado = estado; }

    public UsuarioDTO getUsuario() { return usuario; }
    public void setUsuario(UsuarioDTO usuario) { this.usuario = usuario; }

    public MaterialDTO getMaterial() { return material; }
    public void setMaterial(MaterialDTO material) { this.material = material; }
}
