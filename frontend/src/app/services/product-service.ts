import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  http = inject(HttpClient);
  baseUrl = 'http://localhost:8080';

  cart= signal<any[]>([]);

  getAllProducts() {
    return this.http.get(`${this.baseUrl}/product/getAll`);
  }

  getOneProduct(id:string) {
    return this.http.get(`${this.baseUrl}/product/${id}`);
  }

  getMyProductsToSell():Observable<any>{
    return this.http.get(`${this.baseUrl}/product/searchMyProducts`);
  }

  createProduct(product:any){
    return this.http.post(`${this.baseUrl}/product`, product);
  }

  updateProduct(product:any){
    return this.http.patch(`${this.baseUrl}/product`, product);
  }

  deleteProduct(id:number){
    return this.http.delete(`${this.baseUrl}/product/${id}`);
  }


}
