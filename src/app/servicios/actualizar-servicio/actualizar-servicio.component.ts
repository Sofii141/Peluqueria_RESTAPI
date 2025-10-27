import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';
import { Servicio } from '../modelos/servicio';
import { ServicioService } from '../servicios/servicio.service';
import { Categoria } from '../../categorias/modelos/categoria';
import { CategoriaService } from '../../categorias/servicios/categoria.service';

@Component({
  selector: 'app-actualizar-servicio',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './actualizar-servicio.component.html',
  styleUrl: './actualizar-servicio.component.css'
})
export class ActualizarServicioComponent implements OnInit { 
  
  public servicio: Servicio = new Servicio();
  public categorias: Categoria[] = [];
  public titulo: String = 'Actualizar Servicio';

  // === AÑADIR ESTAS PROPIEDADES ===
  public selectedFile: File | null = null;
  public imagePreview: string | ArrayBuffer | null = null;

  constructor(
    private categoriaService: CategoriaService, 
    private servicioService: ServicioService, 
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    const servicioId = this.route.snapshot.paramMap.get('id');
  
    this.categoriaService.getCategorias().subscribe(categorias => {
      this.categorias = categorias;
  
      if (servicioId) {
        this.servicioService.getServicioById(+servicioId).subscribe(servicio => { 
          this.servicio = servicio;
          
          if (servicio.objCategoria) {
            this.servicio.objCategoria = this.categorias.find(cat => cat.id === servicio.objCategoria?.id) || null;
          }
        });
      }
    });
  }
  
  // === AÑADIR ESTE MÉTODO (COPIADO DE CREAR-SERVICIO) ===
  onFileSelected(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.selectedFile = file;
      const reader = new FileReader();
      reader.onload = () => {
        this.imagePreview = reader.result; // Muestra la previsualización de la NUEVA imagen
      };
      reader.readAsDataURL(file);
    }
  }

  // === MODIFICAR COMPLETAMENTE ESTE MÉTODO ===
  public actualizarServicio(): void {
    // Si el usuario seleccionó una nueva imagen
    if (this.selectedFile) {
      const formData = new FormData();
      formData.append('imagen', this.selectedFile);

      // Importante: No enviar la URL de la imagen antigua en el JSON
      const { imagen, ...servicioSinImagen } = this.servicio;
      formData.append('servicio', JSON.stringify(servicioSinImagen));

      this.servicioService.updateWithImage(this.servicio.id, formData).subscribe({
        next: (response) => {
          this.router.navigate(['/servicios']);
          Swal.fire('Servicio Actualizado', `El servicio ${response.nombre} se actualizó con éxito!`, 'success');
        },
        error: (err) => {
          console.error('Error al actualizar el servicio con imagen:', err);
          Swal.fire('Error', 'Ocurrió un error al actualizar el servicio.', 'error');
        }
      });
    } else {
      // Si no se seleccionó una nueva imagen, usar el método de actualización original
      this.servicioService.update(this.servicio).subscribe({
        next: (response) => {
          this.router.navigate(['/servicios']);
          Swal.fire('Servicio Actualizado', `El servicio ${response.nombre} se actualizó con éxito!`, 'success');
        },
        error: (err) => {
          console.error('Error al actualizar el servicio:', err);       
        }
      });
    }
  }

  compararCategoria(c1: Categoria, c2: Categoria): boolean {
    return c1 && c2 ? c1.id === c2.id : c1 === c2;
  }
}