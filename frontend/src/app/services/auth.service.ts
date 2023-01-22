import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { environment } from 'src/environments/environment';

import { User } from '../entities/user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private _jwtHelper!: JwtHelperService;

  constructor(
    private _http: HttpClient, 
  ) {
    this._jwtHelper = new JwtHelperService();
  }

  getCurrentUser() {
    
    let user!: User;

    try {
      
      let currentUser: any = localStorage.getItem("user");
      currentUser = JSON.parse(currentUser);

      user = {
        username: this._jwtHelper.decodeToken(currentUser.token).sub,
        access_token: currentUser.token,
        role: currentUser.role
      };

    } catch (error) { }

    return user;
  }

  isAuthenticated() {
    const user = this.getCurrentUser();

    if (user) {

      const token = user.access_token.substring(7);

      if (token) {
        return !this._jwtHelper.isTokenExpired(token);
      }
    }

    return false;
  }

  login(user: any) {
    return this._http.post<any>(environment.apiURL + '/login', user);
  }

  logout() {
    localStorage.removeItem("user");
  }

  setCurrentUser(user: any) {

    if (localStorage.getItem("user")) {
      localStorage.removeItem("user");
    }

    localStorage.setItem("user", JSON.stringify(user));
  }
}