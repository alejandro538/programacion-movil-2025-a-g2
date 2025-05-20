package com.corhuila.proyecto_fd_pm.Dto;  // Define el paquete donde está esta clase DTO

public class RegistroDTO {
    private String nombre;     // Campo para el nombre del usuario que se registra
    private String email;      // Campo para el email del usuario
    private String password;   // Campo para la contraseña del usuario

    // Getter para obtener el nombre
    public String getNombre() {
        return nombre;
    }
    // Setter para asignar el nombre
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    // Getter para obtener el email
    public String getEmail() {
        return email;
    }
    // Setter para asignar el email
    public void setEmail(String email) {
        this.email = email;
    }
    // Getter para obtener la contraseña
    public String getPassword() {
        return password;
    }
    // Setter para asignar la contraseña
    public void setPassword(String password) {
        this.password = password;
    }
}
