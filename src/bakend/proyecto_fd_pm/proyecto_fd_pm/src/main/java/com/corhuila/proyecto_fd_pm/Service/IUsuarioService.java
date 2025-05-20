package com.corhuila.proyecto_fd_pm.Service;

import java.util.List;
import java.util.Optional;

import com.corhuila.proyecto_fd_pm.models.Usuario;

public interface IUsuarioService {

    // Obtener todos los usuarios
    List<Usuario> getAllUsuarios();

    // Obtener un usuario por ID
    Optional<Usuario> getUsuarioById(Long id);

    // Guardar un nuevo usuario
    Usuario saveUsuario(Usuario usuario);

    // Actualizar un usuario existente
    Usuario updateUsuario(Long id, Usuario usuarioDetails);

    // Eliminar un usuario por ID
    void deleteUsuario(Long id);

}
