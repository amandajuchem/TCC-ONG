import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { DateUtils } from 'src/app/utils/date-utils';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-atendimento-excluir',
  templateUrl: './atendimento-excluir.component.html',
  styleUrls: ['./atendimento-excluir.component.sass']
})
export class AtendimentoExcluirComponent {

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _dialogRef: MatDialogRef<AtendimentoExcluirComponent>,
    private _facade: FacadeService
  ) { }

  getDateWithTimeZone(date: Date) {
    return DateUtils.getDateWithTimeZone(date);
  }

  submit() {

    this._facade.atendimentoDelete(this.data.atendimento.id).subscribe({

      complete: () => {
        this._facade.notificationShowNotification(MessageUtils.ATENDIMENTO_DELETE_SUCCESS, NotificationType.SUCCESS);
        this._dialogRef.close({ status: true });
      },

      error: (error) => {
        console.log(error);
        this._facade.notificationShowNotification(MessageUtils.ATENDIMENTO_DELETE_FAIL, NotificationType.FAIL);
      }
    });
  }
}