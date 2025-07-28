import { Component, inject } from '@angular/core';
import { Divider } from 'primeng/divider';
import { FloatLabel } from 'primeng/floatlabel';
import { Card } from 'primeng/card';
import { InputText } from 'primeng/inputtext';
import { Router, RouterModule } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  ValidationErrors,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { Message } from "primeng/message";

@Component({
  selector: 'app-register-page',
  imports: [Divider, FloatLabel, Card, InputText, RouterModule, ButtonModule, ReactiveFormsModule, Message],
  templateUrl: './register-page.html',
  styleUrl: './register-page.css',
})
export class RegisterPage {
  form: FormGroup;
  formBuilder = inject(FormBuilder);
  route = inject(Router)

  constructor() {
    this.form = this.formBuilder.group(
      {
        firstName: ['', [Validators.required, Validators.minLength(3)]],
        lastName: ['', [Validators.required, Validators.minLength(3)]],
        email: ['', [Validators.required, Validators.email]],
        password: [
          '',
          [
            Validators.required,
            Validators.pattern(
              /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,}$/
            ),
          ],
        ],
        confirmPassword: ['', [Validators.required]],
      },
      {
        validators: (group: AbstractControl) => {
          const password = group.get('password')?.value;
          const confirm = group.get('confirmPassword')?.value;
          return password === confirm ? null : { passwordsDontMatch: true };
        },
      }
    );
  }

  onSubmit(){
    console.log(this.form.value)
    this.form.reset()
    this.route.navigate(['/login'])

  }
}
