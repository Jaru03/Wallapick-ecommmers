import { Component, computed, effect, inject, linkedSignal } from '@angular/core';
import { ProductoComponent } from '../../components/producto-component/producto-component';
import { CommonModule } from '@angular/common';
import { CarouselModule } from 'primeng/carousel';
import { ProductService } from '../../services/product-service';
import { toSignal } from '@angular/core/rxjs-interop';
import { RouterLink } from '@angular/router';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-home-page',
  imports: [ CommonModule, CarouselModule, RouterLink, ButtonModule],
  templateUrl: './home-page.html',
  styleUrl: './home-page.css',
})
export class HomePage {
onBuy() {
throw new Error('Method not implemented.');
}
addCart(_t35: any) {
throw new Error('Method not implemented.');
}
  activeIndex = 0;
  productsService = inject(ProductService)
  products:any = linkedSignal(toSignal(this.productsService.getAllProducts(), { initialValue: [] }));
  productsCamisetas = computed(() => this.products().datas?.filter((p: any) => p.category === 'camiseta').slice(0, 7));
  productsTech = computed(() => this.products().datas?.filter((p: any) => p.category === 'ordenador').slice(0, 7));
  productsColeccion = computed(() => this.products().datas?.filter((p: any) => p.category === 'coleccion').slice(0, 7));

  constructor() {
    effect(() => console.log(this.products()));
  }

  quotes = [
    {
      text: 'I am Groot.',
      image: 'https://cdn.wallpapersafari.com/41/38/ruKsD7.jpg',
    },
    {
      text: "You're the head of security and your password is 'password'?",
      image:
        'https://r4.wallpaperflare.com/wallpaper/340/7/602/spider-man-spider-man-far-from-home-tom-holland-hd-wallpaper-fd155d90a9273dced67d96fdc3b7e466.jpg',
    },
    {
      text: "That really is America's ass.",
      image: 'https://images8.alphacoders.com/101/1012160.jpg',
    },
  ];

  setActive(index: number) {
    this.activeIndex = index;
  }

  responsiveOptions = [
    {
      breakpoint: '2000px',
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
