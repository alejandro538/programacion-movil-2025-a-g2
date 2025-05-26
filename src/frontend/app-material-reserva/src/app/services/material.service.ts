import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

export interface Material {
  id: number;
  nombre: string;
  descripcion: string;
  cantidadDisponible: number;
}

export interface NuevoMaterial {
  nombre: string;
  descripcion: string;
  cantidadDisponible: number;
}

@Injectable({
  providedIn: 'root'
})
export class MaterialService {
  private apiUrl = 'https://w6x4dvhq-8080.use.devtunnels.ms/api/materiales';

  constructor(private http: HttpClient) {}

  getMateriales(): Observable<Material[]> {
    return this.http.get<Material[]>(this.apiUrl)
      .pipe(catchError(err => {
        console.error('Error al obtener materiales:', err);
        return throwError(() => err);
      }));
  }

  crearMaterial(material: NuevoMaterial): Observable<Material> {
    return this.http.post<Material>(this.apiUrl, material)
      .pipe(catchError(err => {
        console.error('Error al crear material:', err);
        return throwError(() => err);
      }));
  }

  actualizarMaterial(id: number, material: Material): Observable<Material> {
    return this.http.put<Material>(`${this.apiUrl}/${id}`, material)
      .pipe(catchError(err => {
        console.error('Error al actualizar material:', err);
        return throwError(() => err);
      }));
  }

  eliminarMaterial(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`)
      .pipe(catchError(err => {
        console.error('Error al eliminar material:', err);
        return throwError(() => err);
      }));
  }
}
