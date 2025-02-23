import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { AuthenticationService } from '../services/authentication.service';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor(private _authenticationService: AuthenticationService) { }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {

    if (!request.url.includes('/auth/token')) {
    
      const authentication = this._authenticationService.getAuthentication();

      if (authentication) {

        request = request.clone({
          setHeaders: {
            Authorization: 'Bearer ' + authentication.access_token
          }
        })
      }
    }

    return next.handle(request);
  }
}