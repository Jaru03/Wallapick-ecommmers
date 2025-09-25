import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StripeService {
    http = inject(HttpClient);
    url = 'http://wallapick.pickmyskills.com:8080/stripe';

    getSession(products: any[]) {
      return this.http.post(`${this.url}/createCheckoutSession`, products);
    }
}
