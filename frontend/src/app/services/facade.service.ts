import { Injectable } from '@angular/core';

import { Animal } from '../entities/animal';
import { Usuario } from '../entities/usuario';
import { NotificationType } from '../enums/notification-type';
import { AnimalService } from './animal.service';
import { AuthService } from './auth.service';
import { ImagemService } from './imagem.service';
import { NotificationService } from './notification.service';
import { UsuarioService } from './usuario.service';
import { TutorService } from './tutor.service';
import { Tutor } from '../entities/tutor';

@Injectable({
  providedIn: 'root'
})
export class FacadeService {

  constructor(
    private _animalService: AnimalService,
    private _authService: AuthService,
    private _imagemService: ImagemService,
    private _notificationService: NotificationService,
    private _tutorService: TutorService,
    private _usuarioService: UsuarioService
  ) { }

  ////////////////////////////////////////////////// ANIMAL //////////////////////////////////////////////////

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
   * @returns 
   */
  animalFindAll() {
    return this._animalService.findAll();
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
  animalSave(animal: Animal, novaFoto: any) {
    return this._animalService.save(animal, novaFoto);
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
  animalUpdate(animal: Animal, novaFoto: any, antigaFoto: any) {
    return this._animalService.update(animal, novaFoto, antigaFoto);
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
    this._notificationService.showNotification(message, type);
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
   * @returns 
   */
  tutorFindAll() {
    return this._tutorService.findAll();
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
   * @param nome 
   * @returns 
   */
  tutorFindByNomeContains(nome: string) {
    return this._tutorService.findByNomeContains(nome);
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
   * @returns 
   */
  usuarioFindAll() {
    return this._usuarioService.findAll();
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
   * @param cpf 
   */
  usuarioSearch(cpf: string | null) {
    return this._usuarioService.search(cpf);
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