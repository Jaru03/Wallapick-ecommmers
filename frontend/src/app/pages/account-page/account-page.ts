import { Component, inject, signal } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { Card } from "primeng/card";
import { Divider } from "primeng/divider";
import { InputTextModule } from 'primeng/inputtext';
import { PerfilComponent } from "../../components/perfil-component/perfil-component";
import { ComprasComponent } from "../../components/compras-component/compras-component";
import { VentasComponent } from "../../components/ventas-component/ventas-component";
import { LoginService } from '../../services/login-service';
import { Router } from '@angular/router';
import { toSignal } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-account-page',
  imports: [Card, Divider, ButtonModule, PerfilComponent, ComprasComponent, VentasComponent],
  templateUrl: './account-page.html',
  styleUrl: './account-page.css'
})
export class AccountPage {
  options: string[] = ["Mi Perfil", "Mis Compras", "Mis Ventas"]
  optionSelected = ''
  loginService = inject(LoginService);
  router = inject(Router)

  handlerOption(option: string){
    this.optionSelected = option
    console.log(this.optionSelected)
  }

  logOut(){
    this.loginService.logout().subscribe();
    this.router.navigate(['/'])
  }

  userId = this.loginService.token().id;
  dataUser!: any;
  dataUser$ = this.loginService.dataUser(this.userId).subscribe((data:any) => {
    if(data.code === 200){
      this.dataUser = data.data;
    }
    console.log(data);
  })

  handleUpdateUserData(updatedData: any) {
    this.dataUser = updatedData;
  }


}
