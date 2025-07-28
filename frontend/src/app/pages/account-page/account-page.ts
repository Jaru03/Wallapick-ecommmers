import { Component } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { Card } from "primeng/card";
import { Divider } from "primeng/divider";
import { InputTextModule } from 'primeng/inputtext';

@Component({
  selector: 'app-account-page',
  imports: [Card, Divider, ButtonModule, InputTextModule],
  templateUrl: './account-page.html',
  styleUrl: './account-page.css'
})
export class AccountPage {
  options: string[] = ["Mi Perfil", "Mis Compras", "Mis Ventas","Mis Favoritos"]
}
