import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
  standalone: false,
})
export class LoginPage {
  email = '';
  password = '';

  constructor(private auth: AuthService, private router: Router) {}

  iniciarSesion() {
    this.auth.login(this.email, this.password).subscribe({
      next: (user) => {
        alert(`Bienvenido ${user.nombre}`);
        this.router.navigate(['/home']);
      },
      error: () => {
        alert('Credenciales inválidas');
      },
    });
  }
}
