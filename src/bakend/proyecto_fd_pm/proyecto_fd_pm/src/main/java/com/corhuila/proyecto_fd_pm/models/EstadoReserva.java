package com.corhuila.proyecto_fd_pm.models;

// Enum que representa los diferentes estados que puede tener una reserva
public enum EstadoReserva {
    PENDIENTE,   // La reserva está pendiente de aprobación o procesamiento
    APROBADA,    // La reserva ha sido aprobada y está activa
    CANCELADA,   // La reserva fue cancelada y no se llevará a cabo
    FINALIZADA   // La reserva ya ha finalizado (uso concluido)
}
