import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DispersarService {
  private apiUrl = 'http://34.61.182.228/api/dispersion'; // Ajusta si cambia tu endpoint

  constructor(private http: HttpClient) {}

  dispersar(data: any): Observable<any> {
    return this.http.post(this.apiUrl, data);
  }
}
