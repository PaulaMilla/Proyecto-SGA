import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiAuth = 'http://34.61.182.228/api/api/auth';
  private apiUsuarios = 'http://34.61.182.228/api/api/usuarios';
  private tokenKey = 'token';
  private emailKey = 'email';

  constructor(private http: HttpClient) { }

  login(email: string, password:string): Observable<any>{
    return this.http.post(`${this.apiAuth}/login`, { email, password });
  }

  validateToken(): Observable<any>{
    const token = this.getToken();
    return this.http.post(`${this.apiAuth}/validate`, {}, {
      headers: {Authorization: `Bearer ${token}` }
    });
  }

  getUsuarioPorEmail(email: string): Observable<any>{
    return this.http.get(`${this.apiUsuarios}/email/${email}`);
  }
  getToken(): string | null{
    return localStorage.getItem(this.tokenKey);
  }

  isAuthenticated():boolean{
    return !!this.getToken();
  }

  logout(): void{
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.emailKey);
  }

}
