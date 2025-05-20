package com.corhuila.proyecto_fd_pm.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.corhuila.proyecto_fd_pm.models.Material;

@Repository
public interface IMaterialRepository extends JpaRepository<Material, Long> {
    
    // Aquí puedes agregar consultas personalizadas si las necesitas

    // Ejemplo: Buscar materiales por nombre
    Material findByNombre(String nombre);

    // Ejemplo: Buscar materiales por cantidad disponible
    List<Material> findByCantidadDisponibleGreaterThanEqual(int cantidad);
}
