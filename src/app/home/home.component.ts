// src/app/home/home.component.ts (LIMPIO Y CORRECTO)
import { Component } from '@angular/core';
import { FeaturedServicesComponent } from '../featured-services/featured-services.component'; // 1. IMPORTAR

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [FeaturedServicesComponent], // 2. AÑADIR A IMPORTS
  templateUrl: './home.component.html',
  styleUrl: './home.component.css' 
})
export class HomeComponent {
  // ¡Ya no necesita lógica de servicios!
}