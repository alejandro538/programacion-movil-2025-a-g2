package com.corhuila.proyecto_fd_pm.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.corhuila.proyecto_fd_pm.models.Usuario;

public interface IUsuarioRepository  extends JpaRepository<Usuario, Long> {
    Usuario findByEmail (String email);
    
}
