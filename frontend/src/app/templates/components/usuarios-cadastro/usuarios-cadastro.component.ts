import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Usuario } from 'src/app/entities/usuario';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { environment } from 'src/environments/environment';

import { SelecionarImagemComponent } from '../selecionar-imagem/selecionar-imagem.component';

@Component({
  selector: 'app-usuarios-cadastro',
  templateUrl: './usuarios-cadastro.component.html',
  styleUrls: ['./usuarios-cadastro.component.sass']
})
export class UsuariosCadastroComponent implements OnInit {
  
  apiURL!: string;
  user!: any;
  form!: FormGroup;
  foto!: any;
  fotoToSave!: any;
  hide!: boolean;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _dialog: MatDialog,
    private _dialogRef: MatDialogRef<UsuariosCadastroComponent>,
    private _facade: FacadeService,
    private _formBuilder: FormBuilder
  ) { }

  ngOnInit(): void {
    
    this.apiURL = environment.apiURL;
    this.user = this._facade.authGetCurrentUser();
    this.hide = true;
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
      senha: [null, Validators.required],
      setor: [null, Validators.required],
      status: [null, Validators.required],
      foto: [null, Validators.nullValidator]
    });
  }

  removeFoto() {
    
    this.foto = null;
    this.form.get('foto')?.patchValue(null);
  }

  submit() {

    let usuario: Usuario = Object.assign({}, this.form.getRawValue());

    this._facade.usuarioSave(usuario, this.fotoToSave).subscribe({

      complete: () => {
        this._facade.notificationShowNotification(MessageUtils.USUARIO_SAVE_SUCCESS, NotificationType.SUCCESS);
        this._dialogRef.close({status: true});
      },

      error: (error) => {
        console.error(error);
        this._facade.notificationShowNotification(MessageUtils.USUARIO_SAVE_FAIL + error.error[0].message, NotificationType.FAIL);
      }
    });
  }
}