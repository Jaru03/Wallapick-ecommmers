import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  http = inject(HttpClient);
  url = 'http://localhost:8080/order';

  successOrder(ids: any[]) {
    return this.http.post(`${this.url}`, ids);
  }

  getMyOrders() {
    return this.http.get(`${this.url}`);
  }
}
