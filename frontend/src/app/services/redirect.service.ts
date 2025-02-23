import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

import { Authentication } from '../entities/authentication';
import { Empresa } from '../entities/empresa';
import { AuthenticationService } from './authentication.service';
import { EmpresaService } from './empresa.service';

@Injectable({
  providedIn: 'root',
})
export class RedirectService {
  private _authentication!: Authentication;
  private _baseURL!: string;
  private _empresa!: Empresa;

  constructor(
    private _authenticationService: AuthenticationService,
    private _empresaService: EmpresaService,
    private _router: Router
  ) {
    this._authenticationService.getAuthenticationAsObservable().subscribe({
      next: (authentication) => {
        if (authentication) {
          this._authentication = authentication;
          this._baseURL = '/' + this._authentication.role.toLowerCase();
        }
      },
    });

    this._empresaService.get().subscribe({
      next: (empresa) => {
        if (empresa) {
          this._empresa = empresa;
        }
      }
    });
  }

  toAgendamentoList() {
    this._router.navigate([this._baseURL + '/agendamentos']);
  }

  toAgendamento(id: string) {
    this._router.navigate([this._baseURL + '/agendamentos/' + id]);
  }

  toAnimalList() {
    this._router.navigate([this._baseURL + '/animais']);
  }

  toAnimal(id: string) {
    this._router.navigate([this._baseURL + '/animais/' + id]);
  }

  toAtendimentoList() {
    this._router.navigate([this._baseURL + '/atendimentos']);
  }

  toAtendimento(id: string) {
    this._router.navigate([this._baseURL + '/atendimentos/' + id]);
  }

  toEmpresa() {
    this._router.navigate([this._baseURL + '/empresas/' + this._empresa.id]);
  }

  toExameList() {
    this._router.navigate([this._baseURL + '/exames']);
  }

  toExame(id: string) {
    this._router.navigate([this._baseURL + '/exames/' + id]);
  }

  toFeiraAdocaoList() {
    this._router.navigate([this._baseURL + '/feiras-adocao']);
  }

  toFeiraAdocao(id: string) {
    this._router.navigate([this._baseURL + '/feiras-adocao/' + id]);
  }

  toLogin() {
    this._router.navigate(['']);
  }

  toPainel() {
    this._router.navigate([this._baseURL + '/painel']);
  }

  toTutorList() {
    this._router.navigate([this._baseURL + '/tutores']);
  }

  toTutor(id: string) {
    this._router.navigate([this._baseURL + '/tutores/' + id]);
  }

  toUsuarioList() {
    this._router.navigate([this._baseURL + '/usuarios']);
  }

  toUsuario(id: string) {
    this._router.navigate([this._baseURL + '/usuarios/' + id]);
  }
}
