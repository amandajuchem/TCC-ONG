import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-animal-excluir',
  templateUrl: './animal-excluir.component.html',
  styleUrls: ['./animal-excluir.component.sass']
})
export class AnimalExcluirComponent {

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _dialogRef: MatDialogRef<AnimalExcluirComponent>,
    private _facade: FacadeService
  ) { }

  submit() {

    this._facade.animalDelete(this.data.animal.id).subscribe({

      complete: () => {
        this._facade.notificationShowNotification(MessageUtils.ANIMAL_DELETE_SUCCESS, NotificationType.SUCCESS);
        this._dialogRef.close({status: true});
      },

      error: (error) => {
        console.log(error);
        this._facade.notificationShowNotification(MessageUtils.ANIMAL_DELETE_FAIL, NotificationType.FAIL);
      }
    });
  }
}