import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { FacadeService } from '../services/facade.service';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor(private facade: FacadeService) { }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {

    if (this.facade.authGetCurrentUser()) {

      request = request.clone({
        setHeaders: {
          Authorization: 'Bearer ' + this.facade.authGetCurrentUser().token
        }
      })
    }

    return next.handle(request);
  }
}