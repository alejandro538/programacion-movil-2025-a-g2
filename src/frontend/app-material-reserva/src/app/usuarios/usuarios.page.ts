  import { Component, OnInit } from '@angular/core';
  import { UsuarioService } from '../services/usuario.service';
  import { Usuario, AuthService } from '../services/auth.service';

  @Component({
    selector: 'app-usuarios',
    templateUrl: './usuarios.page.html',
    styleUrls: ['./usuarios.page.scss'],
    standalone: false
  })
  export class UsuariosPage implements OnInit {

    usuarios: Usuario[] = [];
    esAdmin: boolean = false;

    constructor(
      private usuarioService: UsuarioService,
      private authService: AuthService
    ) {}

    ngOnInit() {
      const usuario = this.authService.getUsuario();
      this.esAdmin = usuario?.rol === 'ADMIN';

      if (this.esAdmin) {
        this.cargarUsuarios();
      }
    }

    cargarUsuarios() {
      this.usuarioService.getTodos().subscribe(data => {
        this.usuarios = data;
      });
    }

    cambiarRol(usuario: Usuario) {
      const nuevoRol = usuario.rol === 'ADMIN' ? 'USER' : 'ADMIN';
      this.usuarioService.actualizarRolUsuario(usuario.id, nuevoRol).subscribe(() => {
        this.cargarUsuarios();
      });
    }

  }
