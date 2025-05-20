package com.corhuila.proyecto_fd_pm.Service;

import java.util.List;
import java.util.Optional;

import com.corhuila.proyecto_fd_pm.models.Material;

public interface IMaterialService {

    // Obtiene la lista de todos los materiales registrados en la base de datos
    List<Material> getAllMateriales();
    
    // Busca un material por su ID y devuelve un Optional (puede estar vacío si no existe)
    Optional<Material> getMaterialById(Long id);
    
    // Guarda un nuevo material en la base de datos y devuelve el material guardado (con id generado)
    Material saveMaterial(Material material);
    
    // Actualiza un material existente, recibe el id del material y los nuevos detalles
    // Devuelve el material actualizado
    Material updateMaterial(Long id, Material materialDetails);
    
    // Elimina un material por su ID
    void deleteMaterial(Long id);
}
