import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  // BehaviorSubject para mantener el estado del rol actual y notificar a los suscriptores.
  private currentUserRole = new BehaviorSubject<string | null>(this.getRoleFromStorage());
  
  // Exponemos el estado del rol como un Observable público.
  public currentUserRole$: Observable<string | null> = this.currentUserRole.asObservable();

  constructor(private router: Router) { }

  /**
   * Simula el inicio de sesión.
   * Guarda el rol en localStorage y actualiza el BehaviorSubject.
   * @param role El rol a simular ('admin' o 'client').
   */
  login(role: 'admin' | 'client'): void {
    localStorage.setItem('userRole', role);
    this.currentUserRole.next(role);
    // Redirigimos al inicio después de iniciar sesión.
    this.router.navigate(['/']);
  }

  /**
   * Simula el cierre de sesión.
   * Limpia el rol de localStorage y del BehaviorSubject.
   */
  logout(): void {
    localStorage.removeItem('userRole');
    this.currentUserRole.next(null);
    // Redirigimos al inicio después de cerrar sesión.
    this.router.navigate(['/']);
  }

  /**
   * Obtiene el rol actual directamente del BehaviorSubject.
   * @returns El rol actual o null si no hay sesión.
   */
  public getRole(): string | null {
    return this.currentUserRole.getValue();
  }
  
  /**
   * Verifica si el usuario actual es un administrador.
   * @returns true si el rol es 'admin', false en caso contrario.
   */
  public isAdmin(): boolean {
    return this.getRole() === 'admin';
  }

  /**
   * Obtiene el rol guardado en localStorage al iniciar el servicio.
   * @returns El rol guardado o null.
   */
  private getRoleFromStorage(): string | null {
    if (typeof window !== 'undefined' && window.localStorage) {
      return localStorage.getItem('userRole');
    }
    return null;
  }
}