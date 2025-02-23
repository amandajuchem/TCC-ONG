import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { NotificationType } from 'src/app/enums/notification-type';
import { AgendamentoService } from 'src/app/services/agendamento.service';
import { NotificationService } from 'src/app/services/notification.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-agendamento-excluir',
  templateUrl: './agendamento-excluir.component.html',
  styleUrls: ['./agendamento-excluir.component.sass'],
})
export class AgendamentoExcluirComponent {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _agendamentoService: AgendamentoService,
    private _dialogRef: MatDialogRef<AgendamentoExcluirComponent>,
    private _notificationService: NotificationService
  ) {}

  submit() {
    this._agendamentoService.delete(this.data.agendamento.id).subscribe({
      complete: () => {
        this._notificationService.show(
          MessageUtils.AGENDAMENTO.DELETE_SUCCESS,
          NotificationType.SUCCESS
        );
        this._dialogRef.close({ status: true });
      },

      error: (error) => {
        console.log(error);
        this._notificationService.show(
          MessageUtils.AGENDAMENTO.DELETE_FAIL + MessageUtils.getMessage(error),
          NotificationType.FAIL
        );
      },
    });
  }
}
