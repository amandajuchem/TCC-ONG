import { Injectable } from '@angular/core';

import { Animal } from '../entities/animal';
import { Atendimento } from '../entities/atendimento';
import { Exame } from '../entities/exame';
import { Tutor } from '../entities/tutor';
import { Usuario } from '../entities/usuario';
import { NotificationType } from '../enums/notification-type';
import { AgendamentoService } from './agendamento.service';
import { AnimalService } from './animal.service';
import { AtendimentoService } from './atendimento.service';
import { AuthService } from './auth.service';
import { ExameService } from './exame.service';
import { ImagemService } from './imagem.service';
import { NotificationService } from './notification.service';
import { TutorService } from './tutor.service';
import { UsuarioService } from './usuario.service';
import { Agendamento } from '../entities/agendamento';
import {Adocao} from "../entities/adocao";

@Injectable({
  providedIn: 'root'
})
export class FacadeService {

  constructor(
    private _agendamentoService: AgendamentoService,
    private _animalService: AnimalService,
    private _atendimentoService: AtendimentoService,
    private _authService: AuthService,
    private _exameService: ExameService,
    private _imagemService: ImagemService,
    private _notificationService: NotificationService,
    private _tutorService: TutorService,
    private _usuarioService: UsuarioService
  ) { }

  ////////////////////////////////////////////////// AGENDAMENTO //////////////////////////////////////////////////

  /**
   *
   * @param id
   * @returns
   */
  agendamentoDelete(id: string) {
    return this._agendamentoService.delete(id);
  }

  /**
   *
   * @returns
   */
  agendamentoFindAll(page: number, size: number, sort: string, direction: string) {
    return this._agendamentoService.findAll(page, size, sort, direction);
  }

  /**
   *
   * @param agendamento
   * @returns
   */
  agendamentoSave(agendamento: Agendamento) {
    return this._agendamentoService.save(agendamento);
  }

  /**
   *
   * @param value
   * @param page
   * @param size
   * @param sort
   * @param direction
   * @returns
   */
  agendamentoSearch(value: string, page: number, size: number, sort: string, direction: string) {
    return this._agendamentoService.search(value, page, size, sort, direction);
  }

  /**
   *
   * @param agendamento
   * @returns
   */
  agendamentoUpdate(agendamento: Agendamento) {
    return this._agendamentoService.update(agendamento);
  }

  ////////////////////////////////////////////////// ANIMAL //////////////////////////////////////////////////

  animalAdocaoDelete(id: string, idAdocao: string) {
    return this._animalService.adocaoDelete(id, idAdocao);
  }

  animalAdocaoFindAll(id: string, page: number, size: number, sort: string, direction: string) {
    return this._animalService.adocaoFindAll(id, page, size, sort, direction);
  }

  animalAdocaoSave(animal: Animal, adocao: Adocao) {
    return this._animalService.adocaoSave(animal, adocao);
  }

  animalAdocaoUpate(animal: Animal, adocao: Adocao) {
    return this._animalService.adocaoUpdate(animal, adocao);
  }

  /**
   *
   * @param id
   * @returns
   */
  animalDelete(id: string) {
    return this._animalService.delete(id);
  }

  /**
   *
   * @param page
   * @param size
   * @param sort
   * @param direction
   * @returns
   */
  animalFindAll(page: number, size: number, sort: string, direction: string) {
    return this._animalService.findAll(page, size, sort, direction);
  }

  /**
   *
   * @param id
   * @returns
   */
  animalFindById(id: string) {
    return this._animalService.findById(id);
  }

  /**
   *
   * @returns
   */
  animalGet() {
    return this._animalService.get();
  }

  /**
   *
   * @param animal
   * @param novaFoto
   * @returns
   */
  animalSave(animal: Animal, foto: any) {
    return this._animalService.save(animal, foto);
  }

  /**
   *
   * @param value
   * @param page
   * @param size
   * @param sort
   * @param direction
   * @returns
   */
  animalSearch(value: string, page: number, size: number, sort: string, direction: string) {
    return this._animalService.search(value, page, size, sort, direction);
  }

  /**
   *
   * @param animal
   */
  animalSet(animal: Animal) {
    this._animalService.set(animal);
  }

  /**
   *
   * @param animal
   * @param novaFoto
   * @param antigaFoto
   * @returns
   */
  animalUpdate(animal: Animal, novaFoto: any) {
    return this._animalService.update(animal, novaFoto);
  }

  ////////////////////////////////////////////////// ATENDIMENTO //////////////////////////////////////////////////

  /**
   *
   * @param id
   * @returns
   */
  atendimentoDelete(id: string) {
    return this._atendimentoService.delete(id);
  }

  /**
   *
   * @param page
   * @param size
   * @param sort
   * @param direction
   * @returns
   */
  atendimentoFindAll(page: number, size: number, sort: string, direction: string) {
    return this._atendimentoService.findAll(page, size, sort, direction);
  }

  /**
   *
   * @param atendimento
   * @param documentosToSave
   * @returns
   */
  atendimentoSave(atendimento: Atendimento, documentosToSave: Array<File> | null) {
    return this._atendimentoService.save(atendimento, documentosToSave);
  }

  /**
   *
   * @param value
   * @param page
   * @param size
   * @param sort
   * @param direction
   * @returns
   */
  atendimentoSearch(value: string, page: number, size: number, sort: string, direction: string) {
    return this._atendimentoService.search(value, page, size, sort, direction);
  }

  /**
   *
   * @param atendimento
   * @param documentosToSave
   * @param documentosToDelete
   * @returns
   */
  atendimentoUpdate(atendimento: Atendimento, documentosToSave: Array<File> | null, documentosToDelete: Array<string> | null) {
    return this._atendimentoService.update(atendimento, documentosToSave, documentosToDelete);
  }

  ////////////////////////////////////////////////// AUTHENTICATION //////////////////////////////////////////////////

  /**
    *
    * @returns
    */
  authIsAuthenticated() {
    return this._authService.isAuthenticated();
  }

  /**
   *
   * @param user
   * @returns
   */
  authLogin(user: any) {
    return this._authService.login(user);
  }

  /**
   *
   * @returns
   */
  authLogout() {
    return this._authService.logout();
  }

  /**
   *
   * @returns
   */
  authGetCurrentUser() {
    return this._authService.getCurrentUser();
  }

  /**
   *
   * @param currentUser
   * @returns
   */
  authSetCurrentUser(currentUser: any) {
    return this._authService.setCurrentUser(currentUser);
  }

  ////////////////////////////////////////////////// EXAME //////////////////////////////////////////////////

  /**
   *
   * @param id
   * @returns
   */
  exameDelete(id: string) {
    return this._exameService.delete(id);
  }


  /**
   *
   * @param page
   * @param size
   * @param sort
   * @param direction
   * @returns
   */
  exameFindAll(page: number, size: number, sort: string, direction: string) {
    return this._exameService.findAll(page, size, sort, direction);
  }

  /**
   *
   * @param exame
   * @returns
   */
  exameSave(exame: Exame) {
    return this._exameService.save(exame);
  }

  /**
   *
   * @param value
   * @param page
   * @param size
   * @param sort
   * @param direction
   * @returns
   */
  exameSearch(value: string, page: number, size: number, sort: string, direction: string) {
    return this._exameService.search(value, page, size, sort, direction);
  }

  /**
   *
   * @param exame
   * @returns
   */
  exameUpdate(exame: Exame) {
    return this._exameService.update(exame);
  }

  ////////////////////////////////////////////////// IMAGEM //////////////////////////////////////////////////

  /**
   *
   * @param imagem
   * @returns
   */
  imagemToBase64(imagem: any) {
    return this._imagemService.toBase64(imagem);
  }

  ////////////////////////////////////////////////// NOTIFICATION //////////////////////////////////////////////////

  /**
   *
   * @param message
   * @param type
   */
  notificationShowNotification(message: string, type: NotificationType) {
    this._notificationService.show(message, type);
  }

  ////////////////////////////////////////////////// TUTOR //////////////////////////////////////////////////

  /**
   *
   * @param id
   * @returns
   */
  tutorDelete(id: string) {
    return this._tutorService.delete(id);
  }

  /**
   *
   * @param page
   * @param size
   * @param sort
   * @param direction
   * @returns
   */
  tutorFindAll(page: number, size: number, sort: string, direction: string) {
    return this._tutorService.findAll(page, size, sort, direction);
  }

  /**
   *
   * @param id
   * @returns
   */
  tutorFindById(id: string) {
    return this._tutorService.findById(id);
  }

  /**
   *
   * @returns
   */
  tutorGet() {
    return this._tutorService.get();
  }

  /**
   *
   * @param tutor
   * @param novaFoto
   * @returns
   */
  tutorSave(tutor: Tutor, novaFoto: any) {
    return this._tutorService.save(tutor, novaFoto);
  }

  /**
   *
   * @param value
   * @param page
   * @param size
   * @param sort
   * @param direction
   * @returns
   */
  tutorSearch(value: string, page: number, size: number, sort: string, direction: string) {
    return this._tutorService.search(value, page, size, sort, direction);
  }

  /**
   *
   * @param tutor
   */
  tutorSet(tutor: Tutor) {
    this._tutorService.set(tutor);
  }

  /**
   *
   * @param tutor
   * @param novaFoto
   * @param antigaFoto
   * @returns
   */
  tutorUpdate(tutor: Tutor, novaFoto: any, antigaFoto: any) {
    return this._tutorService.update(tutor, novaFoto, antigaFoto);
  }

  ////////////////////////////////////////////////// USUÁRIO //////////////////////////////////////////////////

  /**
   *
   * @param page
   * @param size
   * @param sort
   * @param direction
   * @returns
   */
  usuarioFindAll(page: number, size: number, sort: string, direction: string) {
    return this._usuarioService.findAll(page, size, sort, direction);
  }

  /**
   *
   * @param id
   * @returns
   */
  usuarioFindById(id: string) {
    return this._usuarioService.findById(id);
  }

  /**
   *
   * @returns
   */
  usuarioGet() {
    return this._usuarioService.get();
  }

  /**
   *
   * @param usuario
   * @param novaFoto
   * @param antigaFoto
   * @returns
   */
  usuarioSave(usuario: Usuario, novaFoto: any) {
    return this._usuarioService.save(usuario, novaFoto);
  }

  /**
   *
   * @param value
   * @param page
   * @param size
   * @param sort
   * @param direction
   * @returns
   */
  usuarioSearch(value: string, page: number, size: number, sort: string, direction: string) {
    return this._usuarioService.search(value, page, size, sort, direction);
  }

  /**
   *
   * @param usuario
   */
  usuarioSet(usuario: Usuario) {
    this._usuarioService.set(usuario);
  }

  /**
   *
   * @param usuario
   * @param novaFoto
   * @param antigaFoto
   * @returns
   */
  usuarioUpdate(usuario: Usuario, novaFoto: any, antigaFoto: any) {
    return this._usuarioService.update(usuario, novaFoto, antigaFoto);
  }
}
