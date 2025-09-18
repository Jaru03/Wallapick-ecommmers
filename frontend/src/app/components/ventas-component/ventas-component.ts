import { Component, effect, inject, input } from '@angular/core';
import { ProductService } from '../../services/product-service';
import { toSignal } from '@angular/core/rxjs-interop';
import { ProductoComponent } from "../producto-component/producto-component";
import { Button, ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-ventas-component',
  imports: [ ButtonModule],
  templateUrl: './ventas-component.html',
  styleUrl: './ventas-component.css'
})
export class VentasComponent {
optionSelected = input()
productService = inject(ProductService);
products = toSignal(this.productService.getMyProductsToSell(), { initialValue: [] }); 

constructor() { 
  effect(() => {
    console.log(this.products());
  });
}
}
