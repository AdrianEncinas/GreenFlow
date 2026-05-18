import { Injectable, computed, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { LoginRequest, LoginResponse, RegisterRequest } from '../models/auth.model';
import { environment } from '../../../environments/environment';

const TOKEN_KEY = 'greenflow-token';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly tokenState = signal<string | null>(localStorage.getItem(TOKEN_KEY));

  readonly token = computed(() => this.tokenState());
  readonly isAuthenticated = computed(() => !!this.tokenState());

  constructor(private readonly http: HttpClient, private readonly router: Router) {}

  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http
      .post<LoginResponse>(`${environment.authApiUrl}/api/v1/auth/login`, credentials)
      .pipe(tap((response) => this.setToken(response.token)));
  }

  register(payload: RegisterRequest): Observable<unknown> {
    return this.http.post(`${environment.authApiUrl}/api/v1/users/create`, payload);
  }

  logout(): void {
    localStorage.removeItem(TOKEN_KEY);
    this.tokenState.set(null);
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return this.tokenState();
  }

  private setToken(token: string): void {
    localStorage.setItem(TOKEN_KEY, token);
    this.tokenState.set(token);
  }
}
