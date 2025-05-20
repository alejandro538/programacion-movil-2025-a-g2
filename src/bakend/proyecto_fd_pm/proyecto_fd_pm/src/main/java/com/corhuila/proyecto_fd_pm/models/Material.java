package com.corhuila.proyecto_fd_pm.models;

import jakarta.persistence.*;
import java.util.List;

@Entity  // Indica que esta clase es una entidad JPA y se mapeará a una tabla en la base de datos
@Table(name = "materiales")  // Define el nombre de la tabla en la base de datos
public class Material {

    @Id  // Marca este campo como la clave primaria de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // La base de datos genera automáticamente el valor del id (auto-incremental)
    private Long id;

    @Column(nullable = false)  // Esta columna no puede ser nula en la tabla
    private String nombre;

    @Column  // Columna opcional para descripción del material
    private String descripcion;

    @Column(nullable = false)  // Columna no nula que guarda la cantidad disponible del material
    private int cantidadDisponible;

    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL)  
    // Relación uno a muchos: un material puede tener muchas reservas.
    // "mappedBy" indica que la relación está mapeada por el campo "material" en la entidad Reserva.
    // Cascade ALL significa que las operaciones en Material se propagan a sus reservas (ejemplo: eliminar un material elimina sus reservas).
    private List<Reserva> reservas;

    // Getters and setters para acceso y modificación de los campos privados
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getCantidadDisponible() { return cantidadDisponible; }

    public void setCantidadDisponible(int cantidadDisponible) { this.cantidadDisponible = cantidadDisponible; }

    public List<Reserva> getReservas() { return reservas; }

    public void setReservas(List<Reserva> reservas) { this.reservas = reservas; }
}
