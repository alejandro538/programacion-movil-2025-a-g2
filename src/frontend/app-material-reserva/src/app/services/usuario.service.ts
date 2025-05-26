import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Usuario } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private apiUrl = 'https://w6x4dvhq-8080.use.devtunnels.ms/api/usuarios';

  constructor(private http: HttpClient) {}

  // 👇 Este es el método que necesitas
  getTodos(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(this.apiUrl);
  }
actualizarRolUsuario(id: number, rol: 'USER' | 'ADMIN'): Observable<Usuario> {
  return this.http.put<Usuario>(
    `${this.apiUrl}/admin/usuarios/${id}/rol`,
    { rol: rol }, // Aquí debe llamarse "rol", no "name"
    { headers: { 'Content-Type': 'application/json' } }
  );
}


  }
