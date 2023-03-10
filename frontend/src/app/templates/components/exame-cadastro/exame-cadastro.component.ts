import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Exame } from 'src/app/entities/exame';
import { NotificationType } from 'src/app/enums/notification-type';
import { ExameService } from 'src/app/services/exame.service';
import { NotificationService } from 'src/app/services/notification.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-exame-cadastro',
  templateUrl: './exame-cadastro.component.html',
  styleUrls: ['./exame-cadastro.component.sass']
})
export class ExameCadastroComponent implements OnInit {

  form!: FormGroup;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _dialogRef: MatDialogRef<ExameCadastroComponent>,
    private _exameService: ExameService,
    private _formBuilder: FormBuilder,
    private _notificationService: NotificationService
  ) { }

  ngOnInit(): void {
    
    if (this.data.exame) {
      this.buildForm(this.data.exame);
    } else {
      this.buildForm(null);
    }
  }
  
  buildForm(exame: Exame | null) {

    this.form = this._formBuilder.group({
      id: [exame?.id, Validators.nullValidator],
      nome: [exame?.nome, Validators.required],
      categoria: [exame?.categoria, Validators.required]
    });
  }

  submit() {

    let exame: Exame = Object.assign({}, this.form.getRawValue());

    if (exame.id) {

      this._exameService.update(exame).subscribe({
        
        complete: () => {
          this._notificationService.show(MessageUtils.EXAME_UPDATE_SUCCESS, NotificationType.SUCCESS);
          this._dialogRef.close({status: true});
        },
  
        error: (error) => {
          console.error(error);
          this._notificationService.show(MessageUtils.EXAME_UPDATE_FAIL + error.error[0].message, NotificationType.FAIL);
        }
      });
    } 
    
    else {

      this._exameService.save(exame).subscribe({
        
        complete: () => {
          this._notificationService.show(MessageUtils.EXAME_SAVE_SUCCESS, NotificationType.SUCCESS);
          this._dialogRef.close({status: true});
        },
  
        error: (error) => {
          console.error(error);
          this._notificationService.show(MessageUtils.EXAME_SAVE_FAIL + error.error[0].message, NotificationType.FAIL);
        }
      });
    }
  }
}