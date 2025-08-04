import { Component, input } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';

@Component({
  selector: 'app-perfil-component',
  imports: [ButtonModule, InputTextModule],
  templateUrl: './perfil-component.html',
  styleUrl: './perfil-component.css'
})
export class PerfilComponent {
  optionSelected = input()
}
