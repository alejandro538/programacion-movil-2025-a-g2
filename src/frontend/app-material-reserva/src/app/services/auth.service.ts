import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from 'src/environments/environment';

export interface Usuario {
  id: number;
  nombre: string;
  email: string;
  rol: 'USER' | 'ADMIN';
  password?: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = environment.apiUrl;
  private usuario: Usuario | null = null;

  constructor(private http: HttpClient) {}

  register(usuario: { nombre: string; email: string; password: string }): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, usuario);
  }

  login(email: string, password: string): Observable<Usuario> {
    return this.http.post<Usuario>(`${this.apiUrl}/login`, { email, password }).pipe(
      tap(user => {
        this.usuario = user;
        localStorage.setItem('usuario', JSON.stringify(user));
      })
    );
  }

  logout(): void {
    this.usuario = null;
    localStorage.removeItem('usuario');
    localStorage.removeItem('token');
  }

  getUsuario(): Usuario | null {
    if (!this.usuario) {
      const userString = localStorage.getItem('usuario');
      if (userString) {
        try {
          this.usuario = JSON.parse(userString) as Usuario;
        } catch (e) {
          console.error('Error al parsear usuario desde localStorage:', e);
        }
      }
    }
    return this.usuario;
  }

  isLoggedIn(): boolean {
    return this.getUsuario() !== null;
  }

  esAdmin(): boolean {
    return this.getUsuario()?.rol === 'ADMIN';
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }
}
