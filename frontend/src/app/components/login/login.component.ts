import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  showModal = false;
  modalMessage = '';
  isSuccess = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      const { email, password } = this.loginForm.value;
      this.authService.login(email, password).subscribe({
        next: (res) => {
          localStorage.setItem('token', res.token);
          localStorage.setItem('email', res.email);
          localStorage.setItem('rol', res.rol);

          this.modalMessage = '¡Bienvenido al sistema!';
          this.isSuccess = true;
          this.showModal = true;
        },
        error: (err) => {
          this.modalMessage = 'Credenciales inválidas. Intenta nuevamente.';
          this.isSuccess = false;
          this.showModal = true;
          console.error(err);
        }
      });
    }
  }

  closeModal(): void {
    const modal = document.querySelector('.custom-modal');
    if (modal) {
      modal.classList.add('hide');
      setTimeout(() => {
        this.showModal = false;
        if (this.isSuccess) {
          this.router.navigate(['/home']);
        }
      }, 300);
    } else {
      this.showModal = false;
      if (this.isSuccess) {
        this.router.navigate(['/home']);
      }
    }
  }
}
