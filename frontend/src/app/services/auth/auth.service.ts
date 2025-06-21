import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { jwtDecode } from 'jwt-decode';

interface JwtResponse {
  token: string;
  email: string;
  rol: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiAuth = 'http://34.61.182.228/api/auth';
  private apiUsuarios = 'http://34.61.182.228/api/usuarios';
  private tokenKey = 'token';
  private emailKey = 'email';

  private userSubject = new BehaviorSubject<{ email: string | null; rol: string | null }>({
    email: null,
    rol: null
  });

  user$ = this.userSubject.asObservable();

  constructor(private http: HttpClient) {
    const token = this.getToken();
    if (token) {
      const decoded = this.decodeToken(token);
      this.userSubject.next({ email: decoded?.sub || null, rol: decoded?.role || null });
    }
  }

  login(email: string, password: string): Observable<JwtResponse> {
    return new Observable(observer => {
      this.http.post<JwtResponse>(`${this.apiAuth}/login`, { email, password }).subscribe({
        next: res => {
          localStorage.setItem(this.tokenKey, res.token);
          localStorage.setItem(this.emailKey, res.email);
          this.userSubject.next({ email: res.email, rol: res.rol });
          observer.next(res);
          observer.complete();
        },
        error: err => observer.error(err)
      });
    });
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

  logout(): void {
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.emailKey);
    this.userSubject.next({ email: null, rol: null });
  }

  getCurrentUser() {
    return this.userSubject.getValue();
  }
}