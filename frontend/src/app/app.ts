import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Header } from './components/header/header';
import { Footer } from './components/footer/footer';
import { ScrollTop } from 'primeng/scrolltop';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Header, Footer, ScrollTop],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected title = 'wallapick';
}
