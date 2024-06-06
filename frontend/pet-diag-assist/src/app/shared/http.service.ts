import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';
import { baseUrl } from "../../environments/environment";
import { ErrorService } from './errors.service';

@Injectable({
  providedIn: 'root'
})

export class HttpService {
  constructor(
    private http: HttpClient,
    private errorService: ErrorService
  ) {}


  // Métodos HTTP simplificados sem configuração explícita de token
  get<T>(url: string,  headers?: HttpHeaders): Observable<T> {
    return this.http.get<T>(`${baseUrl}${url}`)
      .pipe(retry(1), catchError(this.errorService.errorHandl));
  }

  getRaw(url: string): Observable<Blob> {
    return this.http.get<Blob>(`${baseUrl}${url}`, { responseType: 'blob' as 'json' })
      .pipe(retry(1), catchError(this.errorService.errorHandl));
  }

  post<T>(url: string, data: any = {}): Observable<T> {
    return this.http.post<T>(`${baseUrl}${url}`, data)
      .pipe(retry(1), catchError(this.errorService.errorHandl));
  }

  put<T>(url: string, data: any = {}): Observable<T> {
    return this.http.put<T>(`${baseUrl}${url}`, data)
      .pipe(retry(1), catchError(this.errorService.errorHandl));
  }

  patch<T>(url: string, data: any = {}): Observable<T> {
    return this.http.patch<T>(`${baseUrl}${url}`, data)
      .pipe(retry(1), catchError(this.errorService.errorHandl));
  }

  delete<T>(url: string): Observable<T> {
    return this.http.delete<T>(`${baseUrl}${url}`)
      .pipe(retry(1), catchError(this.errorService.errorHandl));
  }
}
