import { Component, input } from '@angular/core';
import { DataView } from 'primeng/dataview';
import { ButtonModule } from 'primeng/button';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-producto-component',
  imports: [DataView, ButtonModule, CommonModule, RouterModule],
  templateUrl: './producto-component.html',
  styleUrl: './producto-component.css',
})
export class ProductoComponent {
  layout = input<'grid' | 'list'>('grid');

  
  product = input<any>([{
    id: '1000',
    name: 'Bamboo Watch',
    description: 'Product Description',
    price: 65,
    category: 'Accessories',
    quantity: 24,
    inventoryStatus: 'INSTOCK',
    rating: 5,
    image: 'bamboo-watch.jpg',
  }]);

  options: string[] = ['list', 'grid'];

}
