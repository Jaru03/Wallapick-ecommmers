import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  http = inject(HttpClient);
  baseUrl = 'http://localhost:8080';

  getAllProducts() {
    return this.http.get(`${this.baseUrl}/product/getAll`);
  }

  createProduct(product:any){
    return this.http.post(`${this.baseUrl}/product`, product);
  }
}
