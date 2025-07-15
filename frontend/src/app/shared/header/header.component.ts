import { Component } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  userEmail: string | null = null;
  userRol: string | null = null;
  isMenuOpen = false;

  private subscription!: Subscription;


  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }
  
  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.subscription = this.authService.user$.subscribe(user => {
    this.userEmail = user.email ?? localStorage.getItem('email');
    this.userRol = user.rol ?? localStorage.getItem('rol');
    console.log('[Header] Email:', this.userEmail, '| Rol:', this.userRol);
  });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}

