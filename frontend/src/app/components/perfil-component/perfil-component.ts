import { Component, effect, inject, input, output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { LoginService } from '../../services/login-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-perfil-component',
  imports: [ButtonModule, InputTextModule, ReactiveFormsModule],
  templateUrl: './perfil-component.html',
  styleUrl: './perfil-component.css',
})
export class PerfilComponent {
  form: FormGroup;
  formBuilder = inject(FormBuilder);
  userService = inject(LoginService);
  route = inject(Router);

  optionSelected = input();
  userData = input<any>();
  userDataUpdated = output<any>();

  isEditing = false;
  constructor() {
    this.form = this.formBuilder.group({
      name: [''],
      lastname: [''],
      email: [''],
      password: [''],
    });
  }

  toggleEdit() {
    this.isEditing = !this.isEditing;

    console.log(this.userData(), this.optionSelected());
  }

  updateData() {
    const formValues = this.form.value;
    const cleanedData: { [key: string]: any } = {};

    Object.keys(formValues).forEach((key) => {
      const value = formValues[key];
      if (value !== null && value !== undefined && value !== '') {
        cleanedData[key] = value;
      }
    });

    cleanedData['id'] = this.userData().id;

    this.userService.updateUser(cleanedData).subscribe((response: any) => {
      console.log(response.code, cleanedData);
      if (response.code === 200) {
        this.userDataUpdated.emit(response.data)
        this.isEditing = false;
      }
    });
  }
}
