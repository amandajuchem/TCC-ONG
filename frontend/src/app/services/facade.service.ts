import { Injectable } from '@angular/core';

import { Animal } from '../entities/animal';
import { Usuario } from '../entities/usuario';
import { NotificationType } from '../enums/notification-type';
import { AnimaisService } from './animais.service';
import { AuthService } from './auth.service';
import { ImagensService } from './imagens.service';
import { NotificationsService } from './notifications.service';
import { UsuariosService } from './usuarios.service';

@Injectable({
  providedIn: 'root'
})
export class FacadeService {

  constructor(
    private animaisService: AnimaisService,
    private authService: AuthService,
    private imagensService: ImagensService,
    private notificationsService: NotificationsService,
    private usuariosService: UsuariosService
  ) { }

  ////////////////////////////////////////////////// ANIMAIS //////////////////////////////////////////////////

  /**
   * 
   * @param id 
   * @returns 
   */
  animaisDelete(id: string) {
    return this.animaisService.delete(id);
  }

  /**
   * 
   * @returns 
   */
  animaisFindAll() {
    return this.animaisService.findAll();
  }

  /**
   * 
   * @param id 
   * @returns 
   */
  animaisFindById(id: string) {
    return this.animaisService.findById(id);
  }

  /**
   * 
   * @param animal 
   * @param novaFoto 
   * @returns 
   */
  animaisSave(animal: Animal, novaFoto: any) {
    return this.animaisService.save(animal, novaFoto);
  }

  /**
   * 
   * @param animal 
   * @param novaFoto 
   * @param antigaFoto 
   * @returns 
   */
  animaisUpdate(animal: Animal, novaFoto: any, antigaFoto: any) {
    return this.animaisService.update(animal, novaFoto, antigaFoto);
  }

  ////////////////////////////////////////////////// AUTHENTICATION //////////////////////////////////////////////////

  /**
    * 
    * @returns 
    */
  authIsAuthenticated() {
    return this.authService.isAuthenticated();
  }

  /**
   * 
   * @param user 
   * @returns 
   */
  authLogin(user: any) {
    return this.authService.login(user);
  }

  /**
   * 
   * @returns 
   */
  authLogout() {
    return this.authService.logout();
  }

  /**
   * 
   * @returns 
   */
  authGetCurrentUser() {
    return this.authService.getCurrentUser();
  }

  /**
   * 
   * @param currentUser 
   * @returns 
   */
  authSetCurrentUser(currentUser: any) {
    return this.authService.setCurrentUser(currentUser);
  }

  ////////////////////////////////////////////////// IMAGENS //////////////////////////////////////////////////

  /**
   * 
   * @param imagem 
   * @returns 
   */
  imagensToBase64(imagem: any) {
    return this.imagensService.toBase64(imagem);
  }

  ////////////////////////////////////////////////// NOTIFICATIONS //////////////////////////////////////////////////

  /**
   * 
   * @param message 
   * @param type 
   */
  notificationsShowNotification(message: string, type: NotificationType) {
    this.notificationsService.showNotification(message, type);
  }

  ////////////////////////////////////////////////// USUÁRIOS //////////////////////////////////////////////////

  /**
   * 
   * @returns 
   */
  usuariosFindAll() {
    return this.usuariosService.findAll();
  }

  /**
   * 
   * @param usuario 
   * @param novaFoto 
   * @param antigaFoto 
   * @returns 
   */
  usuariosSave(usuario: Usuario, novaFoto: any) {
    return this.usuariosService.save(usuario, novaFoto);
  }

  /**
   * 
   * @param usuario 
   * @param novaFoto 
   * @param antigaFoto 
   * @returns 
   */
  usuariosUpdate(usuario: Usuario, novaFoto: any, antigaFoto: any) {
    return this.usuariosService.update(usuario, novaFoto, antigaFoto);
  }
}