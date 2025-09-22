import {
  Component,
  computed,
  effect,
  inject,
  linkedSignal,
  OnInit,
  signal,
} from '@angular/core';
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
import { ButtonModule } from 'primeng/button';
import { LoginService } from '../../services/login-service';

@Component({
  selector: 'app-products-page',
  imports: [
    ProductoComponent,
    DividerModule,
    FormsModule,
    Slider,
    FloatLabelModule,
    InputTextModule,
    Card,
    CommonModule,
    ButtonModule,
  ],
  templateUrl: './products-page.html',
  styleUrl: './products-page.css',
})
export class ProductsPage {
  rangeValues = [0, 2000];
  rangeValuesSignal = signal<[number, number]>([0, 2000]);
  estadoSeleccionado: string[] = []; // para usar con ngModel
  estadoSeleccionadoSignal = signal<string[]>([]);
  categoriaSeleccionada = signal<string>('Todas');
  isView: boolean = false;
  productsService = inject(ProductService);

  // Estado de la paginación
  currentPage = signal(1); // Página actual
  itemsPerPage = signal(15); // Número de productos por página

  products: any = linkedSignal(
    toSignal(this.productsService.getAllProducts(), { initialValue: [] })
  );

  paginatedProducts = computed(() => {
    const allProducts = this.filteredProducts() || [];
    const page = this.currentPage();
    const perPage = this.itemsPerPage();
    const start = (page - 1) * perPage;
    const end = start + perPage;

    return allProducts.slice(start, end);
  });

  totalPages = computed(() => {
    const total = this.filteredProducts()?.length || 0;
    return Math.ceil(total / this.itemsPerPage());
  });

  filteredProducts = computed(() => {
    const selectedCategory = this.categoriaSeleccionada();
    const selectedEstados = this.estadoSeleccionadoSignal();
    const [minPrice, maxPrice] = this.rangeValuesSignal();
    const allProducts = this.products().datas || [];

    return allProducts.filter((p: any) => {
      const matchesCategory =
        selectedCategory === 'Todas' || p.category === selectedCategory;
        
      const matchesEstado =
        selectedEstados.length === 0 || selectedEstados.includes(p.status);

      const matchesPrice = p.price >= minPrice && p.price <= maxPrice;

      return matchesCategory && matchesEstado && matchesPrice;
    });
  });

  nextPage() {
    if (this.currentPage() < this.totalPages()) {
      this.currentPage.update((p) => p + 1);
    }
  }

  prevPage() {
    if (this.currentPage() > 1) {
      this.currentPage.update((p) => p - 1);
    }
  }

  goToPage(page: number) {
    if (page >= 1 && page <= this.totalPages()) {
      this.currentPage.set(page);
    }
  }

  categories = new Set<string>();
  categoriesArray: string[] = [];
  state = new Set<string>();
  statesArray: string[] = [];

  filterVisibility() {
    this.isView = !this.isView;
  }

  selectCategory(category: string) {
    this.categoriaSeleccionada.set(category);
  }

  constructor() {
    // sincronizar array con signal
    effect(() => {
      this.estadoSeleccionadoSignal.set([...this.estadoSeleccionado]);
      console.log('Estado actualizado:', this.estadoSeleccionado);
    });

    effect(() => {
      const productos = this.products().datas;

      if (productos?.length) {
        this.categories = new Set(
          productos.map((p: { category: string }) => p.category)
        );
        this.categoriesArray = Array.from(this.categories);

        this.state = new Set(
          productos
            .map((p: { status: string | boolean }) => p.status)
            .filter(
              (status: any) =>
                status !== '' && status !== null && status !== undefined
            )
        );
        this.statesArray = Array.from(this.state);
      }

      this.rangeValuesSignal.set([this.rangeValues[0], this.rangeValues[1]]);

      console.log(this.filteredProducts());
    });
  }
}
