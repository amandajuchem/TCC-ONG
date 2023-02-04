import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Exame } from 'src/app/entities/exame';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
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
    private _facade: FacadeService,
    private _formBuilder: FormBuilder,
    private _dialogRef: MatDialogRef<ExameCadastroComponent>
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

      this._facade.exameUpdate(exame).subscribe({
        
        complete: () => {
          this._facade.notificationShowNotification(MessageUtils.EXAME_UPDATE_SUCCESS, NotificationType.SUCCESS);
          this._dialogRef.close({status: true});
        },
  
        error: (error) => {
          console.error(error);
          this._facade.notificationShowNotification(MessageUtils.EXAME_UPDATE_FAIL + error.error[0].message, NotificationType.FAIL);
        }
      });
    } 
    
    else {

      this._facade.exameSave(exame).subscribe({
        
        complete: () => {
          this._facade.notificationShowNotification(MessageUtils.EXAME_SAVE_SUCCESS, NotificationType.SUCCESS);
          this._dialogRef.close({status: true});
        },
  
        error: (error) => {
          console.error(error);
          this._facade.notificationShowNotification(MessageUtils.EXAME_SAVE_FAIL + error.error[0].message, NotificationType.FAIL);
        }
      });
    }
  }
}