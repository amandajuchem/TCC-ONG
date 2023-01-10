import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-animais-excluir',
  templateUrl: './animais-excluir.component.html',
  styleUrls: ['./animais-excluir.component.sass']
})
export class AnimaisExcluirComponent {

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private dialogRef: MatDialogRef<AnimaisExcluirComponent>,
    private facade: FacadeService
  ) { }

  submit() {

    this.facade.animaisDelete(this.data.animal.id).subscribe({
      
      complete: () => {
        this.facade.notificationsShowNotification(MessageUtils.ANIMAIS_DELETE_SUCCESS, NotificationType.SUCCESS);
        this.dialogRef.close({ status: true });
      },

      error: (error) => {
        console.log(error);
        this.facade.notificationsShowNotification(MessageUtils.ANIMAIS_DELETE_FAIL, NotificationType.FAIL);
      }
    });
  }
}