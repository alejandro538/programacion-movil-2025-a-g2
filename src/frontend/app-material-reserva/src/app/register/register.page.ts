import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.page.html',
  styleUrls: ['./register.page.scss'],
  standalone: false
})
export class RegisterPage {
  nombre = '';
  email = '';
  password = '';

  constructor(private auth: AuthService, private router: Router) {}

  registrarse() {
    this.auth.register({ nombre: this.nombre, email: this.email, password: this.password }).subscribe({
      next: () => {
        alert('Usuario registrado correctamente');
        this.router.navigate(['/login']);
      },
      error: (err) => {
        alert('Error al registrar: ' + err.error);
      }
    });
  }
}
