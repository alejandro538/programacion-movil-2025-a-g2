package com.corhuila.proyecto_fd_pm.Service;

import com.corhuila.proyecto_fd_pm.Dto.ReservaDTO;
import com.corhuila.proyecto_fd_pm.models.Reserva;

import java.util.List;

public interface IReservaService {

    // Devuelve una lista de todas las reservas en forma de DTO para facilitar la transferencia
    List<ReservaDTO> getAllReservas();

    // Crea una nueva reserva a partir de un DTO, puede lanzar excepciones si hay errores o violaciones de seguridad
    ReservaDTO createReserva(ReservaDTO reservaDTO) throws IllegalArgumentException, SecurityException;

    // Actualiza una reserva existente identificada por reservaId y usuarioId con los datos del DTO
    // Lanza excepciones en caso de error o si el usuario no tiene permisos
    ReservaDTO updateReserva(Long reservaId, Long usuarioId, ReservaDTO reservaDTO) throws IllegalArgumentException, SecurityException;

    // Elimina una reserva especificada por reservaId y usuarioId, con control de errores y seguridad
    void deleteReserva(Long reservaId, Long usuarioId) throws IllegalArgumentException, SecurityException;

    // Obtiene la lista de reservas filtrando por el ID del usuario propietario
    List<ReservaDTO> getReservasByUsuarioId(Long usuarioId);

    // Obtiene la lista de reservas filtrando por el estado (ej: PENDIENTE, APROBADA)
    List<ReservaDTO> getReservasByEstado(String estado);

    // Métodos auxiliares para convertir entre entidad Reserva y su DTO
    ReservaDTO convertToDTO(Reserva reserva);
    Reserva convertToEntity(ReservaDTO reservaDTO);

    // Obtiene una reserva por su ID, útil para búsquedas o validaciones internas
    Reserva getReservaById(Long id);

    // Guarda una reserva (entidad), puede ser usada internamente para persistencia
    Reserva saveReserva(Reserva reserva);

}
