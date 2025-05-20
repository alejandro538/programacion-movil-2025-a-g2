package com.corhuila.proyecto_fd_pm.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.corhuila.proyecto_fd_pm.models.Reserva;

public interface IReservaRepository  extends JpaRepository<Reserva, Long> {
    List<Reserva> findByUsuarioId(Long usuarioId);

}
