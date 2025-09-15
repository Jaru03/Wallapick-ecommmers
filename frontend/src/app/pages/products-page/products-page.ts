import { Component, inject, linkedSignal, OnInit } from '@angular/core';
import { ProductoComponent } from '../../components/producto-component/producto-component';
import { DividerModule } from 'primeng/divider';
import { FormsModule } from '@angular/forms';
import { Slider } from 'primeng/slider';
import { FloatLabelModule } from "primeng/floatlabel"
import { InputTextModule } from 'primeng/inputtext';
import { Checkbox } from 'primeng/checkbox';
import { Card } from "primeng/card";
import { CommonModule } from '@angular/common';
import { ProductService } from '../../services/product-service';
import { toSignal } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-products-page',
  imports: [ProductoComponent, DividerModule, FormsModule, Slider, FloatLabelModule, InputTextModule, Checkbox, Card, CommonModule],
  templateUrl: './products-page.html',
  styleUrl: './products-page.css',
})
export class ProductsPage{
  rangeValues: number[] = [0, 2000];
  estadoSeleccionado : string[]= []
  isView:boolean = false;
  productsService = inject(ProductService)
  products:any = linkedSignal(toSignal(this.productsService.getAllProducts(), {initialValue: []}))

  filterVisibility(){
    this.isView = !this.isView;
  }

}
