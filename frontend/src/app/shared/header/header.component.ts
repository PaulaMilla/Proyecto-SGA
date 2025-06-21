import { Component } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  role: string | null = null;
  isMenuOpen = false;

  constructor(private authService: AuthService){}

  ngOnInit(): void{
    this.role = this.authService.getUserRole();
  }


  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  logout(): void{
    this.authService.logout();
    location.reload();
  }

}
