import { Component, computed, effect, inject, linkedSignal } from '@angular/core';
import { ProductoComponent } from '../../components/producto-component/producto-component';
import { CommonModule } from '@angular/common';
import { CarouselModule } from 'primeng/carousel';
import { ProductService } from '../../services/product-service';
import { toSignal } from '@angular/core/rxjs-interop';
import { RouterLink } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { Hero } from "../../components/hero/hero";
import { Carousels } from "../../components/carousels/carousels";
import { Opinions } from "../../components/opinions/opinions";

@Component({
  selector: 'app-home-page',
  imports: [Hero, Carousels, Opinions],
  templateUrl: './home-page.html',
  styleUrl: './home-page.css',
})
export class HomePage {  
}
