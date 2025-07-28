import { Component, inject } from '@angular/core';
import { Card } from 'primeng/card';
import { FloatLabel } from 'primeng/floatlabel';
import { InputTextModule } from 'primeng/inputtext';
import { Button, ButtonModule } from 'primeng/button';
import { Divider } from 'primeng/divider';
import { RouterModule } from '@angular/router';
import { MessageModule } from 'primeng/message';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';

@Component({
  selector: 'app-login-page',
  imports: [
    Card,
    FloatLabel,
    InputTextModule,
    ButtonModule,
    Divider,
    RouterModule,
    MessageModule,
    ReactiveFormsModule,
  ],
  templateUrl: './login-page.html',
  styleUrl: './login-page.css',
})
export class LoginPage {
  form: FormGroup;
  formBuilder = inject(FormBuilder)

  constructor() {
    this.form = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password:['', [Validators.required, Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,}$/)]]
    });
  }

  onSubmit() {
    console.log(this.form.value);
  }
}
