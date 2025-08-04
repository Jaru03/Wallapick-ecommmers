import { effect, Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
 isLogged = signal(false);

  login() {
    this.isLogged.set(true);
  }

  logout() {
    this.isLogged.set(false);
  }

  toggle() {
    this.isLogged.update(value => !value);
  }
}
