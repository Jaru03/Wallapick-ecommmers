import { Component } from '@angular/core';
import { ProductoComponent } from '../producto-component/producto-component';
import { CommonModule } from '@angular/common';
import { CarouselModule } from 'primeng/carousel';

@Component({
  selector: 'app-home-page',
  imports: [ProductoComponent, CommonModule, CarouselModule],
  templateUrl: './home-page.html',
  styleUrl: './home-page.css',
})
export class HomePage {
  activeIndex = 0;

  quotes = [
    {
      text: 'I am Groot.',
      author: 'Groot',
      image: 'https://cdn.wallpapersafari.com/41/38/ruKsD7.jpg',
    },
    {
      text: "You're the head of security and your password is 'password'?",
      author: 'Peter Parker',
      image:
        'https://r4.wallpaperflare.com/wallpaper/340/7/602/spider-man-spider-man-far-from-home-tom-holland-hd-wallpaper-fd155d90a9273dced67d96fdc3b7e466.jpg',
    },
    {
      text: "That really is America's ass.",
      author: 'Captain America',
      image: 'https://images8.alphacoders.com/101/1012160.jpg',
    },
  ];

  setActive(index: number) {
    this.activeIndex = index;
  }
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
}
