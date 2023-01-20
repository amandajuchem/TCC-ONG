import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Tutor } from 'src/app/entities/tutor';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { environment } from 'src/environments/environment';

import { SelecionarImagemComponent } from '../selecionar-imagem/selecionar-imagem.component';

@Component({
  selector: 'app-tutor-cadastro',
  templateUrl: './tutor-cadastro.component.html',
  styleUrls: ['./tutor-cadastro.component.sass']
})
export class TutorCadastroComponent implements OnInit {

  apiURL!: string;
  form!: FormGroup;
  foto!: any;
  fotoToSave!: any;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _dialog: MatDialog,
    private _dialogRef: MatDialogRef<TutorCadastroComponent>,
    private _facade: FacadeService,
    private _formBuilder: FormBuilder
  ) { }

  ngOnInit(): void {
    
    this.apiURL = environment.apiURL;
    this.buildForm();
  }
  
  addFoto() {

    this._dialog.open(SelecionarImagemComponent, {
      data: {
        multiple: false
      },
      width: '100%'
    })
    .afterClosed().subscribe(result => {

      if (result && result.status) {

        this._facade.imagemToBase64(result.images[0])?.then(data => {

          let imagem = { id: new Date().getTime(), data: data, nome: null, salvo: false };

          this.foto = imagem;
          this.fotoToSave = result.images[0];
        });
      }
    });
  }

  buildForm() {

    this.form = this._formBuilder.group({
      id: [null, Validators.nullValidator],
      nome: [null, Validators.required],
      cpf: [null, Validators.required],
      rg: [null, Validators.nullValidator],
      telefone: [null, Validators.required],
      situacao: [null, Validators.required],

      endereco: this._formBuilder.group({
        id: [null, Validators.nullValidator],
        rua: [null, Validators.required],
        numeroResidencia: [null, Validators.required],
        bairro: [null, Validators.required],
        complemento: [null, Validators.nullValidator],
        cidade: [null, Validators.required],
        estado: [null, Validators.required],
        cep: [null, Validators.required]
      })
    });
  }

  removeFoto() {

    this.foto = null;
    this.form.get('foto')?.patchValue(null);
  }

  submit() {

    let tutor: Tutor = Object.assign({}, this.form.getRawValue());

    this._facade.tutorSave(tutor, this.fotoToSave).subscribe({

      complete: () => {
        this._facade.notificationShowNotification(MessageUtils.TUTOR_SAVE_SUCCESS, NotificationType.SUCCESS);
        this._dialogRef.close({status: true});
      },

      error: (error) => {
        console.error(error);
        this._facade.notificationShowNotification(MessageUtils.TUTOR_SAVE_FAIL + error.error[0].message, NotificationType.FAIL);
      }
    });
  } 
}