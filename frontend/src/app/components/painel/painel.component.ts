import { Component } from '@angular/core';
import { Authentication } from 'src/app/entities/authentication';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { RedirectService } from 'src/app/services/redirect.service';

@Component({
  selector: 'app-painel',
  templateUrl: './painel.component.html',
  styleUrls: ['./painel.component.sass'],
})
export class PainelComponent {

  authentication!: Authentication;

  constructor(
    private _authenticationService: AuthenticationService,
    private _redirectService: RedirectService
  ) {
    this._authenticationService.getAuthenticationAsObservable().subscribe({
      next: (authentication) => {
        if (authentication) {
          this.authentication = authentication;
        }
      },
    });
  }

  hasRoles(roles: Array<string>) {
    return this._authenticationService.hasRoles(roles);
  }

  redirectToAgendamentoList() {
    this._redirectService.toAgendamentoList();
  }

  redirectToAnimalList() {
    this._redirectService.toAnimalList();
  }

  redirectToAtendimentoList() {
    this._redirectService.toAtendimentoList();
  }

  redirectToEmpresa() {
    this._redirectService.toEmpresa();
  }

  redirectToExameList() {
    this._redirectService.toExameList();
  }

  redirectToFeiraAdocaoList() {
    this._redirectService.toFeiraAdocaoList();
  }

  redirectToTutorList() {
    this._redirectService.toTutorList();
  }

  redirectToUsuarioList() {
    this._redirectService.toUsuarioList();
  }
}
