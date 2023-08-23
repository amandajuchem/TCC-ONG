import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { BehaviorSubject } from 'rxjs';
import { environment } from 'src/environments/environment';

import { Authentication } from '../entities/authentication';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  private _baseURL: string = `${environment.apiURL}/auth`;
  private _jwtHelper: JwtHelperService = new JwtHelperService();
  private _subject = new BehaviorSubject<Authentication | null>(null);

  constructor(private _http: HttpClient) {}

  getAuthentication(): Authentication | null {
    if (!this._subject.getValue()) {
      const access_token = localStorage.getItem('access_token');

      if (access_token) {
        const decodedToken = this._jwtHelper.decodeToken(access_token);
        const authentication: Authentication = {
          access_token: access_token,
          username: decodedToken.sub,
          role: decodedToken.scope,
        };

        this._subject.next(authentication);
        return authentication;
      }
    }

    return this._subject.getValue();
  }

  getAuthenticationAsObservable() {
    return this._subject.asObservable();
  }

  hasRoles(roles: Array<string>) {
    const authentication = this.getAuthentication();
    return (
      !!authentication &&
      roles
        .map((role) => role.toLowerCase())
        .includes(authentication.role.toLowerCase())
    );
  }

  isAuthenticated(): boolean {
    const authentication = this.getAuthentication();
    return (
      !!authentication &&
      !this._jwtHelper.isTokenExpired(authentication.access_token)
    );
  }

  token(user: any) {
    return this._http.post<Authentication>(`${this._baseURL}/token`, user);
  }

  logout() {
    localStorage.removeItem('access_token');
    this._subject.next(null);
  }

  setAuthentication(authentication: Authentication) {
    localStorage.setItem('access_token', authentication.access_token);
    this.getAuthentication();
  }
}
