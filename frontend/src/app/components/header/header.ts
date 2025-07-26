import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MenuItem } from 'primeng/api';
import { Menubar } from 'primeng/menubar';

@Component({
  selector: 'app-header',
  imports: [RouterModule,  Menubar],
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class Header {
  itemsText: MenuItem[] = [
    {label: "Inicio", routerLink:"/"},
    {label: "Productos", routerLink:"/products"}
  ]
  
  itemsIcons: MenuItem[] = [
    {icon: "pi-heart", routerLink:"/account/favorites"},
    {icon: "pi-shopping-cart", routerLink:"/cart"},
    {icon: "pi-user", routerLink:"/account"},
    
  ]
}
