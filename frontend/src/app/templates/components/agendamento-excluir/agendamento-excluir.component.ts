import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-agendamento-excluir',
  templateUrl: './agendamento-excluir.component.html',
  styleUrls: ['./agendamento-excluir.component.sass']
})
export class AgendamentoExcluirComponent {

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _dialogRef: MatDialogRef<AgendamentoExcluirComponent>,
    private _facade: FacadeService
  ) { }

  submit() {

    this._facade.agendamentoDelete(this.data.agendamento.id).subscribe({

      complete: () => {
        this._facade.notificationShowNotification(MessageUtils.AGENDAMENTO_DELETE_SUCCESS, NotificationType.SUCCESS);
        this._dialogRef.close({status: true});
      },

      error: (error) => {
        console.log(error);
        this._facade.notificationShowNotification(MessageUtils.AGENDAMENTO_DELETE_FAIL, NotificationType.FAIL);
      }
    });
  }
}