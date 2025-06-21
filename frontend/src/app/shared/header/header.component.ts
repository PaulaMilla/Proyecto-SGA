import { Component } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  userEmail: string | null = null;
  userRol: string | null = null;
  isMenuOpen = false;

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }
  
  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
  this.authService.user$.subscribe(user => {
      console.log('Usuario recibido en header:', user);
      this.userEmail = user.email;
      this.userRol = user.rol;
    });
  }  

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}

