import { Component } from '@angular/core';
import { ProductoComponent } from '../../components/producto-component/producto-component';
import { DividerModule } from 'primeng/divider';
import { FormsModule } from '@angular/forms';
import { Slider } from 'primeng/slider';
import { FloatLabelModule } from "primeng/floatlabel"
import { InputTextModule } from 'primeng/inputtext';
import { Checkbox } from 'primeng/checkbox';
import { Card } from "primeng/card";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-products-page',
  imports: [ProductoComponent, DividerModule, FormsModule, Slider, FloatLabelModule, InputTextModule, Checkbox, Card, CommonModule],
  templateUrl: './products-page.html',
  styleUrl: './products-page.css',
})
export class ProductsPage {
  rangeValues: number[] = [0, 2000];
  estadoSeleccionado : string[]= []
  isView:boolean = false;

  filterVisibility(){
    this.isView = !this.isView;
  }
}
