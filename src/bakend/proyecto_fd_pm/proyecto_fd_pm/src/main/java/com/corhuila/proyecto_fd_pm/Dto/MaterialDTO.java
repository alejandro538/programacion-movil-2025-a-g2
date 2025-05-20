package com.corhuila.proyecto_fd_pm.Dto;  // Paquete donde se encuentra la clase DTO

import com.corhuila.proyecto_fd_pm.models.Material; // Importa la entidad Material para usar en el constructor

public class MaterialDTO {
    private Long id;                     // ID único del material
    private String nombre;               // Nombre del material
    private String descripcion;          // Descripción del material
    private int cantidadDisponible;     // Cantidad disponible del material

    // Constructor vacío (necesario para frameworks que crean instancias por reflexión)
    public MaterialDTO() {}

    // Constructor con todos los campos para crear un objeto DTO directamente con valores
    public MaterialDTO(Long id, String nombre, String descripcion, int cantidadDisponible) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidadDisponible = cantidadDisponible;
    }

    // Constructor que crea un DTO a partir de una entidad Material (mapeo de entidad a DTO)
    public MaterialDTO(Material material) {
        this.id = material.getId();                          // Copia el ID desde la entidad
        this.nombre = material.getNombre();                  // Copia el nombre desde la entidad
        this.descripcion = material.getDescripcion();        // Copia la descripción desde la entidad
        this.cantidadDisponible = material.getCantidadDisponible();  // Copia la cantidad disponible
    }

    // Getter para obtener el id
    public Long getId() { return id; }
    // Setter para asignar el id
    public void setId(Long id) { this.id = id; }

    // Getter para obtener el nombre
    public String getNombre() { return nombre; }
    // Setter para asignar el nombre
    public void setNombre(String nombre) { this.nombre = nombre; }

    // Getter para obtener la descripción
    public String getDescripcion() { return descripcion; }
    // Setter para asignar la descripción
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    // Getter para obtener la cantidad disponible
    public int getCantidadDisponible() { return cantidadDisponible; }
    // Setter para asignar la cantidad disponible
    public void setCantidadDisponible(int cantidadDisponible) { this.cantidadDisponible = cantidadDisponible; }
}
