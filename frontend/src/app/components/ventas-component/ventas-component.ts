import { Component, effect, inject, input, linkedSignal } from '@angular/core';
import { ProductService } from '../../services/product-service';
import { toSignal } from '@angular/core/rxjs-interop';
import { ProductoComponent } from '../producto-component/producto-component';
import { Button, ButtonModule } from 'primeng/button';
import { MessageService } from 'primeng/api';
import { Toast } from 'primeng/toast';
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { SelectModule } from 'primeng/select';

@Component({
  selector: 'app-ventas-component',
  imports: [
    ButtonModule,
    Toast,
    DialogModule,
    InputTextModule,
    ReactiveFormsModule,
    SelectModule,
  ],
  templateUrl: './ventas-component.html',
  styleUrl: './ventas-component.css',
})
export class VentasComponent {
  optionSelected = input();
  messageService = inject(MessageService);
  productService = inject(ProductService);
  products = linkedSignal(
    toSignal(this.productService.getMyProductsToSell(), {
      initialValue: [],
    })
  );
  visible: boolean = false;
  selectedProductId!: number;

  form: FormGroup;
  formBuilder = inject(FormBuilder);

  showDialog() {
    this.visible = true;
  }

  deleteProduct(id: number) {
    this.productService.deleteProduct(id).subscribe((data: any) => {
      if (data.code === 200) {
        this.messageService.add({
          severity: 'success',
          summary: 'Producto Eliminado',
          detail: 'Eliminado con éxito.',
        });

        this.productService.getMyProductsToSell().subscribe((products: any) => {
          this.products.set(products);
        });
      } else {
        this.messageService.add({
          severity: 'error',
          summary: `Error ${data.code}`,
          detail: data.data,
        });
      }
    });
  }

  updateProduct(id: number) {
    if (!this.selectedProductId || this.form.invalid) return;

    this.form.addControl('id', this.formBuilder.control(id));

    this.productService
      .updateProduct(this.form.value)
      .subscribe((data: any) => {
        if (data.code === 200) {
          this.messageService.add({
            severity: 'success',
            summary: 'Producto Actualizado',
            detail: 'Actualizado con éxito.',
          });

          this.productService
            .getMyProductsToSell()
            .subscribe((products: any) => {
              this.products.set(products);
            });
        } else {
          this.messageService.add({
            severity: 'error',
            summary: `Error ${data.code}`,
            detail: data.data,
          });
        }
        this.visible = false;
      });
  }

  editProduct(product: any) {
    this.form.patchValue({
      name: product.name,
      description: product.description,
      category: product.category,
      price: product.price,
      status: product.status,
    });

    this.selectedProductId = product.id;
    this.visible = true;
  }

  constructor() {
    this.form = this.formBuilder.group({
      name: [''],
      description: [''],
      category: [''],
      price: [''],
      status: [''],
    });

    effect(() => {
      this.products();
    });
  }

  categories: string[] = [
    'Tecnología',
    'Hogar y Jardín',
    'Moda y Accesorios',
    'Motor',
    'Deportes y Ocio',
    'Juguetes y Juegos',
    'Libros, Películas y Música',
    'Bebés y Niños',
    'Herramientas y Bricolaje',
    'Mascotas',
    'Inmobiliaria',
  ];

  states: string[] = ['Nuevo', 'Como Nuevo', 'Usado'];
}
