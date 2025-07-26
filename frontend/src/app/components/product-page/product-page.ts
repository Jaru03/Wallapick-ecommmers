import { Component } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { Divider } from 'primeng/divider';
import { ProductoComponent } from '../producto-component/producto-component';
import { BreadcrumbModule } from 'primeng/breadcrumb';
import { AvatarModule } from 'primeng/avatar';
import { RatingModule } from 'primeng/rating';
import { FormsModule } from '@angular/forms';
import { Card } from "primeng/card";
import { Carousel } from "primeng/carousel";

@Component({
  selector: 'app-product-page',
  imports: [ButtonModule, Divider, ProductoComponent, BreadcrumbModule, AvatarModule, RatingModule, FormsModule, Card, Carousel],
  templateUrl: './product-page.html',
  styleUrl: './product-page.css',
})
export class ProductPage {
  items = [
    { label: 'Home', icon: 'pi pi-home', routerLink: '/' },
    { label: 'Computer' },
    { label: 'Accessories' },
    { label: 'Keyboard' },
    { label: 'Wireless' },
  ];

  rating = 5;

  responsiveOptions = [
    {
      breakpoint: '1400px',
      numVisible: 3,
      numScroll: 1,
    },
    {
      breakpoint: '1280px',
      numVisible: 3,
      numScroll: 1,
    },
    {
      breakpoint: '786px',
      numVisible: 2,
      numScroll: 1,
    },
    {
      breakpoint: '575px',
      numVisible: 1,
      numScroll: 1,
    },
  ];

   products = [
    [{
      id: '1000',
      name: 'Bamboo Watch',
      description: 'Product Description',
      price: 65,
      category: 'Accessories',
      quantity: 24,
      inventoryStatus: 'INSTOCK',
      rating: 5,
      image: 'bamboo-watch.jpg',
    }],
    [{
      id: '1000',
      name: 'Bamboo Watch',
      description: 'Product Description',
      price: 65,
      category: 'Accessories',
      quantity: 24,
      inventoryStatus: 'INSTOCK',
      rating: 5,
      image: 'bamboo-watch.jpg',
    }],
    [{
      id: '1000',
      name: 'Bamboo Watch',
      description: 'Product Description',
      price: 65,
      category: 'Accessories',
      quantity: 24,
      inventoryStatus: 'INSTOCK',
      rating: 5,
      image: 'bamboo-watch.jpg',
    }],
    [{
      id: '1000',
      name: 'Bamboo Watch',
      description: 'Product Description',
      price: 65,
      category: 'Accessories',
      quantity: 24,
      inventoryStatus: 'INSTOCK',
      rating: 5,
      image: 'bamboo-watch.jpg',
    }],
    
  ];
  
}
