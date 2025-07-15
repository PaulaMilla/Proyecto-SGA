import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { jwtDecode } from 'jwt-decode';

export interface JwtResponse {
  token: string;
  email: string;
  rol: string;
}

export interface User {
  email: string | null;
  rol: string | null;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiAuth = 'http://34.61.182.228/api/auth';
  private apiUsuarios = 'http://34.61.182.228/api/usuarios';

  private tokenKey = 'token';
  private emailKey = 'email';
  private rolKey = 'rol';

  private userSubject = new BehaviorSubject<User>({ email: null, rol: null });
  user$ = this.userSubject.asObservable();

  constructor(private http: HttpClient) {
    this.loadUserFromStorage();
  }

  private loadUserFromStorage(): void {
    const token = this.getToken();
    const email = localStorage.getItem(this.emailKey);
    const rol = localStorage.getItem(this.rolKey);

    if (token && email && rol) {
      this.userSubject.next({ email, rol });
    } else {
      this.userSubject.next({ email: null, rol: null });
    }
  }

  login(email: string, password: string): Observable<JwtResponse> {
    return new Observable(observer => {
      this.http.post<JwtResponse>(`${this.apiAuth}/login`, { email, password }).subscribe({
        next: res => {
          localStorage.setItem(this.tokenKey, res.token);
          localStorage.setItem(this.emailKey, res.email);
          localStorage.setItem(this.rolKey, res.rol);
          this.userSubject.next({ email: res.email, rol: res.rol });
          observer.next(res);
          observer.complete();
        },
        error: err => observer.error(err)
      });
    });
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.emailKey);
    localStorage.removeItem(this.rolKey);
    this.userSubject.next({ email: null, rol: null });
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  decodeToken(token: string) {
    try {
      return jwtDecode<{ sub: string; role: string }>(token);
    } catch {
      return null;
    }
  }

  getCurrentUser(): User {
    return this.userSubject.getValue();
  }
}
