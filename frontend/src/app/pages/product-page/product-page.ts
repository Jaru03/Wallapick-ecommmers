import { Component, effect, inject, linkedSignal, OnInit } from '@angular/core';
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
import { StripeService } from '../../services/stripe-service';

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
export class ProductPage implements OnInit {
  route = inject(ActivatedRoute);
  id = this.route.snapshot.paramMap.get('id');
  rating = 5;
  productService = inject(ProductService);
  stripeService = inject(StripeService)
  loginService = inject(LoginService)

  product: any = linkedSignal(toSignal(this.productService.getOneProduct(this.id!), {
    initialValue: null,
  }));

  userId = this.loginService.token().id;
  dataUser!: any;
  dataUser$ = this.loginService.dataUser(this.userId).subscribe((data:any) => {
    if(data.code === 200){
      this.dataUser = data.data;
      
    }
  })

  ngOnInit() {
  this.route.params.subscribe(params => {
    const id = params['id'];
    this.productService.getOneProduct(id).subscribe((data: any) => {
      this.product.set(data);
    });
  });
  
}
isLogged = this.loginService.userLogged();
cart = this.productService.cart;
router = inject(Router);


  goToEditProduct() {
    this.loginService.goToEditProduct.set(true);
    this.router.navigate(['/account']);
  }
  onBuy() {
    if (!this.isLogged) {
      this.router.navigate(['/login']);
    }

    this.stripeService.getSession([this.product()?.data]).subscribe((data: any) => {
      if (data.code === 200) {
        localStorage.setItem('cart', JSON.stringify([this.product()?.data]));
        window.location.href = data.data;
      }
    });
  }

  addCart() {
    if (!this.isLogged) {
      this.router.navigate(['/login']);
    }

    this.cart.update(data => [...data, this.product()?.data])
  }



  constructor() {
    effect(() => {
    this.product()
      
      if (this.product()?.data?.category) {
      this.items = [
        { label: 'Home', icon: 'pi pi-home', routerLink: '/' },
        { label: this.product()?.data?.category, icon: '', routerLink: '' }
      ];
    }
    });    

    
  }

  items = [
    { label: 'Home', icon: 'pi pi-home', routerLink: '/' },
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
