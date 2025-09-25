import { Component, computed, effect, inject, linkedSignal } from '@angular/core';
import { ProductService } from '../../services/product-service';
import { toSignal } from '@angular/core/rxjs-interop';
import { CarouselModule } from 'primeng/carousel';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-carousels',
  imports: [CarouselModule, CommonModule, RouterLink, ButtonModule],
  templateUrl: './carousels.html',
  styleUrl: './carousels.css'
})
export class Carousels {

  activeIndex = 0;
  productsService = inject(ProductService)
  products:any = linkedSignal(toSignal(this.productsService.getAllProducts(), { initialValue: [] }));
  productsCamisetas = computed(() => this.products().datas?.filter((p: any) => p.category === 'camiseta').slice(0, 7));
  productsTech = computed(() => this.products().datas?.filter((p: any) => p.category === 'ordenador').slice(0, 7));
  productsColeccion = computed(() => this.products().datas?.filter((p: any) => p.category === 'coleccion').slice(0, 7));

  constructor() {
    effect(() => this.products());
  }

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
