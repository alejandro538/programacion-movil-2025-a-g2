package com.corhuila.proyecto_fd_pm.controller; // Paquete del controlador

import com.corhuila.proyecto_fd_pm.Dto.ReservaDTO; // Importa el DTO de Reserva
import com.corhuila.proyecto_fd_pm.Service.IReservaService; // Importa la interfaz del servicio de reservas
import com.corhuila.proyecto_fd_pm.models.EstadoReserva; // Importa el enum EstadoReserva
import com.corhuila.proyecto_fd_pm.models.Reserva; // Importa la entidad Reserva

import org.springframework.beans.factory.annotation.Autowired; // Importa Autowired para inyección de dependencias
import org.springframework.http.ResponseEntity; // Para manejar respuestas HTTP
import org.springframework.http.HttpStatus; // Para establecer códigos de estado HTTP
import org.springframework.web.bind.annotation.*; // Para anotaciones REST

import java.util.List; // Lista para manejar múltiples reservas
import java.util.Map; // Mapa para obtener campos dinámicos como el estado

@RestController // Define esta clase como un controlador REST
@RequestMapping("/api/reservas") // Establece la ruta base del endpoint
//@CrossOrigin(origins = "http://localhost:4200") // Permite peticiones CORS desde localhost:4200
public class ReservaController { // Clase del controlador de reservas

    @Autowired // Inyección de dependencias del servicio de reservas
    private IReservaService reservaService; // Servicio que contiene la lógica de negocio

    @GetMapping // GET /api/reservas
    public ResponseEntity<List<ReservaDTO>> getAllReservas() { // Método para obtener todas las reservas
        List<ReservaDTO> reservas = reservaService.getAllReservas(); // Llama al servicio
        return ResponseEntity.ok(reservas); // Devuelve la lista de reservas con estado 200
    }

    @PostMapping // POST /api/reservas
    public ResponseEntity<?> createReserva(@RequestBody ReservaDTO reservaDTO) { // Método para crear una nueva reserva
        try {
            ReservaDTO nuevaReserva = reservaService.createReserva(reservaDTO); // Llama al servicio para crear
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaReserva); // Devuelve 201 y la reserva creada
        } catch (IllegalArgumentException e) { // Maneja errores de validación
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear reserva: " + e.getMessage());
        } catch (SecurityException e) { // Maneja errores de autorización
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/usuario/{usuarioId}") // PUT /api/reservas/{id}/usuario/{usuarioId}
    public ResponseEntity<?> updateReserva(@PathVariable Long id, @PathVariable Long usuarioId, @RequestBody ReservaDTO reservaDTO) {
        try {
            ReservaDTO reservaActualizada = reservaService.updateReserva(id, usuarioId, reservaDTO); // Llama al servicio para actualizar
            return ResponseEntity.ok(reservaActualizada); // Devuelve la reserva actualizada
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reserva no encontrada: " + e.getMessage()); // Error 404 si no existe
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado: " + e.getMessage()); // Error 403 si no tiene permiso
        }
    }

    @DeleteMapping("/{id}/usuario/{usuarioId}") // DELETE /api/reservas/{id}/usuario/{usuarioId}
    public ResponseEntity<?> deleteReserva(@PathVariable Long id, @PathVariable Long usuarioId) {
        try {
            reservaService.deleteReserva(id, usuarioId); // Llama al servicio para eliminar
            return ResponseEntity.noContent().build(); // Devuelve 204 sin contenido
        } catch (IllegalArgumentException | SecurityException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al eliminar reserva: " + e.getMessage()); // Devuelve mensaje de error
        }
    }

    @GetMapping("/usuario/{usuarioId}") // GET /api/reservas/usuario/{usuarioId}
    public ResponseEntity<List<ReservaDTO>> getReservasByUsuario(@PathVariable Long usuarioId) {
        List<ReservaDTO> reservas = reservaService.getReservasByUsuarioId(usuarioId); // Obtiene reservas por ID de usuario
        return ResponseEntity.ok(reservas); // Devuelve la lista de reservas del usuario
    }

    @GetMapping("/estado/{estado}") // GET /api/reservas/estado/{estado}
    public ResponseEntity<?> getReservasByEstado(@PathVariable String estado) {
        try {
            EstadoReserva.valueOf(estado.toUpperCase()); // Valida que el estado sea válido
            List<ReservaDTO> reservas = reservaService.getReservasByEstado(estado); // Obtiene las reservas por estado
            return ResponseEntity.ok(reservas); // Devuelve la lista de reservas
        } catch (IllegalArgumentException e) { // Si el estado no es válido
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Estado inválido: " + estado); // Devuelve 400
        }
    }

    @PutMapping("/{id}/estado") // PUT /api/reservas/{id}/estado
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Reserva reserva = reservaService.getReservaById(id); // Busca la reserva por ID
        if (reserva == null) { // Si no existe
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reserva no encontrada."); // Devuelve 404
        }

        String nuevoEstado = body.get("estado"); // Obtiene el nuevo estado del body
        if (nuevoEstado == null || nuevoEstado.trim().isEmpty()) { // Si el campo estado está vacío
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El campo 'estado' es requerido."); // Devuelve 400
        }

        try {
            EstadoReserva estado = EstadoReserva.valueOf(nuevoEstado.toUpperCase()); // Convierte string a enum
            reserva.setEstado(estado); // Asigna nuevo estado
            Reserva actualizado = reservaService.saveReserva(reserva); // Guarda la reserva actualizada
            return ResponseEntity.ok(new ReservaDTO(actualizado)); // Devuelve la reserva en formato DTO
        } catch (IllegalArgumentException e) { // Si el estado no es válido
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Estado inválido: " + nuevoEstado); // Devuelve 400
        }
    }
}
