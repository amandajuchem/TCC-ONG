import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private jwtHelper: JwtHelperService;

  constructor(private http: HttpClient) {
    this.jwtHelper = new JwtHelperService();
  }

  getCurrentUser() {
    let currentUser: any = localStorage.getItem("currentUser");

    try {
      currentUser = JSON.parse(currentUser);
      currentUser.username = this.jwtHelper.decodeToken(currentUser.token).sub;
    } catch (error) { }

    return currentUser;
  }

  isAuthenticated() {
    const currentUser = this.getCurrentUser();

    if (currentUser) {

      const token = currentUser.token.substring(7);

      if (token) {
        return !this.jwtHelper.isTokenExpired(token);
      }
    }

    return false;
  }

  login(user: any) {
    return this.http.post<any>(environment.apiURL + '/login', user);
  }

  logout() {
    localStorage.removeItem("currentUser");
  }

  setCurrentUser(currentUser: any) {

    if (localStorage.getItem("currentUser")) {
      localStorage.removeItem("currentUser");
    }

    localStorage.setItem("currentUser", JSON.stringify(currentUser));
  }
}