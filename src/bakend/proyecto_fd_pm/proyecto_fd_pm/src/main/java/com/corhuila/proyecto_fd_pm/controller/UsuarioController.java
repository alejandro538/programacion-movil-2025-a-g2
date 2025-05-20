package com.corhuila.proyecto_fd_pm.controller; // Define el paquete donde está esta clase

import com.corhuila.proyecto_fd_pm.Dto.ReservaDTO; // Importa el DTO para reservas (datos simplificados)
import com.corhuila.proyecto_fd_pm.Dto.UsuarioDTO; // Importa el DTO para usuarios
import com.corhuila.proyecto_fd_pm.Service.IReservaService; // Servicio para lógica de reservas
import com.corhuila.proyecto_fd_pm.Service.IUsuarioService; // Servicio para lógica de usuarios

import com.corhuila.proyecto_fd_pm.models.Rol; // Importa el enum Rol (USER, ADMIN, etc.)
import com.corhuila.proyecto_fd_pm.models.Usuario; // Importa la entidad Usuario

import org.springframework.beans.factory.annotation.Autowired; // Para inyectar dependencias
import org.springframework.http.ResponseEntity; // Para construir respuestas HTTP
import org.springframework.web.bind.annotation.*; // Para anotaciones REST

import java.util.List; // Para listas
import java.util.Optional; // Para manejo seguro de nulos
import java.util.stream.Collectors; // Para transformar listas con streams

@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/api/usuarios") // Ruta base para todos los endpoints de usuarios
@CrossOrigin(origins = "http://localhost:4200") // Permite llamadas desde frontend Angular en localhost:4200
public class UsuarioController {

    @Autowired // Inyecta automáticamente el servicio de usuario
    private IUsuarioService usuarioService;

    @Autowired // Inyecta automáticamente el servicio de reserva
    private IReservaService reservaService;

    @GetMapping // Maneja GET /api/usuarios, devuelve lista de usuarios
    public List<UsuarioDTO> getAllUsuarios() {
        return usuarioService.getAllUsuarios() // Obtiene todos los usuarios desde el servicio
                .stream() // Convierte la lista a stream para procesarla
                .map(u -> new UsuarioDTO(u.getId(), u.getNombre(), u.getEmail(), u.getRol())) // Convierte cada Usuario a UsuarioDTO
                .collect(Collectors.toList()); // Recoge el resultado en una lista y la retorna
    }

    @GetMapping("/{id}") // Maneja GET /api/usuarios/{id}, busca usuario por id
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable Long id) {
        return usuarioService.getUsuarioById(id) // Busca usuario en el servicio por id
                .map(u -> ResponseEntity.ok(new UsuarioDTO(u.getId(), u.getNombre(), u.getEmail(), u.getRol()))) // Si existe, devuelve 200 con DTO
                .orElse(ResponseEntity.notFound().build()); // Si no existe, devuelve 404 Not Found
    }

    @PostMapping // Maneja POST /api/usuarios, crea un nuevo usuario
    public UsuarioDTO createUsuario(@RequestBody Usuario usuario) {
        usuario.setRol(Rol.USER); // Asigna el rol USER por defecto al nuevo usuario
        Usuario saved = usuarioService.saveUsuario(usuario); // Guarda el usuario en la base de datos
        return new UsuarioDTO(saved.getId(), saved.getNombre(), saved.getEmail(), saved.getRol()); // Retorna DTO con los datos guardados
    }

    @PutMapping("/{id}") // Maneja PUT /api/usuarios/{id}, actualiza usuario existente
    public ResponseEntity<UsuarioDTO> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        Optional<Usuario> existingOpt = usuarioService.getUsuarioById(id); // Busca usuario por id
        if (existingOpt.isPresent()) { // Si usuario existe
            Usuario existing = existingOpt.get(); // Obtiene el usuario existente
            existing.setNombre(usuario.getNombre()); // Actualiza nombre
            existing.setEmail(usuario.getEmail()); // Actualiza email
            existing.setRol(usuario.getRol()); // Actualiza rol (opcional permitir cambio)

            Usuario updated = usuarioService.saveUsuario(existing); // Guarda los cambios
            UsuarioDTO dto = new UsuarioDTO(updated.getId(), updated.getNombre(), updated.getEmail(), updated.getRol()); // Crea DTO actualizado

            return ResponseEntity.ok(dto); // Devuelve 200 OK con usuario actualizado
        } else {
            return ResponseEntity.notFound().build(); // Si no existe usuario, devuelve 404
        }
    }

    @DeleteMapping("/{id}") // Maneja DELETE /api/usuarios/{id}, elimina usuario
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id); // Llama al servicio para eliminar usuario por id
        return ResponseEntity.noContent().build(); // Devuelve 204 No Content indicando éxito sin cuerpo
    }

    // ADMIN: Cambiar rol de usuario
    @PutMapping("/admin/usuarios/{id}/rol") // Maneja PUT para cambiar rol, ruta especial de admin
    public ResponseEntity<Usuario> actualizarRolUsuario(@PathVariable Long id, @RequestBody Rol nuevoRol) {
        Usuario usuario = usuarioService.getUsuarioById(id) // Busca usuario por id
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado")); // Si no existe lanza excepción
        usuario.setRol(nuevoRol); // Cambia el rol al nuevo rol recibido
        Usuario actualizado = usuarioService.saveUsuario(usuario); // Guarda el usuario actualizado
        return ResponseEntity.ok(actualizado); // Devuelve 200 OK con usuario actualizado
    }

    // Obtener reservas por usuario
    @GetMapping("/{id}/reservas") // Maneja GET /api/usuarios/{id}/reservas, para obtener reservas del usuario
    public ResponseEntity<List<ReservaDTO>> getReservasPorUsuario(@PathVariable Long id) {
        List<ReservaDTO> dtoList = reservaService.getReservasByUsuarioId(id); // Obtiene lista de reservas en DTOs por id usuario
        return ResponseEntity.ok(dtoList); // Devuelve 200 OK con lista de reservas
    }
}
