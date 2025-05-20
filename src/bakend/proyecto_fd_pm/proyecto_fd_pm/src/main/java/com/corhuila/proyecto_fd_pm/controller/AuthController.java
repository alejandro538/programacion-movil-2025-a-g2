package com.corhuila.proyecto_fd_pm.controller; // Paquete donde se encuentra el controlador

import com.corhuila.proyecto_fd_pm.models.Usuario; // Importa el modelo Usuario
import com.corhuila.proyecto_fd_pm.models.Rol; // Importa el enum Rol
import com.corhuila.proyecto_fd_pm.Dto.LoginDTO; // Importa el DTO para login
import com.corhuila.proyecto_fd_pm.Dto.RegistroDTO; // Importa el DTO para registro
import com.corhuila.proyecto_fd_pm.Repository.IUsuarioRepository; // Importa el repositorio de usuarios

import org.springframework.beans.factory.annotation.Autowired; // Importa la anotación Autowired
import org.springframework.http.ResponseEntity; // Importa ResponseEntity para respuestas HTTP
import org.springframework.web.bind.annotation.*; // Importa las anotaciones del controlador

@RestController // Marca la clase como un controlador REST
@RequestMapping("/api/auth") // Ruta base para este controlador
@CrossOrigin(origins = "*") // Permite peticiones desde cualquier origen
public class AuthController { // Define la clase AuthController

    @Autowired // Inyección de dependencia para el repositorio de usuarios
    private IUsuarioRepository usuarioRepository; // Repositorio para acceder a los usuarios

    @PostMapping("/register") // Define la ruta POST /api/auth/register
    public ResponseEntity<?> register(@RequestBody RegistroDTO dto) { // Método para registrar usuario
        if (usuarioRepository.findByEmail(dto.getEmail()) != null) { // Verifica si el email ya existe
            return ResponseEntity.badRequest().body("Email ya registrado"); // Devuelve error si ya existe
        }

        Usuario usuario = new Usuario(); // Crea un nuevo objeto Usuario
        usuario.setNombre(dto.getNombre()); // Asigna el nombre del DTO al usuario
        usuario.setEmail(dto.getEmail()); // Asigna el email del DTO al usuario
        usuario.setPassword(dto.getPassword()); // Asigna la contraseña SIN encriptar
        usuario.setRol(Rol.USER); // Asigna el rol USER por defecto

        usuarioRepository.save(usuario); // Guarda el usuario en la base de datos

        return ResponseEntity.ok("Usuario registrado correctamente"); // Devuelve respuesta exitosa
    }

    @PostMapping("/login") // Define la ruta POST /api/auth/login
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) { // Método para login de usuario
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail()); // Busca el usuario por email

        if (usuario == null || !usuario.getPassword().equals(dto.getPassword())) { // Verifica credenciales
            return ResponseEntity.status(401).body("Credenciales inválidas"); // Devuelve error si no coinciden
        }

        // Devuelve el usuario sin token
        return ResponseEntity.ok(usuario); // Devuelve el usuario si las credenciales son válidas
    }
}
