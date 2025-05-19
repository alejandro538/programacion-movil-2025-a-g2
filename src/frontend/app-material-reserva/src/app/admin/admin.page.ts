import { Component, OnInit } from '@angular/core';
import { ReservaService, ReservaDTO } from '../services/reserva.service';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.page.html',
  styleUrls: ['./admin.page.scss'],
  standalone: false
})
export class AdminPage implements OnInit {

  reservas: ReservaDTO[] = [];

  constructor(
    private reservaService: ReservaService,
    public authService: AuthService // <-- public para acceso en el template
  ) {}

  ngOnInit() {
    console.log('Rol usuario desde AuthService:', this.authService.getUsuario()?.rol);
    console.log('esAdmin():', this.authService.esAdmin());
    if (this.authService.esAdmin()) {
      this.cargarTodasLasReservas();
    }
  }

  cargarTodasLasReservas() {
    this.reservaService.getTodasLasReservas().subscribe({
      next: data => {
        this.reservas = data;
      },
      error: err => {
        console.error('Error cargando reservas:', err);
      }
    });
  }

  cambiarEstado(reserva: ReservaDTO, nuevoEstado: 'PENDIENTE' | 'APROBADA' | 'CANCELADA' | 'FINALIZADA') {
    const usuarioAdmin = this.authService.getUsuario();

    if (!usuarioAdmin) {
      alert('No hay usuario autenticado');
      return;
    }

    if (!reserva.id) {
      alert('Reserva inválida, falta id');
      return;
    }

    this.reservaService.actualizarEstadoReserva(reserva.id, nuevoEstado)
      .subscribe({
        next: () => {
          alert('Estado actualizado con éxito');
          this.cargarTodasLasReservas();
        },
        error: err => {
          console.error('Error al actualizar estado:', err);
          alert('Error al actualizar estado: ' + (err.error?.message || err.message));
        }
      });
  }
}
