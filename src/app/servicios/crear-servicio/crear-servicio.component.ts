import { Component, OnInit } from '@angular/core';
import { Servicio } from '../modelos/servicio';
import { ServicioService } from '../servicios/servicio.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2';
import { Categoria } from '../../categorias/modelos/categoria';
import { CategoriaService } from '../../categorias/servicios/categoria.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-crear-servicio',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './crear-servicio.component.html',
  styleUrl: './crear-servicio.component.css'
})
export class CrearServicioComponent implements OnInit {
  public servicio: Servicio = new Servicio();
  public categorias: Categoria[] = [];
  public titulo: String = 'Registrar Nuevo Servicio';

  public selectedFile!: File;
  public imagePreview: string | ArrayBuffer | null = null;

  constructor(
    private categoriaService: CategoriaService,
    private servicioService: ServicioService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.servicio.objCategoria = null;
    this.servicio.disponible = true;
    this.categoriaService.getCategorias().subscribe(
      categorias => this.categorias = categorias
    );
  }

  onFileSelected(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.selectedFile = file;
      const reader = new FileReader();
      reader.onload = () => {
        this.imagePreview = reader.result;
      };
      reader.readAsDataURL(file);
    }
  }

  public crearServicio(): void {
    if (!this.selectedFile) {
      Swal.fire('Error', 'Debe seleccionar una imagen para el servicio.', 'error');
      return;
    }

    const formData = new FormData();
    formData.append('imagen', this.selectedFile);

    const { imagen, ...servicioSinImagen } = this.servicio;

    formData.append('servicio', JSON.stringify(servicioSinImagen));

    this.servicioService.createWithImage(formData).subscribe({
      next: (response) => {
        this.router.navigate(['/servicios']);
        Swal.fire('Nuevo Servicio', `Servicio ${response.nombre} creado con éxito!`, 'success');
      },
      error: (err) => {
        console.error('Error al crear el servicio:', err);
        Swal.fire('Error', 'Ocurrió un error al crear el servicio.', 'error');
      }
    });
}

  compararCategoria(c1: Categoria, c2: Categoria): boolean {
    return c1 && c2 ? c1.id === c2.id : c1 === c2;
  }
}