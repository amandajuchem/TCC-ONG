import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { AuthService } from '../services/auth.service';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor(private _authService: AuthService) { }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {

    let user = this._authService.getCurrentUser();

    if (user) {

      request = request.clone({
        setHeaders: {
          Authorization: 'Bearer ' + user.access_token
        }
      })
    }

    return next.handle(request);
  }
}