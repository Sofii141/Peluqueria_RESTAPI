import { Component } from '@angular/core'; // 1. YA NO SE NECESITA HostListener
import { RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common'; // 2. YA NO SE NECESITA NgClass
import { AuthService } from '../auth/auth.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, CommonModule], // 3. QUITAR NgClass de imports
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {

  public currentUserRole$: Observable<string | null>;
  // 4. ELIMINAR la propiedad isScrolled
  // public isScrolled = false;

  constructor(private authService: AuthService) {
    this.currentUserRole$ = this.authService.currentUserRole$;
  }

  // 5. ELIMINAR TODO EL MÉTODO onWindowScroll
  /*
  @HostListener('window:scroll', [])
  onWindowScroll() {
    const verticalOffset = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;
    this.isScrolled = verticalOffset > 10;
  }
  */
  
  loginAsAdmin(): void {
    this.authService.login('admin');
  }

  loginAsClient(): void {
    this.authService.login('client');
  }

  logout(): void {
    this.authService.logout();
  }
}