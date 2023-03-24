import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Usuario } from 'src/app/entities/usuario';
import { NotificationType } from 'src/app/enums/notification-type';
import { AuthService } from 'src/app/services/auth.service';
import { ImagemService } from 'src/app/services/imagem.service';
import { NotificationService } from 'src/app/services/notification.service';
import { UsuarioService } from 'src/app/services/usuario.service';
import { MessageUtils } from 'src/app/utils/message-utils';

import { SelecionarImagemComponent } from '../selecionar-imagem/selecionar-imagem.component';

@Component({
  selector: 'app-usuario-cadastro',
  templateUrl: './usuario-cadastro.component.html',
  styleUrls: ['./usuario-cadastro.component.sass']
})
export class UsuarioCadastroComponent implements OnInit {
  
  form!: FormGroup;
  foto!: any;
  hide!: boolean;
  user!: any;

  constructor(
    private _authService: AuthService,
    private _dialog: MatDialog,
    private _dialogRef: MatDialogRef<UsuarioCadastroComponent>,
    private _formBuilder: FormBuilder,
    private _imagemService: ImagemService,
    private _notificationService: NotificationService,
    private _usuarioService: UsuarioService
  ) { }

  ngOnInit(): void {
    this.user = this._authService.getCurrentUser();
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

        this._imagemService.toBase64(result.images[0])?.then(data => {
          this.foto = { id: new Date().getTime(), data: data, file: result.images[0] };
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
  }

  submit() {

    const usuario: Usuario = Object.assign({}, this.form.getRawValue());
    const imagem: File = this.foto?.file;

    this._usuarioService.save(usuario, imagem).subscribe({

      complete: () => {
        this._notificationService.show(MessageUtils.USUARIO_SAVE_SUCCESS, NotificationType.SUCCESS);
        this._dialogRef.close({status: true});
      },

      error: (error) => {
        console.error(error);
        this._notificationService.show(MessageUtils.USUARIO_SAVE_FAIL + error.error[0].message, NotificationType.FAIL);
      }
    });
  }
}