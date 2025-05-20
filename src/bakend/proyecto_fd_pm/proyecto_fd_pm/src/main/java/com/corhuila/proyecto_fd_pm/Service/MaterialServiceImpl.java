package com.corhuila.proyecto_fd_pm.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.corhuila.proyecto_fd_pm.models.Material;
import com.corhuila.proyecto_fd_pm.Repository.IMaterialRepository;

@Service
public class MaterialServiceImpl implements IMaterialService {

    @Autowired
    private IMaterialRepository materialRepository;

    // Devuelve todos los materiales almacenados en la base de datos
    @Override
    public List<Material> getAllMateriales() {
        return materialRepository.findAll();
    }

    // Busca un material por su ID y devuelve un Optional para manejar la posible ausencia
    @Override
    public Optional<Material> getMaterialById(Long id) {
        return materialRepository.findById(id);
    }

    // Guarda un nuevo material o actualiza uno existente en la base de datos
    @Override
    public Material saveMaterial(Material material) {
        return materialRepository.save(material);
    }

    // Actualiza un material existente si se encuentra por ID, de lo contrario lanza excepción
    @Override
    public Material updateMaterial(Long id, Material materialDetails) {
        Optional<Material> optionalMaterial = materialRepository.findById(id);

        if (optionalMaterial.isPresent()) {
            Material material = optionalMaterial.get();
            material.setNombre(materialDetails.getNombre());
            material.setDescripcion(materialDetails.getDescripcion());
            material.setCantidadDisponible(materialDetails.getCantidadDisponible());
            return materialRepository.save(material);
        } else {
            throw new IllegalArgumentException("Material no encontrado con id: " + id);
        }
    }

    // Elimina un material por su ID, sin importar si existe o no (puede lanzar excepción si no existe)
    @Override
    public void deleteMaterial(Long id) {
        materialRepository.deleteById(id);
    }
}
