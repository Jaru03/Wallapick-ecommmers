import { Component, effect, inject, input } from '@angular/core';
import { OrderService } from '../../services/order-service';
import { toSignal } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-compras-component',
  imports: [],
  templateUrl: './compras-component.html',
  styleUrl: './compras-component.css'
})
export class ComprasComponent {
  orderService = inject(OrderService);
  optionSelected = input()
  myOrders:any = toSignal(this.orderService.getMyOrders(), { initialValue: [] });

  constructor(){
    effect(() => {
      
      this.myOrders();
      
    });
  }
}
