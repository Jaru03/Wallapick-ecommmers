import { Component, inject } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MenuItem } from 'primeng/api';
import { Menubar } from 'primeng/menubar';
import { LoginService } from '../../services/login-service';
import { ButtonModule } from 'primeng/button';
import { InputIcon } from 'primeng/inputicon';
import { IconField } from 'primeng/iconfield';
import { InputTextModule } from 'primeng/inputtext';
import { BadgeModule } from 'primeng/badge';
import { OverlayBadgeModule } from 'primeng/overlaybadge';

@Component({
  selector: 'app-header',
  imports: [RouterModule,  Menubar, ButtonModule, OverlayBadgeModule, InputIcon, IconField, InputTextModule, BadgeModule],
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
    {icon: "pi-plus-circle", routerLink:"/sell"},
    {icon: "pi-shopping-cart", routerLink:"/cart"},
    {icon: "pi-user", routerLink:"/account"},
    
  ]
}
