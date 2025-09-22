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
  categorySelect = signal<string>('');
  form: FormGroup;
  formBuilder =  inject(FormBuilder);
  productService = inject(ProductService)
  messageService = inject(MessageService)
  selectedFile: File | null = null;

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

    for (const key of formData.keys()) {
    console.log(`${key}: ${formData.get(key)}`);
  }
    
    this.productService.createProduct(formData).subscribe((data:any) => {
      console.log(data);
      if (data.code === 200) {
        
        this.messageService.add({
          severity: 'success',
          summary: 'Producto Creado',
          detail: 'Creado.',
        });
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

  subcategories = signal<Category[]>([
    {
      title: 'Tecnología',
      subcategories: [
        'Ordenadores y Portátiles',
        'Teléfonos Móviles',
        'Tablets',
        'Accesorios y Periféricos',
        'Consolas y Videojuegos',
        'Componentes',
        'Smartwatches y Wearables',
        'Equipos de Red',
      ],
    },
    {
      title: 'Hogar y Jardín',
      subcategories: [
        'Muebles',
        'Electrodomésticos',
        'Decoración',
        'Jardinería',
        'Iluminación',
        'Climatización',
      ],
    },
    {
      title: 'Moda y Accesorios',
      subcategories: [
        'Ropa Hombre',
        'Ropa Mujer',
        'Calzado',
        'Bolsos y Mochilas',
        'Relojes y Joyería',
        'Gafas de Sol',
      ],
    },
    {
      title: 'Motor',
      subcategories: [
        'Coches',
        'Motos',
        'Recambios y Accesorios',
        'Herramientas de Taller',
        'Neumáticos',
        'Navegadores y Radios',
      ],
    },
    {
      title: 'Deportes y Ocio',
      subcategories: [
        'Bicicletas',
        'Equipamiento Deportivo',
        'Camping y Montaña',
        'Pesca y Caza',
        'Fitness',
        'Patinetes y Scooters',
      ],
    },
    {
      title: 'Juguetes y Juegos',
      subcategories: [
        'Juguetes para Niños',
        'Juegos de Mesa',
        'LEGO y Puzzles',
        'Figuras y Muñecos',
        'Control Remoto y Drones',
      ],
    },
    {
      title: 'Libros, Películas y Música',
      subcategories: [
        'Libros',
        'Cómics',
        'Vinilos y CDs',
        'Películas y Series',
        'Instrumentos Musicales',
      ],
    },
    {
      title: 'Bebés y Niños',
      subcategories: [
        'Ropa de Bebé',
        'Carros y Sillas',
        'Juguetes',
        'Cunas y Mobiliario',
        'Alimentación y Lactancia',
      ],
    },
    {
      title: 'Herramientas y Bricolaje',
      subcategories: [
        'Herramientas Eléctricas',
        'Manual',
        'Materiales de Construcción',
        'Pintura y Decoración',
      ],
    },
    {
      title: 'Mascotas',
      subcategories: [
        'Perros',
        'Gatos',
        'Jaulas y Terrarios',
        'Accesorios',
        'Alimentos',
        'Otros Animales',
      ],
    },
    {
      title: 'Inmobiliaria',
      subcategories: [
        'Pisos',
        'Casas',
        'Garajes',
        'Trasteros',
        'Habitaciones',
        'Locales Comerciales',
      ],
    },
  ]);

  subcategoriesSelected = computed(() => {
    const category = this.categorySelect();
    const match = this.subcategories().find((c) => c.title === category);
    return match?.subcategories ?? [];
  });

  states: string[] = ['Nuevo', 'Como Nuevo', 'Usado'];
}
