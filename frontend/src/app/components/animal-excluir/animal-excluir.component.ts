import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { NotificationType } from 'src/app/enums/notification-type';
import { AnimalService } from 'src/app/services/animal.service';
import { NotificationService } from 'src/app/services/notification.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-animal-excluir',
  templateUrl: './animal-excluir.component.html',
  styleUrls: ['./animal-excluir.component.sass'],
})
export class AnimalExcluirComponent {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _animalService: AnimalService,
    private _dialogRef: MatDialogRef<AnimalExcluirComponent>,
    private _notificationService: NotificationService
  ) {}

  submit() {
    this._animalService.delete(this.data.animal.id).subscribe({
      complete: () => {
        this._notificationService.show(
          MessageUtils.ANIMAL.DELETE_SUCCESS,
          NotificationType.SUCCESS
        );
        this._dialogRef.close({ status: true });
      },

      error: (error) => {
        console.log(error);
        this._notificationService.show(
          MessageUtils.ANIMAL.DELETE_FAIL + MessageUtils.getMessage(error),
          NotificationType.FAIL
        );
      },
    });
  }
}
