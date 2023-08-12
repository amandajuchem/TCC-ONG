import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { environment } from 'src/environments/environment';

import { Authentication } from '../entities/authentication';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private _baseURL: string = environment.apiURL + '/auth';
  private _jwtHelper: JwtHelperService;

  constructor(
    private _http: HttpClient, 
  ) {
    this._jwtHelper = new JwtHelperService();
  }

  getAuthentication(): Authentication {
    const authenticationString = localStorage.getItem("authentication");
    const authentication: Authentication =  authenticationString ? JSON.parse(authenticationString) : null;
    return authentication;
  }

  isAuthenticated() {

    const authentication = this.getAuthentication();

    if (authentication) {

      const token = authentication.access_token.substring(7);

      if (token) {
        return !this._jwtHelper.isTokenExpired(token);
      }
    }

    return false;
  }

  login(user: any) {
    return this._http.post<Authentication>(this._baseURL + '/token', user);
  }

  logout() {
    localStorage.removeItem("authentication");
  }

  setAuthentication(authentication: Authentication) {

    if (localStorage.getItem("authentication")) {
      localStorage.removeItem("authentication");
    }

    authentication.username = this._jwtHelper.decodeToken(authentication.access_token).sub;
    authentication.role = this._jwtHelper.decodeToken(authentication.access_token).scope;
    
    localStorage.setItem("authentication", JSON.stringify(authentication));
  }
}