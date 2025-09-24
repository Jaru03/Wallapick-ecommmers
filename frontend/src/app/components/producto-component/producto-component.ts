import { Component, inject, Input, input } from '@angular/core';
import { DataView } from 'primeng/dataview';
import { ButtonModule } from 'primeng/button';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { LoginService } from '../../services/login-service';
import { ProductService } from '../../services/product-service';
import { StripeService } from '../../services/stripe-service';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { Toast } from 'primeng/toast';
import { Dialog, DialogModule } from 'primeng/dialog';
import { SelectModule } from 'primeng/select';
import { InputTextModule } from 'primeng/inputtext';

@Component({
  selector: 'app-producto-component',
  imports: [DataView, ButtonModule, CommonModule, RouterModule, ReactiveFormsModule, DialogModule, SelectModule, InputTextModule, Toast],
  templateUrl: './producto-component.html',
  styleUrl: './producto-component.css',
})
export class ProductoComponent {
  layout = input<'grid' | 'list'>('grid');
  loginService = inject(LoginService);
  productService = inject(ProductService);
  stripeService = inject(StripeService);
  isLogged = this.loginService.userLogged();
  cart = this.productService.cart;
  router = inject(Router);
  messageService = inject(MessageService);

  userId = this.loginService.token()?.id;
  dataUser!: any;
  dataUser$ = this.loginService.dataUser(this.userId).subscribe((data:any) => {
    if(data.code === 200){
      this.dataUser = data.data;
    }
    console.log(data);
  })

  visible: boolean = false;
  selectedProductId!: number;

  form: FormGroup;
  formBuilder = inject(FormBuilder);

  constructor() {
    this.form = this.formBuilder.group({
      name: [''],
      description: [''],
      category: [''],
      price: [''],
      status: [''],
    });
  }

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
    console.log(this.form.value);

    this.productService
      .updateProduct(this.form.value)
      .subscribe((data: any) => {
        if (data.code === 200) {
          this.messageService.add({
            severity: 'success',
            summary: 'Producto Actualizado',
            detail: 'Actualizado con éxito.',
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

  onBuy() {
    if (!this.isLogged) {
      this.router.navigate(['/login']);
    }

    this.stripeService.getSession(this.product()).subscribe((data: any) => {
      if (data.code === 200) {
        localStorage.setItem('cart', JSON.stringify(this.product()));
        window.location.href = data.data;
        console.log(data.data);
      }
    });
  }

  addCart(product: any) {
    if (!this.isLogged) {
      this.router.navigate(['/login']);
    }

    this.cart.update((data) => [...data, product]);
  }

  product = input<any>([
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
  ]);

  options: string[] = ['list', 'grid'];

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
