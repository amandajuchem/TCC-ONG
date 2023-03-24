import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { NotificationType } from 'src/app/enums/notification-type';
import { FeiraAdocaoService } from 'src/app/services/feira-adocao.service';
import { NotificationService } from 'src/app/services/notification.service';
import { DateUtils } from 'src/app/utils/date-utils';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-feira-adocao-excluir',
  templateUrl: './feira-adocao-excluir.component.html',
  styleUrls: ['./feira-adocao-excluir.component.sass']
})
export class FeiraAdocaoExcluirComponent {

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _feiraAdocaoService: FeiraAdocaoService,
    private _dialogRef: MatDialogRef<FeiraAdocaoExcluirComponent>,
    private _notificationService: NotificationService
  ) { }

  getDateWithTimeZone(date: any) {
    return DateUtils.getDateWithTimeZone(date);
  }

  submit() {

    this._feiraAdocaoService.delete(this.data.feiraAdocao.id).subscribe({

      complete: () => {
        this._notificationService.show(MessageUtils.FEIRA_ADOCAO_DELETE_SUCCESS, NotificationType.SUCCESS);
        this._dialogRef.close({status: true});
      },

      error: (error) => {
        console.log(error);
        this._notificationService.show(MessageUtils.FEIRA_ADOCAO_DELETE_FAIL, NotificationType.FAIL);
      }
    });
  }
}