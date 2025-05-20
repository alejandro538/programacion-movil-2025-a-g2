package com.corhuila.proyecto_fd_pm.Dto;

import com.corhuila.proyecto_fd_pm.models.Rol;        // Importa el enum Rol
import com.corhuila.proyecto_fd_pm.models.Usuario;    // Importa la entidad Usuario
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;  // Para ignorar propiedades desconocidas en JSON

@JsonIgnoreProperties(ignoreUnknown = true)  // Ignora propiedades extras en JSON para evitar errores al deserializar
public class UsuarioDTO {

    private Long id;        // ID del usuario
    private String nombre;  // Nombre del usuario
    private String email;   // Email del usuario
    private Rol rol;        // Rol del usuario (enum)

    // Constructor vacío requerido por Jackson para serialización/deserialización automática
    public UsuarioDTO() {}

    // Constructor completo para crear el DTO con todos los datos
    public UsuarioDTO(Long id, String nombre, String email, Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
    }

    // Constructor que recibe la entidad Usuario y extrae los datos para llenar el DTO
    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nombre = usuario.getNombre();
        this.email = usuario.getEmail();
        this.rol = usuario.getRol();
    }

    // Getters y setters para cada atributo privado

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
}
