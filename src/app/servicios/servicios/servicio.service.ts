import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Servicio } from '../modelos/servicio'; 
import { catchError, Observable, throwError } from 'rxjs';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class ServicioService { 

  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
  private urlEndPoint: string = 'http://localhost:5000/api/servicios'; 

  constructor(private http: HttpClient) { }

  getServicios(): Observable<Servicio[]> {
    console.log("Listando servicios desde el servicio");
    return this.http.get<Servicio[]>(this.urlEndPoint);
  }

  create(servicio: Servicio): Observable<Servicio> {
    console.log("Creando desde el servicio");
    return this.http.post<Servicio>(this.urlEndPoint, servicio, {headers: this.httpHeaders}).pipe(
      catchError(this.handleError)  
    );
  }   
  
  update(servicio: Servicio): Observable<Servicio> {
    console.log("Actualizando servicio desde el servicio", servicio);
    return this.http.put<Servicio>(`${this.urlEndPoint}/${servicio.id}`, servicio, { headers: this.httpHeaders }).pipe(
      catchError(this.handleError)  
    );
  }

  deleteServicio(id: number): Observable<void> {
    console.log("Eliminando servicio desde el servicio");
    return this.http.delete<void>(`${this.urlEndPoint}/${id}`, { headers: this.httpHeaders }).pipe(
      catchError(this.handleError)  
    ); 
  }

  getServicioById(id: number): Observable<Servicio> {
    console.log("Obteniendo servicio con ID:", id);
    return this.http.get<Servicio>(`${this.urlEndPoint}/${id}`);
  }

  private handleError(error: HttpErrorResponse) {
    if (error.status === 400 || error.status === 404) { 
      const mensajeError = error.error.mensaje || 'Error de validación o recurso no encontrado.';
      console.error(`Error ${error.status}: ${mensajeError}`);
      
      Swal.fire({
        icon: 'error',
        title: '¡Error!',
        text: mensajeError, 
        confirmButtonText: 'Cerrar'
      });

      return throwError(() => new Error(mensajeError));
    } else {      
      return throwError(() => new Error('Ocurrió un error inesperado. Por favor, intente más tarde.'));
    }
  }

  createWithImage(formData: FormData): Observable<Servicio> {
    console.log("Creando servicio con imagen desde el servicio");
    return this.http.post<Servicio>(`${this.urlEndPoint}/con-imagen`, formData).pipe(
      catchError(this.handleError)
    );
  }

  updateWithImage(id: number, formData: FormData): Observable<Servicio> {
    console.log("Actualizando servicio con imagen desde el servicio");
    // Usamos PUT para la actualización
    return this.http.put<Servicio>(`${this.urlEndPoint}/con-imagen/${id}`, formData).pipe(
      catchError(this.handleError)
    );
  }


}