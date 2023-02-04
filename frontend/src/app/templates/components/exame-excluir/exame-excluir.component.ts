import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-exame-excluir',
  templateUrl: './exame-excluir.component.html',
  styleUrls: ['./exame-excluir.component.sass']
})
export class ExameExcluirComponent {

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _dialogRef: MatDialogRef<ExameExcluirComponent>,
    private _facade: FacadeService
  ) { }

  submit() {

    this._facade.exameDelete(this.data.exame.id).subscribe({

      complete: () => {
        this._facade.notificationShowNotification(MessageUtils.EXAME_DELETE_SUCCESS, NotificationType.SUCCESS);
        this._dialogRef.close({status: true});
      },

      error: (error) => {
        console.log(error);
        this._facade.notificationShowNotification(MessageUtils.EXAME_DELETE_FAIL, NotificationType.FAIL);
      }
    });
  }
}