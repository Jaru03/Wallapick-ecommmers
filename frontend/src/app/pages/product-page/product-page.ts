import { Component, effect, inject } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { Divider } from 'primeng/divider';
import { ProductoComponent } from '../../components/producto-component/producto-component';
import { BreadcrumbModule } from 'primeng/breadcrumb';
import { AvatarModule } from 'primeng/avatar';
import { RatingModule } from 'primeng/rating';
import { FormsModule } from '@angular/forms';
import { Card } from 'primeng/card';
import { Carousel } from 'primeng/carousel';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ProductService } from '../../services/product-service';
import { toSignal } from '@angular/core/rxjs-interop';
import { LoginService } from '../../services/login-service';

@Component({
  selector: 'app-product-page',
  imports: [
    ButtonModule,
    Divider,
    BreadcrumbModule,
    AvatarModule,
    RatingModule,
    FormsModule,
    Card,
    RouterModule,
  ],
  templateUrl: './product-page.html',
  styleUrl: './product-page.css',
})
export class ProductPage {
  route = inject(ActivatedRoute);
  id = this.route.snapshot.paramMap.get('id');
  rating = 5;
  productService = inject(ProductService);
  product: any = toSignal(this.productService.getOneProduct(this.id!), {
    initialValue: null,
  });
  loginService = inject(LoginService);
  isLogged = this.loginService.userLogged();
  cart = this.productService.cart;
  router = inject(Router);
  
  onBuy() {
    if (!this.isLogged) {
      this.router.navigate(['/login']);
    }
  }

  addCart() {
    if (!this.isLogged) {
      this.router.navigate(['/login']);
    }

    this.cart.update(data => [...data, this.product()?.data])
  }

  constructor() {
    effect(() => {
      console.log(this.product());
    });
  }

  items = [
    { label: 'Home', icon: 'pi pi-home', routerLink: '/' },
    { label: 'Computer' },
    { label: 'Accessories' },
    { label: 'Keyboard' },
    { label: 'Wireless' },
  ];

  responsiveOptions = [
    {
      breakpoint: '2000px',
      numVisible: 3,
      numScroll: 1,
    },
    {
      breakpoint: '1024px',
      numVisible: 3,
      numScroll: 1,
    },
    {
      breakpoint: '768px',
      numVisible: 2,
      numScroll: 1,
    },
    {
      breakpoint: '480px',
      numVisible: 1,
      numScroll: 1,
    },
  ];

  products = [
    [
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
    ],
    [
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
    ],
    [
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
    ],
    [
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
    ],
  ];
}
