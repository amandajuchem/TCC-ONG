import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { NotificationType } from 'src/app/enums/notification-type';
import { ExameService } from 'src/app/services/exame.service';
import { NotificationService } from 'src/app/services/notification.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-exame-excluir',
  templateUrl: './exame-excluir.component.html',
  styleUrls: ['./exame-excluir.component.sass'],
})
export class ExameExcluirComponent {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _dialogRef: MatDialogRef<ExameExcluirComponent>,
    private _exameService: ExameService,
    private _notificationService: NotificationService
  ) {}

  submit() {
    this._exameService.delete(this.data.exame.id).subscribe({
      complete: () => {
        this._notificationService.show(
          MessageUtils.EXAME.DELETE_SUCCESS,
          NotificationType.SUCCESS
        );
        this._dialogRef.close({ status: true });
      },

      error: (error) => {
        console.log(error);
        this._notificationService.show(
          MessageUtils.EXAME.DELETE_FAIL + MessageUtils.getMessage(error),
          NotificationType.FAIL
        );
      },
    });
  }
}
