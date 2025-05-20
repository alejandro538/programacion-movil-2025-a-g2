package com.corhuila.proyecto_fd_pm.Dto; // Define el paquete donde está esta clase DTO

// Clase LoginDTO: objeto para transferir datos de login (email y contraseña)
public class LoginDTO {
    
    private String email;    // Campo para almacenar el email del usuario
    private String password; // Campo para almacenar la contraseña del usuario
    
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
