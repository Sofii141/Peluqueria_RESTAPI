import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { Servicio } from '../servicios/modelos/servicio';
import { ServicioService } from '../servicios/servicios/servicio.service';
import { Categoria } from '../categorias/modelos/categoria'; // 1. IMPORTAR CATEGORIA
import { CategoriaService } from '../categorias/servicios/categoria.service'; // 2. IMPORTAR SU SERVICIO

@Component({
  selector: 'app-featured-services',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './featured-services.component.html',
  styleUrl: './featured-services.component.css'
})
export class FeaturedServicesComponent implements OnInit {

  public todosLosServicios: Servicio[] = [];
  public serviciosFiltrados: Servicio[] = [];
  public categorias: Categoria[] = [];
  public categoriaActiva: number | 'all' = 'all';

  // 3. INYECTAR EL CATEGORIASERVICE
  constructor(
    private servicioService: ServicioService,
    private categoriaService: CategoriaService
  ) { }

  ngOnInit(): void {
    // Obtenemos las categorías para los filtros
    this.categoriaService.getCategorias().subscribe(cats => {
      this.categorias = cats;
    });

    // Obtenemos TODOS los servicios disponibles
    this.servicioService.getServicios().subscribe(servicios => {
      this.todosLosServicios = servicios.filter(s => s.disponible);
      this.serviciosFiltrados = this.todosLosServicios; // Al inicio, mostramos todos
    });
  }

  // 4. NUEVA FUNCIÓN PARA FILTRAR
  filtrarServicios(idCategoria: number | 'all'): void {
    this.categoriaActiva = idCategoria;
    if (idCategoria === 'all') {
      this.serviciosFiltrados = this.todosLosServicios;
    } else {
      this.serviciosFiltrados = this.todosLosServicios.filter(
        servicio => servicio.objCategoria?.id === idCategoria
      );
    }
  }
}