import { Component, inject, OnInit } from '@angular/core';
import { Button, ButtonDirective, ButtonModule } from "primeng/button";
import { ProductService } from '../../services/product-service';
import { OrderService } from '../../services/order-service';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-success-page',
  imports: [ButtonModule, ButtonDirective, RouterModule],
  templateUrl: './success-page.html',
  styleUrl: './success-page.css'
})
export class SuccessPage implements OnInit {
  orderService = inject(OrderService);
  
  ngOnInit(): void {  
    
    const productsId = JSON.parse(localStorage.getItem('cart') || '[]').map((product: any) => product.id);
    console.log(productsId);
    if (productsId.length > 0) {
    this.orderService.successOrder(productsId).subscribe((data:any) => {
      console.log(data);
      
    });}
  }


}
