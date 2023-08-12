import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { NotificationType } from 'src/app/enums/notification-type';
import { AdocaoService } from 'src/app/services/adocao.service';
import { NotificationService } from 'src/app/services/notification.service';
import { DateUtils } from 'src/app/utils/date-utils';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-animal-adocoes-excluir',
  templateUrl: './animal-adocoes-excluir.component.html',
  styleUrls: ['./animal-adocoes-excluir.component.sass'],
})
export class AnimalAdocoesExcluirComponent {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _adocaoService: AdocaoService,
    private _dialogRef: MatDialogRef<AnimalAdocoesExcluirComponent>,
    private _notificationService: NotificationService
  ) {}

  getDateWithTimeZone(date: any) {
    return DateUtils.getDateWithTimeZone(date);
  }

  submit() {
    this._adocaoService.delete(this.data.adocao.id).subscribe({
      complete: () => {
        this._notificationService.show(
          MessageUtils.ADOCAO.DELETE_SUCCESS,
          NotificationType.SUCCESS
        );
        this._dialogRef.close({ status: true });
      },

      error: (error) => {
        console.log(error);
        this._notificationService.show(
          MessageUtils.ADOCAO.DELETE_FAIL + MessageUtils.getMessage(error),
          NotificationType.FAIL
        );
      },
    });
  }
}
