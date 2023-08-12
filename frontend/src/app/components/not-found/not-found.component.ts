import { Component } from '@angular/core';
import { RedirectService } from 'src/app/services/redirect.service';

@Component({
  selector: 'app-not-found',
  templateUrl: './not-found.component.html',
  styleUrls: ['./not-found.component.sass'],
})
export class NotFoundComponent {
  constructor(private _redirectService: RedirectService) {}

  redirectToPanel() {
    this._redirectService.toPainel();
  }
}
