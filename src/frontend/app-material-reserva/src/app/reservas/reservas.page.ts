import { Component, OnInit } from '@angular/core';
import { ReservaDTO, ReservaInput, ReservaService, EstadoReserva } from '../services/reserva.service';
import { Material, MaterialService } from '../services/material.service';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-reservas',
  templateUrl: './reservas.page.html',
  styleUrls: ['./reservas.page.scss'],
  standalone: false
})
export class ReservasPage implements OnInit {

  reservas: ReservaDTO[] = [];
  materiales: Material[] = [];
  rolUsuario: string = '';

  nuevaReserva: ReservaInput = {
    fechaInicio: '',
    fechaFin: '',
    estado: 'PENDIENTE',
    usuarioId: 0,
    materialId: 0
  };

  constructor(
    private reservaService: ReservaService,
    private materialService: MaterialService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const usuario = this.authService.getUsuario();
    if (!usuario) {
      console.error('No hay usuario autenticado');
      return;
    }

    this.nuevaReserva.usuarioId = usuario.id;
    this.rolUsuario = usuario.rol;

    this.cargarReservas(usuario.id);
    this.cargarMateriales();
  }

  cargarReservas(usuarioId: number): void {
    this.reservaService.getReservasPorUsuario(usuarioId).subscribe({
      next: data => this.reservas = data,
      error: err => {
        console.error('Error al cargar reservas:', err);
        alert('Error al cargar reservas.');
      }
    });
  }

  cargarMateriales(): void {
    this.materialService.getMateriales().subscribe({
      next: data => this.materiales = data,
      error: err => {
        console.error('Error al cargar materiales:', err);
        alert('Error al cargar materiales.');
      }
    });
  }

  guardarReserva(): void {
    if (!this.nuevaReserva.fechaInicio || !this.nuevaReserva.fechaFin) {
      alert('Debe ingresar fecha de inicio y fecha fin.');
      return;
    }

    if (!this.nuevaReserva.materialId || this.nuevaReserva.materialId <= 0) {
      alert('Debe seleccionar un material válido.');
      return;
    }

    this.nuevaReserva.fechaInicio = this.formatearFecha(this.nuevaReserva.fechaInicio);
    this.nuevaReserva.fechaFin = this.formatearFecha(this.nuevaReserva.fechaFin);

    this.reservaService.crearReserva(this.nuevaReserva).subscribe({
      next: () => {
        alert('Reserva guardada con éxito');
        this.resetearFormulario();
        this.cargarReservas(this.nuevaReserva.usuarioId);
      },
      error: err => {
        console.error('Error al guardar reserva:', err);
        alert('Error al guardar la reserva. Intente nuevamente.');
      }
    });
  }

  actualizarEstado(reserva: ReservaDTO, nuevoEstado: EstadoReserva): void {
    if (!reserva.id) {
      alert('Reserva inválida, falta id');
      return;
    }

    this.reservaService.actualizarEstadoReserva(reserva.id, nuevoEstado).subscribe({
      next: () => {
        alert(`Estado actualizado a ${nuevoEstado}`);
        const usuario = this.authService.getUsuario();
        if (usuario) this.cargarReservas(usuario.id);
      },
      error: err => {
        console.error('Error al actualizar estado:', err);
        alert('No se pudo actualizar el estado');
      }
    });
  }

  private resetearFormulario(): void {
    this.nuevaReserva = {
      fechaInicio: '',
      fechaFin: '',
      estado: 'PENDIENTE',
      usuarioId: this.nuevaReserva.usuarioId,
      materialId: 0
    };
  }

  private formatearFecha(fecha: string): string {
    return fecha.length === 16 ? `${fecha}:00` : fecha;
  }
}
