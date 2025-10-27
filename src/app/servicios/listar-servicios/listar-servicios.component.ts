import { Component, OnInit } from '@angular/core';
import { Servicio } from '../modelos/servicio';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ServicioService } from '../servicios/servicio.service';
import { HttpClientModule } from '@angular/common/http';
import Swal from 'sweetalert2';
import { SweetAlert2Module } from '@sweetalert2/ngx-sweetalert2';
import { Categoria } from '../../categorias/modelos/categoria';
import { CategoriaService } from '../../categorias/servicios/categoria.service';
import { AuthService } from '../../auth/auth.service'; // 1. IMPORTAR AuthService

@Component({
  selector: 'app-listar-servicios',
  standalone: true,
  imports: [CommonModule, RouterLink, HttpClientModule, SweetAlert2Module],
  templateUrl: './listar-servicios.component.html',
  styleUrls: ['./listar-servicios.component.css']
})
export class ListarServiciosComponent implements OnInit {

  servicios: Servicio[] = [];
  serviciosFiltrados: Servicio[] = [];
  categorias: Categoria[] = [];
  categoriaActiva: string | number = 'all';
  public isAdmin: boolean = false; // 2. AÑADIR PROPIEDAD PARA VERIFICAR ROL

  constructor(
    private objServicioService: ServicioService,
    private objCategoriaService: CategoriaService,
    private router: Router,
    private authService: AuthService // 3. INYECTAR AuthService
  ) {}

  ngOnInit(): void {
    // 4. ESTABLECER EL VALOR DE isAdmin AL INICIAR
    this.isAdmin = this.authService.isAdmin();
    
    this.objCategoriaService.getCategorias().subscribe(categorias => {
      this.categorias = categorias;
    });

    this.objServicioService.getServicios().subscribe(servicios => {
      this.servicios = servicios;
      this.serviciosFiltrados = servicios;
    });
  }

  filtrarServicios(categoriaId: string | number): void {
    this.categoriaActiva = categoriaId;
    if (categoriaId === 'all') {
      this.serviciosFiltrados = this.servicios;
    } else {
      this.serviciosFiltrados = this.servicios.filter(
        servicio => servicio.objCategoria?.id === categoriaId
      );
    }
  }

  editarServicio(id: number): void {
    this.router.navigate(['/servicios/actualizar', id]);
  }

  eliminarServicio(id: number): void {
    Swal.fire({
      title: '¿Desea eliminar el servicio?',
      text: "La eliminación no se puede revertir",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Confirmar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.objServicioService.deleteServicio(id).subscribe(() => {
          this.servicios = this.servicios.filter(s => s.id !== id);
          this.serviciosFiltrados = this.serviciosFiltrados.filter(s => s.id !== id);
          Swal.fire('Eliminado', 'El servicio ha sido eliminado.', 'success');
        });
      }
    });
  }
}