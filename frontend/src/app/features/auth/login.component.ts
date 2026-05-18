import { ChangeDetectionStrategy, Component } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { finalize, switchMap } from 'rxjs';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LoginComponent {
  readonly form;
  isRegisterMode = false;

  loading = false;
  error = '';

  constructor(
    private readonly fb: FormBuilder,
    private readonly authService: AuthService,
    private readonly router: Router,
  ) {
    this.form = this.fb.nonNullable.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]],
      confirmPassword: [''],
    });
  }

  toggleMode(): void {
    this.isRegisterMode = !this.isRegisterMode;
    this.error = '';
    this.form.controls.confirmPassword.setValue('');
  }

  setMode(isRegisterMode: boolean): void {
    this.isRegisterMode = isRegisterMode;
    this.error = '';
    this.form.controls.confirmPassword.setValue('');
  }

  useDemoAccess(): void {
    this.setMode(false);
    this.form.setValue({
      username: 'admin',
      password: 'admin123',
      confirmPassword: '',
    });
  }

  submit(): void {
    if (this.form.invalid || this.loading) {
      this.form.markAllAsTouched();
      return;
    }

    if (this.isRegisterMode && this.form.controls.password.value !== this.form.controls.confirmPassword.value) {
      this.error = 'Las contrasenas no coinciden.';
      return;
    }

    this.loading = true;
    this.error = '';

    const credentials = {
      username: this.form.controls.username.value,
      password: this.form.controls.password.value,
    };

    const request$ = this.isRegisterMode
      ? this.authService
          .register({
            ...credentials,
            role: 'ROLE_USER',
          })
          .pipe(switchMap(() => this.authService.login(credentials)))
      : this.authService.login(credentials);

    request$
      .pipe(finalize(() => (this.loading = false)))
      .subscribe({
        next: () => this.router.navigate(['/dashboard']),
        error: (error: HttpErrorResponse) => {
          if (error.status === 401) {
            this.error = 'Credenciales invalidas. Si no tienes cuenta, registrate primero.';
            return;
          }

          if (error.status === 409) {
            this.error = 'El nombre de usuario ya existe. Usa otro.';
            return;
          }

          this.error = 'No fue posible procesar la solicitud. Verifica que auth-service este activo.';
        },
      });
  }
}
