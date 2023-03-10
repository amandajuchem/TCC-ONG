import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { NotificationType } from 'src/app/enums/notification-type';
import { NotificationService } from 'src/app/services/notification.service';
import { TutorService } from 'src/app/services/tutor.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-tutor-excluir',
  templateUrl: './tutor-excluir.component.html',
  styleUrls: ['./tutor-excluir.component.sass']
})
export class TutorExcluirComponent {

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _dialogRef: MatDialogRef<TutorExcluirComponent>,
    private _notificationService: NotificationService,
    private _tutorService: TutorService
  ) { }

  submit() {

    this._tutorService.delete(this.data.tutor.id).subscribe({
      
      complete: () => {
        this._notificationService.show(MessageUtils.TUTOR_DELETE_SUCCESS, NotificationType.SUCCESS);
        this._dialogRef.close({ status: true });
      },

      error: (error) => {
        console.log(error);
        this._notificationService.show(MessageUtils.TUTOR_DELETE_FAIL, NotificationType.FAIL);
      }
    });
  }
}