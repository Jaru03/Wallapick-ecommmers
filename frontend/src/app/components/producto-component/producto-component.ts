import { Component, inject, input } from '@angular/core';
import { DataView } from 'primeng/dataview';
import { ButtonModule } from 'primeng/button';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { LoginService } from '../../services/login-service';
import { ProductService } from '../../services/product-service';
import { StripeService } from '../../services/stripe-service';

@Component({
  selector: 'app-producto-component',
  imports: [DataView, ButtonModule, CommonModule, RouterModule],
  templateUrl: './producto-component.html',
  styleUrl: './producto-component.css',
})
export class ProductoComponent {
  layout = input<'grid' | 'list'>('grid');
  loginService = inject(LoginService);
  productService = inject(ProductService);
  stripeService = inject(StripeService);
  isLogged = this.loginService.userLogged();
  cart = this.productService.cart;
  router = inject(Router);

  userId = this.loginService.token().id;
  dataUser!: any;
  dataUser$ = this.loginService.dataUser(this.userId).subscribe((data:any) => {
    if(data.code === 200){
      this.dataUser = data.data;
    }
    console.log(data);
  })

  onBuy() {
    if (!this.isLogged) {
      this.router.navigate(['/login']);
    }

    this.stripeService.getSession(this.product()).subscribe((data: any) => {
      if (data.code === 200) {
        localStorage.setItem('cart', JSON.stringify(this.product()));
        window.location.href = data.data;
        console.log(data.data);
      }
    });
  }

  addCart(product: any) {
    if (!this.isLogged) {
      this.router.navigate(['/login']);
    }

    this.cart.update((data) => [...data, product]);
  }

  product = input<any>([
    {
      id: '1000',
      name: 'Bamboo Watch',
      description: 'Product Description',
      price: 65,
      category: 'Accessories',
      quantity: 24,
      inventoryStatus: 'INSTOCK',
      rating: 5,
      image: 'bamboo-watch.jpg',
    },
  ]);

  options: string[] = ['list', 'grid'];
}
