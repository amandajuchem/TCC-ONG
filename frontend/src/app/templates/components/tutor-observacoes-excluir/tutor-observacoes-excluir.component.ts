import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { NotificationType } from 'src/app/enums/notification-type';
import { NotificationService } from 'src/app/services/notification.service';
import { ObservacaoService } from 'src/app/services/observacao.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-tutor-observacoes-excluir',
  templateUrl: './tutor-observacoes-excluir.component.html',
  styleUrls: ['./tutor-observacoes-excluir.component.sass']
})
export class TutorObservacoesExcluirComponent {

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _dialogRef: MatDialogRef<TutorObservacoesExcluirComponent>,
    private _notificationService: NotificationService,
    private _observacaoService: ObservacaoService
  ) { }

  submit() {

    this._observacaoService.delete(this.data.observacao.id).subscribe({

      complete: () => {
        this._notificationService.show(MessageUtils.OBSERVACAO_DELETE_SUCCESS, NotificationType.SUCCESS);
        this._dialogRef.close({ status: true });
      },

      error: (error) => {
        console.log(error);
        this._notificationService.show(MessageUtils.OBSERVACAO_DELETE_FAIL, NotificationType.FAIL);
      }
    });
  }
}