import { Component, computed, effect, inject, linkedSignal, OnInit, signal } from '@angular/core';
import { ProductoComponent } from '../../components/producto-component/producto-component';
import { DividerModule } from 'primeng/divider';
import { FormsModule } from '@angular/forms';
import { Slider } from 'primeng/slider';
import { FloatLabelModule } from 'primeng/floatlabel';
import { InputTextModule } from 'primeng/inputtext';
import { Checkbox } from 'primeng/checkbox';
import { Card } from 'primeng/card';
import { CommonModule } from '@angular/common';
import { ProductService } from '../../services/product-service';
import { toSignal } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-products-page',
  imports: [
    ProductoComponent,
    DividerModule,
    FormsModule,
    Slider,
    FloatLabelModule,
    InputTextModule,
    Checkbox,
    Card,
    CommonModule,
  ],
  templateUrl: './products-page.html',
  styleUrl: './products-page.css',
})
export class ProductsPage {
  rangeValues: number[] = [0, 2000];
  estadoSeleccionado: string[] = [];
  categoriaSeleccionada = signal<string>("All");
  isView: boolean = false;
  productsService = inject(ProductService);
  products: any = linkedSignal(
    toSignal(this.productsService.getAllProducts(), { initialValue: [] })
  );

  filteredProducts = computed(() => {
  const selectedCategory = this.categoriaSeleccionada();
  const allProducts = this.products().datas;

  return selectedCategory === 'All'
    ? allProducts
    : allProducts.filter((p: { category: string }) => p.category === selectedCategory);
});


  categories = new Set<string>();
  categoriesArray: string[] = [];

  filterVisibility() {
    this.isView = !this.isView;
  }

  selectCategory(category: string) {
    this.categoriaSeleccionada.set(category);
  }

  constructor() {
    effect(() => {
      const productos = this.products().datas;

      if (productos?.length) {
        this.categories = new Set(
          productos.map((p: { category: string }) => p.category)
        );
        this.categoriesArray = Array.from(this.categories);
      }

      console.log(this.filteredProducts());
    });
  }
}
