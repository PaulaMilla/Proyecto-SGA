import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

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
  private roleKey = 'rol';

  constructor(private http: HttpClient) { }

  login(email: string, password: string): Observable<JwtResponse> {
  return this.http.post<JwtResponse>(`${this.apiAuth}/login`, { email, password });
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

  getUserRole(): string | null {
    console.log(this.roleKey);
    return localStorage.getItem(this.roleKey);
  }
  
  isLoggedIn():boolean{
    return !!this.getToken();
  }

  logout(): void{
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.emailKey);
    localStorage.removeItem(this.roleKey);
    localStorage.removeItem('role');
  }

}
