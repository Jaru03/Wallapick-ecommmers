import { Component, inject } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MenuItem } from 'primeng/api';
import { Menubar } from 'primeng/menubar';
import { LoginService } from '../../services/login-service';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-header',
  imports: [RouterModule,  Menubar, ButtonModule],
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class Header {
  loginService = inject(LoginService)
  
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
