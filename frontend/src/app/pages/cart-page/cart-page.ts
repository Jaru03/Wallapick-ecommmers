import { Component, effect, inject } from '@angular/core';
import { ProductoComponent } from "../../components/producto-component/producto-component";
import { CardModule } from 'primeng/card';
import { Divider } from "primeng/divider";
import { Button } from "primeng/button";
import { Router, RouterModule } from '@angular/router';
import { ProductService } from '../../services/product-service';
import { StripeService } from '../../services/stripe-service';

@Component({
  selector: 'app-cart-page',
  imports: [ProductoComponent, CardModule, Divider, Button, RouterModule],
  templateUrl: './cart-page.html',
  styleUrl: './cart-page.css'
})
export class CartPage {
  productService = inject(ProductService)
  stripeService = inject(StripeService);
  product = this.productService.cart;  
  router = inject(Router)
  totalSummaries(){
    return this.product().reduce((acc, product) => acc + product.price, 0);
  }

  getSessionCheckout(){
    this.stripeService.getSession(this.product()).subscribe((data:any) => {
    if(data.code === 200){
      localStorage.setItem('cart', JSON.stringify(this.product()));
      window.location.href = data.data;
      console.log(data.data);
      
    }
  })
  }

  constructor(){
    effect(() => {
      console.log(this.product());
    })

    console.log(this.totalSummaries());
    
  }
}
