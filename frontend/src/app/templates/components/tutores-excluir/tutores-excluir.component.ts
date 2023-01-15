import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-tutores-excluir',
  templateUrl: './tutores-excluir.component.html',
  styleUrls: ['./tutores-excluir.component.sass']
})
export class TutoresExcluirComponent {

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private dialogRef: MatDialogRef<TutoresExcluirComponent>,
    private facade: FacadeService
  ) { }

  submit() {

    this.facade.tutorDelete(this.data.tutor.id).subscribe({
      
      complete: () => {
        this.facade.notificationShowNotification(MessageUtils.TUTORES_DELETE_SUCCESS, NotificationType.SUCCESS);
        this.dialogRef.close({ status: true });
      },

      error: (error) => {
        console.log(error);
        this.facade.notificationShowNotification(MessageUtils.TUTORES_DELETE_FAIL, NotificationType.FAIL);
      }
    });
  }
}