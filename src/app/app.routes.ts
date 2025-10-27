import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { ListarServiciosComponent } from './servicios/listar-servicios/listar-servicios.component';
import { CrearServicioComponent } from './servicios/crear-servicio/crear-servicio.component';
import { ActualizarServicioComponent } from './servicios/actualizar-servicio/actualizar-servicio.component';

import { OfertasComponent } from './ofertas/ofertas.component';
import { CuponesComponent } from './cupones/cupones.component';
import { AyudaComponent } from './ayuda/ayuda.component';

// 1. IMPORTAR EL NUEVO GUARDIA
import { adminGuard } from './auth/auth.guard';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    
    // Rutas de la funcionalidad principal (Servicios de la peluquería)
    { path: 'servicios', component: ListarServiciosComponent },
    
    // 2. APLICAR EL GUARDIA A LAS RUTAS PROTEGIDAS
    { 
      path: 'servicios/crear', 
      component: CrearServicioComponent,
      canActivate: [adminGuard] // Solo los administradores pueden activar esta ruta
    },
    { 
      path: 'servicios/actualizar/:id', 
      component: ActualizarServicioComponent,
      canActivate: [adminGuard] // Solo los administradores pueden activar esta ruta
    },

    // Rutas "Borrador"
    { path: 'ofertas', component: OfertasComponent },
    { path: 'cupones', component: CuponesComponent },
    { path: 'ayuda', component: AyudaComponent },
    
    // Redirige cualquier otra ruta a la página de inicio
    { path: '**', redirectTo: '', pathMatch: 'full' }
];