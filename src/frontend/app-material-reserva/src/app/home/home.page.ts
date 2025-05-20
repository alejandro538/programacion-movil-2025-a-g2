import { Component, OnInit } from '@angular/core';
import { AuthService, Usuario } from '../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss'],
  standalone: false,
})
export class HomePage implements OnInit {
  usuario: Usuario | null = null;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit() {
    this.usuario = this.authService.getUsuario();
  }

  cerrarSesion() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
