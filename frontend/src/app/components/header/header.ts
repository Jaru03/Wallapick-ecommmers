import { Component, effect, inject } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { MenuItem } from 'primeng/api';
import { Menubar } from 'primeng/menubar';
import { LoginService } from '../../services/login-service';
import { ButtonModule } from 'primeng/button';
import { InputIcon } from 'primeng/inputicon';
import { IconField } from 'primeng/iconfield';
import { InputTextModule } from 'primeng/inputtext';
import { BadgeModule } from 'primeng/badge';
import { OverlayBadgeModule } from 'primeng/overlaybadge';
import { ProductService } from '../../services/product-service';
import { FormsModule } from '@angular/forms';
import { Card } from 'primeng/card';
import { ElementRef, HostListener, ViewChild } from '@angular/core';

@Component({
  selector: 'app-header',
  imports: [
    RouterModule,
    Menubar,
    ButtonModule,
    OverlayBadgeModule,
    InputIcon,
    IconField,
    InputTextModule,
    BadgeModule,
    FormsModule,
    Card
  ],
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class Header {
  loginService = inject(LoginService);
  productService = inject(ProductService);
  cartLength = this.productService.cart().length;
  searchInput = ''
  timeout: any;
  productsPartial!:any;
  router = inject(Router)

  handlerInput(){
    clearTimeout(this.timeout)
    this.timeout = setTimeout(()=>{
      console.log(this.searchInput);
      this.productService.getPartialName(this.searchInput).subscribe((data:any)=> {
        this.productsPartial = data
        console.log(data);
      })
    }, 300)
    
  }

  goToProduct(id:number, input: HTMLInputElement){
    input.value = ''
    this.searchInput = ''
    this.router.navigate([`/products/${id}`])
  }

  constructor() {
    effect(() => {
      this.cartLength = this.productService.cart().length;
    });
  }
  @ViewChild('searchContainer') searchContainer!: ElementRef;
  @HostListener('document:click', ['$event'])
  onClickOutside(event: MouseEvent) {
  if (!this.searchContainer) return;

  const clickedInside = this.searchContainer.nativeElement.contains(event.target);
  if (!clickedInside) {
    this.searchInput = '';
    this.productsPartial = null;
  }
}

  itemsText: MenuItem[] = [
    { label: 'Inicio', routerLink: '/' },
    { label: 'Productos', routerLink: '/products' },
  ];

  itemsIcons: MenuItem[] = [
    { icon: 'pi-plus-circle', routerLink: '/sell' },
    { icon: 'pi-shopping-cart', routerLink: '/cart' },
    { icon: 'pi-user', routerLink: '/account' },
  ];
}
