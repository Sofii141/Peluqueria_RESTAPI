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
  
  public actualizarServicio(): void {
    this.servicioService.update(this.servicio).subscribe(
      response => {
        this.router.navigate(['/servicios']);
        Swal.fire('Servicio Actualizado', `El servicio ${response.nombre} se actualizó con éxito!`, 'success');
      },
      error => {
        console.error('Error al actualizar el servicio:', error);       
      }
    );
  }

  compararCategoria(c1: Categoria, c2: Categoria): boolean {
    return c1 && c2 ? c1.id === c2.id : c1 === c2;
  }
}