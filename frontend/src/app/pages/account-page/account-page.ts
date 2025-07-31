import { Component, signal } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { Card } from "primeng/card";
import { Divider } from "primeng/divider";
import { InputTextModule } from 'primeng/inputtext';
import { PerfilComponent } from "../../components/perfil-component/perfil-component";
import { ComprasComponent } from "../../components/compras-component/compras-component";
import { VentasComponent } from "../../components/ventas-component/ventas-component";
import { FavoritosComponent } from "../../components/favoritos-component/favoritos-component";

@Component({
  selector: 'app-account-page',
  imports: [Card, Divider, ButtonModule, PerfilComponent, ComprasComponent, VentasComponent, FavoritosComponent],
  templateUrl: './account-page.html',
  styleUrl: './account-page.css'
})
export class AccountPage {
  options: string[] = ["Mi Perfil", "Mis Compras", "Mis Ventas","Mis Favoritos"]
  optionSelected = ''

  handlerOption(option: string){
    this.optionSelected = option
    console.log(this.optionSelected)
  }

}
