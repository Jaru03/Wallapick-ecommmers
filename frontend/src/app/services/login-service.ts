import { HttpClient } from '@angular/common/http';
import { effect, inject, Injectable, signal } from '@angular/core';
import { map } from 'rxjs';

type LoginData = {
  username: string
  password: string
}

type RegisterData = {
  username: string,
  password: string,
  name: string,
  lastname: string
  email: string
}

@Injectable({
  providedIn: 'root'
})
export class LoginService {
 isLogged = signal(false);
 http = inject(HttpClient);
 baseUrl = 'http://localhost:8080'

  login(userData: LoginData) {
    return this.http.post(`${this.baseUrl}/user/login`, userData).pipe(map( (response) => console.log(response) ))
  }

  register(userData: RegisterData){
    return this.http.post(`${this.baseUrl}/user`, userData)
  }

  logout() {
    this.isLogged.set(false);
  }

  toggle() {
    this.isLogged.update(value => !value);
  }
}
