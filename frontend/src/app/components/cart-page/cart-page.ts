import { Component } from '@angular/core';
import { ProductoComponent } from "../producto-component/producto-component";
import { CardModule } from 'primeng/card';
import { Divider } from "primeng/divider";
import { Button } from "primeng/button";
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-cart-page',
  imports: [ProductoComponent, CardModule, Divider, Button, RouterModule],
  templateUrl: './cart-page.html',
  styleUrl: './cart-page.css'
})
export class CartPage {

}
