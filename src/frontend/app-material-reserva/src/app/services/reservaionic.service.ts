import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

export type EstadoReserva = 'PENDIENTE' | 'APROBADA' | 'CANCELADA' | 'FINALIZADA';

export interface ReservaDTO {
  id?: number;
  fechaInicio: string;
  fechaFin: string;
  estado: EstadoReserva;
  usuarioNombre: string;
  materialNombre: string;
}

export interface ReservaInput {
  fechaInicio: string;
  fechaFin: string;
  estado: EstadoReserva;
  usuarioId: number;
  materialId: number;
}

@Injectable({
  providedIn: 'root'
})
export class ReservaService {
  private apiUrl = 'https://w6x4dvhq-8080.use.devtunnels.ms/api/reservas';

  constructor(private http: HttpClient) {}

  getTodasLasReservas(): Observable<ReservaDTO[]> {
    return this.http.get<ReservaDTO[]>(this.apiUrl)
      .pipe(catchError(err => {
        console.error('Error al obtener reservas:', err);
        return throwError(() => err);
      }));
  }

  getReservasPorUsuario(usuarioId: number): Observable<ReservaDTO[]> {
    return this.http.get<ReservaDTO[]>(`${this.apiUrl}/usuario/${usuarioId}`)
      .pipe(catchError(err => {
        console.error('Error al obtener reservas por usuario:', err);
        return throwError(() => err);
      }));
  }

  crearReserva(reserva: ReservaInput): Observable<ReservaDTO> {
    return this.http.post<ReservaDTO>(this.apiUrl, reserva)
      .pipe(catchError(err => {
        console.error('Error al crear reserva:', err);
        return throwError(() => err);
      }));
  }

  actualizarEstadoReserva(id: number, estado: EstadoReserva): Observable<ReservaDTO> {
    const body = { estado };
    return this.http.put<ReservaDTO>(`${this.apiUrl}/${id}/estado`, body)
      .pipe(catchError(err => {
        console.error('Error al actualizar estado de reserva:', err);
        return throwError(() => err);
      }));
  }
}
