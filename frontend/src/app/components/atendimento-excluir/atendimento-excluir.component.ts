import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { NotificationType } from 'src/app/enums/notification-type';
import { AtendimentoService } from 'src/app/services/atendimento.service';
import { NotificationService } from 'src/app/services/notification.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-atendimento-excluir',
  templateUrl: './atendimento-excluir.component.html',
  styleUrls: ['./atendimento-excluir.component.sass'],
})
export class AtendimentoExcluirComponent {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _atendimentoService: AtendimentoService,
    private _dialogRef: MatDialogRef<AtendimentoExcluirComponent>,
    private _notificationService: NotificationService
  ) {}

  submit() {
    this._atendimentoService.delete(this.data.atendimento.id).subscribe({
      complete: () => {
        this._notificationService.show(
          MessageUtils.ATENDIMENTO.DELETE_SUCCESS,
          NotificationType.SUCCESS
        );
        this._dialogRef.close({ status: true });
      },

      error: (error) => {
        console.log(error);
        this._notificationService.show(
          MessageUtils.ATENDIMENTO.DELETE_FAIL + MessageUtils.getMessage(error),
          NotificationType.FAIL
        );
      },
    });
  }
}
