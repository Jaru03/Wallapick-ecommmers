import { Component, computed, signal } from '@angular/core';
import { Card } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';
import { Select } from 'primeng/select';
import { TextareaModule } from 'primeng/textarea';
import { ButtonModule } from 'primeng/button';

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
  ],
  templateUrl: './sell-page.html',
  styleUrl: './sell-page.css',
})
export class SellPage {
  categorySelect = signal<string>('');

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
