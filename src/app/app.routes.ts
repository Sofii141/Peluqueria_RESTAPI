// src/app/app.routes.ts
import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { ListarServiciosComponent } from './servicios/listar-servicios/listar-servicios.component';
import { CrearServicioComponent } from './servicios/crear-servicio/crear-servicio.component';
import { ActualizarServicioComponent } from './servicios/actualizar-servicio/actualizar-servicio.component';

// REQUERIMIENTO: Importar los nuevos componentes marcadores
import { OfertasComponent } from './ofertas/ofertas.component';
import { CuponesComponent } from './cupones/cupones.component';
import { AyudaComponent } from './ayuda/ayuda.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    
    // Rutas de la funcionalidad principal (Servicios de la peluquería)
    { path: 'servicios', component: ListarServiciosComponent },
    { path: 'servicios/crear', component: CrearServicioComponent },
    { path: 'servicios/actualizar/:id', component: ActualizarServicioComponent },

    // REQUERIMIENTO: Añadir las rutas para los componentes "Borrador"
    { path: 'ofertas', component: OfertasComponent },
    { path: 'cupones', component: CuponesComponent },
    { path: 'ayuda', component: AyudaComponent },
    
    // Redirige cualquier otra ruta a la página de inicio
    { path: '**', redirectTo: '', pathMatch: 'full' }
];