import { Component, effect, inject } from '@angular/core';
import { ProductoComponent } from "../../components/producto-component/producto-component";
import { CardModule } from 'primeng/card';
import { Divider } from "primeng/divider";
import { Button } from "primeng/button";
import { RouterModule } from '@angular/router';
import { ProductService } from '../../services/product-service';

@Component({
  selector: 'app-cart-page',
  imports: [ProductoComponent, CardModule, Divider, Button, RouterModule],
  templateUrl: './cart-page.html',
  styleUrl: './cart-page.css'
})
export class CartPage {
  productService = inject(ProductService)
  product = this.productService.cart;

  totalSummaries(){
    return this.product().reduce((acc, product) => acc + product.price, 0);
  }

  constructor(){
    effect(() => {
      console.log(this.product());
    })

    console.log(this.totalSummaries());
    
  }
}
