import { HttpClient } from '@angular/common/http';
import { effect, inject, Injectable, signal } from '@angular/core';
import { map, Observable } from 'rxjs';

type LoginData = {
  username: string;
  password: string;
};

type RegisterData = {
  username: string;
  password: string;
  name: string;
  lastname: string;
  email: string;
};

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  isLogged = signal(false);
  http = inject(HttpClient);
  baseUrl = 'http://wallapick.pickmyskills.com:8080';
  goToEditProduct = signal<boolean>(false);

  login(userData: LoginData) {
    return this.http.post(`${this.baseUrl}/user/login`, userData).pipe(
      map((response: any) => {
        if (response.code === 200) {
          localStorage.setItem('token', response.data);
        }
        return response;
      })
    );
  }

  register(userData: RegisterData) {
    return this.http.post(`${this.baseUrl}/user`, userData);
  }

  logout() {
    localStorage.removeItem('token');
  }

  toggle(): void {
    this.isLogged.update((value) => !value);
  }

  userLogged(): boolean {
    if (localStorage.getItem('token')) {
      return true;
    }

    return false;
  }

  token() {
    const storedToken = localStorage.getItem('token');
    if (!storedToken) return null;

    try {
      const base64Url = storedToken.split('.')[1]; // Extrae el payload
      const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
      const jsonPayload = decodeURIComponent(
        atob(base64)
          .split('')
          .map((c) => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
          .join('')
      );

      return JSON.parse(jsonPayload);
    } catch (e) {
      console.error('Token inválido o malformado:', e);
      return null;
    }
  }

  dataUser(id: string) {
    return this.http.get(`${this.baseUrl}/user/${id}`)
  }

  updateUser(userData: any) {
    return this.http.patch(`${this.baseUrl}/user`, userData)
  }

  deleteUser(id: string) {
    return this.http.delete(`${this.baseUrl}/user/${id}`)
  }
}
