package com.corhuila.proyecto_fd_pm.controller; // Paquete del controlador

import com.corhuila.proyecto_fd_pm.models.Material; // Importa el modelo Material
import com.corhuila.proyecto_fd_pm.Dto.MaterialDTO; // Importa el DTO de Material
import com.corhuila.proyecto_fd_pm.Service.IMaterialService; // Importa la interfaz del servicio de Material

import org.springframework.beans.factory.annotation.Autowired; // Importa Autowired para inyección de dependencias
import org.springframework.http.ResponseEntity; // Importa ResponseEntity para respuestas HTTP
import org.springframework.web.bind.annotation.*; // Importa las anotaciones REST

import java.util.List; // Importa lista
import java.util.Optional; // Importa Optional para manejo de nulos
import java.util.stream.Collectors; // Importa Collectors para convertir listas

@RestController // Marca la clase como un controlador REST
@RequestMapping("/api/materiales") // Ruta base del controlador
//@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8100"}) // Permite solicitudes desde estas URLs
public class MaterialController { // Define la clase MaterialController

    @Autowired // Inyección del servicio de Material
    private IMaterialService materialService; // Servicio que maneja lógica de negocio de Material

    // Obtener todos los materiales
    @GetMapping // Ruta GET /api/materiales
    public List<MaterialDTO> getAllMateriales() { // Método para obtener todos los materiales
        return materialService.getAllMateriales() // Llama al servicio para obtener materiales
                .stream() // Convierte la lista a stream
                .map(m -> new MaterialDTO(m.getId(), m.getNombre(), m.getDescripcion(), m.getCantidadDisponible())) // Mapea cada Material a un MaterialDTO
                .collect(Collectors.toList()); // Convierte el stream a lista
    }

    // Obtener material por ID
    @GetMapping("/{id}") // Ruta GET /api/materiales/{id}
    public ResponseEntity<MaterialDTO> getMaterialById(@PathVariable Long id) { // Método para obtener un material por ID
        return materialService.getMaterialById(id) // Llama al servicio para buscar por ID
                .map(m -> ResponseEntity.ok(new MaterialDTO(m.getId(), m.getNombre(), m.getDescripcion(), m.getCantidadDisponible()))) // Si se encuentra, devuelve DTO
                .orElse(ResponseEntity.notFound().build()); // Si no se encuentra, devuelve 404
    }

    // Crear un nuevo material
    @PostMapping // Ruta POST /api/materiales
    public MaterialDTO createMaterial(@RequestBody Material material) { // Método para crear un material
        Material saved = materialService.saveMaterial(material); // Guarda el material usando el servicio
        return new MaterialDTO(saved.getId(), saved.getNombre(), saved.getDescripcion(), saved.getCantidadDisponible()); // Devuelve el DTO del material guardado
    }

    // Actualizar un material existente
    @PutMapping("/{id}") // Ruta PUT /api/materiales/{id}
    public ResponseEntity<MaterialDTO> updateMaterial(@PathVariable Long id, @RequestBody Material material) { // Método para actualizar un material
        Optional<Material> existingOpt = materialService.getMaterialById(id); // Busca el material existente
        if (existingOpt.isPresent()) { // Si existe el material
            Material existing = existingOpt.get(); // Obtiene el material
            existing.setNombre(material.getNombre()); // Actualiza el nombre
            existing.setDescripcion(material.getDescripcion()); // Actualiza la descripción
            existing.setCantidadDisponible(material.getCantidadDisponible()); // Actualiza la cantidad disponible

            Material updated = materialService.updateMaterial(id, existing); // Guarda los cambios
            MaterialDTO dto = new MaterialDTO(updated.getId(), updated.getNombre(), updated.getDescripcion(), updated.getCantidadDisponible()); // Crea el DTO actualizado
            return ResponseEntity.ok(dto); // Devuelve el material actualizado
        } else {
            return ResponseEntity.notFound().build(); // Si no existe, devuelve 404
        }
    }

    // Eliminar material por ID
    @DeleteMapping("/{id}") // Ruta DELETE /api/materiales/{id}
    public ResponseEntity<Void> deleteMaterial(@PathVariable Long id) { // Método para eliminar un material
        materialService.deleteMaterial(id); // Llama al servicio para eliminar
        return ResponseEntity.noContent().build(); // Devuelve 204 sin contenido
    }
}
