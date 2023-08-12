import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

import { Authentication } from '../entities/authentication';
import { AuthenticationService } from './authentication.service';

@Injectable({
  providedIn: 'root',
})
export class RedirectService {
  private _authentication!: Authentication;
  private _baseURL!: string;

  constructor(
    private _authenticationService: AuthenticationService,
    private _router: Router
  ) {
    this._authentication = this._authenticationService.getAuthentication();
    this._baseURL = '/' + this._authentication.role.toLowerCase();
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
