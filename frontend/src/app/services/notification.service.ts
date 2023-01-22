import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NotificationType } from '../enums/notification-type';
import { NotificationComponent } from '../templates/components/notification/notification.component';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(
    private _snackBar: MatSnackBar
  ) { }

  showNotification(message: string, type: NotificationType) {
    
    this._snackBar.openFromComponent(NotificationComponent, {
      data: {
        message: message
      },
      duration: 5000,
      horizontalPosition: 'center',
      verticalPosition: 'bottom',
      panelClass: type
    });
  }
}