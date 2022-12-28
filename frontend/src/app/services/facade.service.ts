import { Injectable } from '@angular/core';

import { Usuario } from '../entities/usuario';
import { NotificationType } from '../enums/notification-type';
import { AuthService } from './auth.service';
import { ImagensService } from './imagens.service';
import { NotificationsService } from './notifications.service';
import { UsuariosService } from './usuarios.service';

@Injectable({
  providedIn: 'root'
})
export class FacadeService {

  constructor(
    private authService: AuthService,
    private imagensService: ImagensService,
    private notificationsService: NotificationsService,
    private usuariosService: UsuariosService
  ) { }

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
  usuariosSave(usuario: Usuario, novaFoto: any, antigaFoto: any) {
    return this.usuariosService.save(usuario, novaFoto, antigaFoto);
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