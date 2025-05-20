package com.corhuila.proyecto_fd_pm.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.corhuila.proyecto_fd_pm.models.Usuario;
import com.corhuila.proyecto_fd_pm.Repository.IUsuarioRepository;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    // Obtiene todos los usuarios
    @Override
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    // Busca un usuario por su ID, devuelve Optional
    @Override
    public Optional<Usuario> getUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }

    // Guarda un usuario (crear)
    @Override
    public Usuario saveUsuario(Usuario usuario) {
        // Aquí podrías encriptar la contraseña antes de guardar si implementas seguridad
        return usuarioRepository.save(usuario);
    }

    // Actualiza un usuario existente
    @Override
    public Usuario updateUsuario(Long id, Usuario usuarioDetails) {
        Optional<Usuario> existingUsuarioOpt = usuarioRepository.findById(id);
        if (existingUsuarioOpt.isPresent()) {
            Usuario existingUsuario = existingUsuarioOpt.get();
            existingUsuario.setNombre(usuarioDetails.getNombre());
            existingUsuario.setEmail(usuarioDetails.getEmail());
            // Aquí puedes agregar lógica para encriptar contraseña si la actualizas
            return usuarioRepository.save(existingUsuario);
        } else {
            throw new IllegalArgumentException("Usuario no encontrado con id: " + id);
        }
    }

    // Elimina un usuario por su ID
    @Override
    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

}
