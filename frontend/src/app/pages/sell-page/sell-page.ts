import { Component, computed, inject, signal } from '@angular/core';
import { Card } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Select } from 'primeng/select';
import { TextareaModule } from 'primeng/textarea';
import { ButtonModule } from 'primeng/button';
import { ProductService } from '../../services/product-service';
import { MessageService } from 'primeng/api';
import { Toast } from "primeng/toast";
import { Router } from '@angular/router';

type Category = {
  title: string;
  subcategories: string[];
};

@Component({
  selector: 'app-sell-page',
  imports: [
    Card,
    InputTextModule,
    FormsModule,
    Select,
    TextareaModule,
    ButtonModule,
    FormsModule,
    ReactiveFormsModule,
    Toast
],
  templateUrl: './sell-page.html',
  styleUrl: './sell-page.css',
})
export class SellPage {
  form: FormGroup;
  formBuilder =  inject(FormBuilder);
  productService = inject(ProductService)
  messageService = inject(MessageService)
  selectedFile: File | null = null;
  router = inject(Router)

  constructor() {
    this.form = this.formBuilder.group({
      name: [''],
      description: [''],
      category: [''],
      price: [''],
      status: [''],
    })
  }

  onSubmit(){
    const price = +this.form.get("price")?.value
    this.form.get("price")?.setValue(price)

    const formData = new FormData();
    const productData = this.form.value;

    formData.append('product', JSON.stringify(productData));
    formData.append('image', this.selectedFile as Blob);
    
    this.productService.createProduct(formData).subscribe((data:any) => {
      if (data.code === 200) {
        
        this.messageService.add({
          severity: 'success',
          summary: 'Producto Creado',
          detail: 'Creado.',
        });

      setTimeout(() => {
          this.router.navigate(['/products']);
        }, 1000);

        
      }else{
        this.messageService.add({
          severity: 'error',
          summary: `Error ${data.code}`,
          detail: data.data,
        });
      }
    })
  }

  onImageSelected(event: Event) {
    const fileInput = event.target as HTMLInputElement;
    if (fileInput.files && fileInput.files.length > 0) {
      this.selectedFile = fileInput.files[0];
    }
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
