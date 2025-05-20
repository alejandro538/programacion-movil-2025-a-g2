import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, tap, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators'; // Importar 'map' aquí
import { environment } from 'src/environments/environment';

// Interfaz para el usuario. Es buena práctica mantener las interfaces claras.
export interface Usuario {
  id: number;
  nombre: string;
  email: string;
  rol: 'USER' | 'ADMIN';
}

// Interfaz para la respuesta de login, si el backend devuelve un token
export interface LoginResponse {
  user: Usuario;
  token: string; // Asumiendo que el backend devuelve un token aquí
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = environment.apiUrl;
  private currentUser: Usuario | null = null;

  constructor(private http: HttpClient) {
    this.loadUserFromLocalStorage();
  }

  private loadUserFromLocalStorage(): void {
    const userString = localStorage.getItem('currentUser');
    if (userString) {
      try {
        this.currentUser = JSON.parse(userString) as Usuario;
      } catch (e) {
        console.error('AuthService: Error al parsear usuario desde localStorage:', e);
        localStorage.removeItem('currentUser');
      }
    }
  }

  register(usuario: { nombre: string; email: string; password: string }): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, usuario).pipe(
      catchError(this.handleError)
    );
  }

  /**
   * Realiza el login de un usuario.
   * Almacena el usuario y el token en localStorage tras un login exitoso.
   * @param email Email del usuario.
   * @param password Contraseña del usuario.
   * @returns Un Observable con el objeto Usuario.
   */
  login(email: string, password: string): Observable<Usuario> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, { email, password }).pipe(
      tap(response => {
        // Este tap se usa para efectos secundarios (guardar en localStorage)
        this.currentUser = response.user;
        localStorage.setItem('currentUser', JSON.stringify(response.user));
        localStorage.setItem('token', response.token);
      }),
      map(response => response.user), // <-- ¡Este es el cambio clave! Transforma LoginResponse a Usuario
      catchError(this.handleError)
    );
  }

  logout(): void {
    this.currentUser = null;
    localStorage.removeItem('currentUser');
    localStorage.removeItem('token');
  }

  getUsuario(): Usuario | null {
    return this.currentUser;
  }

  isLoggedIn(): boolean {
    return this.getUsuario() !== null && this.getToken() !== null;
  }

  esAdmin(): boolean {
    return this.getUsuario()?.rol === 'ADMIN';
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'Ha ocurrido un error desconocido.';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Error del cliente: ${error.error.message}`;
    } else {
      errorMessage = `Error del servidor: Código ${error.status}, mensaje: ${error.message}`;
      if (error.error && error.error.message) {
        errorMessage = `${error.status}: ${error.error.message}`;
      } else if (error.status === 0) {
        errorMessage = 'No se pudo conectar con el servidor. ¿Está el backend funcionando?';
      }
    }
    console.error(errorMessage);
    return throwError(() => new Error(errorMessage));
  }
}