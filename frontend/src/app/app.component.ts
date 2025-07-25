import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  constructor(private router: Router) {}

  usaLayoutPublico(): boolean {
    // Solo estas rutas usarán layout público
    return this.router.url === '/';
  }
}
