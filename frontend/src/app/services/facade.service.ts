import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { NotificationService } from './notification.service';
import { NotificationType } from '../enums/notification-type';

@Injectable({
  providedIn: 'root'
})
export class FacadeService {

  constructor(
    private authService: AuthService,
    private notificationService: NotificationService
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

  ////////////////////////////////////////////////// NOTIFICATION //////////////////////////////////////////////////

  /**
   * 
   * @param message 
   * @param type 
   */
  notificationShowNotification(message: string, type: NotificationType) {
    this.notificationService.showNotification(message, type);
  }
}