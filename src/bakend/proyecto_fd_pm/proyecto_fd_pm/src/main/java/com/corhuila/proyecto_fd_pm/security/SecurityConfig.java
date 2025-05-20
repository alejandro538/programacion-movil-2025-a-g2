package com.corhuila.proyecto_fd_pm.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@Configuration  // Indica que esta clase contiene configuraciones de Spring
public class SecurityConfig {

    @Bean  // Declara este método como un bean que Spring debe gestionar
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 🔒 Desactiva la protección CSRF (Cross-Site Request Forgery)
            // Útil para APIs REST que no usan sesiones o tokens CSRF
            .csrf(csrf -> csrf.disable())

            // 🌐 Configura CORS (Cross-Origin Resource Sharing) para permitir peticiones desde el frontend
            .cors(cors -> cors.configurationSource(request -> {
                CorsConfiguration config = new CorsConfiguration();
                // Permite solicitudes solo desde el origen del frontend en desarrollo (Ionic/Angular)
                config.setAllowedOrigins(Arrays.asList("http://localhost:8100"));
                // Métodos HTTP permitidos
                config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                // Permite cualquier header en la petición
                config.setAllowedHeaders(Arrays.asList("*"));
                return config;
            }))

            // 🔓 Permite el acceso a todas las rutas sin autenticación
            // Esto solo debe usarse en desarrollo; en producción restringir el acceso
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        // Construye y retorna la configuración de seguridad definida
        return http.build();
    }
}
