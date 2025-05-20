import { Component, OnInit } from '@angular/core';
import { MaterialService, Material, NuevoMaterial } from '../services/material.service';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-materiales',
  templateUrl: './materiales.page.html',
  styleUrls: ['./materiales.page.scss'],

  standalone: false
})
export class MaterialesPage implements OnInit {

  materiales: Material[] = [];
  nuevoMaterial: Material = { id: 0, nombre: '', descripcion: '', cantidadDisponible: 0 };
  esAdmin = false;

  constructor(private materialService: MaterialService, private authService: AuthService) {}

  ngOnInit() {
    this.esAdmin = this.authService.esAdmin();
    this.cargarMateriales();
  }

  cargarMateriales() {
    this.materialService.getMateriales().subscribe(data => this.materiales = data);
  }

  guardarMaterial() {
    if (this.nuevoMaterial.id === 0) {
      // Crear nuevo material usando la interfaz NuevoMaterial (sin id)
      const materialParaCrear: NuevoMaterial = {
        nombre: this.nuevoMaterial.nombre,
        descripcion: this.nuevoMaterial.descripcion,
        cantidadDisponible: this.nuevoMaterial.cantidadDisponible
      };

      this.materialService.crearMaterial(materialParaCrear).subscribe(() => {
        this.nuevoMaterial = { id: 0, nombre: '', descripcion: '', cantidadDisponible: 0 };
        this.cargarMateriales();
      });
    } else {
      // Actualizar material existente
      this.materialService.actualizarMaterial(this.nuevoMaterial.id, this.nuevoMaterial).subscribe(() => {
        this.nuevoMaterial = { id: 0, nombre: '', descripcion: '', cantidadDisponible: 0 };
        this.cargarMateriales();
      });
    }
  }

  editar(material: Material) {
    this.nuevoMaterial = { ...material };
  }

  eliminar(id: number) {
    this.materialService.eliminarMaterial(id).subscribe(() => this.cargarMateriales());
  }
}
