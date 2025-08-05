import { Component, inject } from '@angular/core';
import { Divider } from 'primeng/divider';
import { FloatLabel } from 'primeng/floatlabel';
import { Card } from 'primeng/card';
import { InputText } from 'primeng/inputtext';
import { Router, RouterModule } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { MessageService } from 'primeng/api';
import { Toast } from 'primeng/toast';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Message } from 'primeng/message';
import { LoginService } from '../../services/login-service';

@Component({
  selector: 'app-register-page',
  imports: [
    Divider,
    FloatLabel,
    Card,
    InputText,
    RouterModule,
    ButtonModule,
    ReactiveFormsModule,
    Message,
    Toast,
  ],
  templateUrl: './register-page.html',
  styleUrl: './register-page.css',
  providers: [MessageService],
})
export class RegisterPage {
  form: FormGroup;
  formBuilder = inject(FormBuilder);
  route = inject(Router);
  auth = inject(LoginService);
  messageService = inject(MessageService);

  constructor() {
    this.form = this.formBuilder.group(
      {
        name: ['', [Validators.required, Validators.minLength(3)]],
        lastname: ['', [Validators.required, Validators.minLength(3)]],
        username: ['', [Validators.required, Validators.minLength(3)]],
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

  onSubmit() {
    console.log(this.form.value);

    this.auth.register(this.form.value).subscribe((data: any) => {
      if (data.codigo === 200) {
        this.messageService.add({
          severity: 'success',
          summary: 'Registrado Correctamente',
          detail: 'Redirigiendo al login...',
        });
        setTimeout(() => {
          this.route.navigate(['/login']);
        }, 1000);
      }
    });
  }
}
