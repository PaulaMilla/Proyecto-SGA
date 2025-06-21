import { Component } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';
import { Router } from '@angular/router';
import { FormBuilder,FormGroup, Validators } from '@angular/forms';
import { jwtDecode } from 'jwt-decode';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  loginForm!: FormGroup;

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router){}
  
  ngOnInit(): void{
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    })
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      const { email, password } = this.loginForm.value;
      this.authService.login(email, password).subscribe({
        next: (res) => {
          localStorage.setItem('token', res.token);
          localStorage.setItem('email', res.email);
          localStorage.setItem('rol', res.rol); 
          this.router.navigate(['/home']); 
        },
        error: (err) => {
          alert('Credenciales inválidas');
          console.log(err);
        }
      });
    }
  }

}
