import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CardsService {

  constructor(private http: HttpClient) { }

  private getAuthHeader(username: string, password: string): HttpHeaders {
    const credentials = btoa(`${username}:${password}`);
    return new HttpHeaders({
      'Authorization': `Basic ${credentials}`,
      'Content-Type': 'application/json',
    });
  }

  findAll(): Observable<any> {
    const url = 'http://localhost:8081/api/soa/cards';
    const headers = this.getAuthHeader('user', 'password');
    const options = {
      headers: headers,
    };
    return this.http.get(url, options);
  }

  send(message: any): Observable<any> {
    const url = 'http://localhost:8082/api/message/send';
    const headers = this.getAuthHeader('user', 'password');
    const options = {
      headers: headers,
    };
    return this.http.post(url, message, options);
  }
}
