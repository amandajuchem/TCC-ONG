import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Usuario } from 'src/app/entities/usuario';
import { NotificationType } from 'src/app/enums/notification-type';
import { AuthService } from 'src/app/services/auth.service';
import { ImagemService } from 'src/app/services/imagem.service';
import { NotificationService } from 'src/app/services/notification.service';
import { UsuarioService } from 'src/app/services/usuario.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { environment } from 'src/environments/environment';

import { SelecionarImagemComponent } from '../selecionar-imagem/selecionar-imagem.component';

@Component({
  selector: 'app-usuario-informacoes',
  templateUrl: './usuario-informacoes.component.html',
  styleUrls: ['./usuario-informacoes.component.sass']
})
export class UsuarioInformacoesComponent implements OnInit {

  apiURL!: string;
  form!: FormGroup;
  foto!: any;
  hide!: boolean;
  user!: any;
  usuario!: Usuario;

  constructor(
    private _authService: AuthService,
    private _dialog: MatDialog,
    private _formBuilder: FormBuilder,
    private _imagemService: ImagemService,
    private _notificationService: NotificationService,
    private _usuarioService: UsuarioService
  ) { }

  ngOnInit(): void {
    
    this.apiURL = environment.apiURL;
    this.hide = true;
    this.user = this._authService.getCurrentUser();

    this._usuarioService.get().subscribe({

      next: (usuario) => {
          
        if (usuario) {
          this.usuario = usuario;
          this.buildForm(usuario);

          if (usuario.foto) {
            this.foto = { id: usuario.foto.id, nome: usuario.foto.nome };
          } else {
            this.foto = null;
          }
        }
      }
    });
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
          this.foto = { id: new Date().getTime(), data: data, file: result.images[0] };;
        });
      }
    });
  }

  buildForm(usuario: Usuario) {

    this.form = this._formBuilder.group({
      id: [usuario.id, Validators.nullValidator],
      nome: [usuario.nome, Validators.required],
      cpf: [usuario.cpf, Validators.required],
      senha: [usuario.senha, Validators.required],
      setor: [usuario.setor, Validators.required],
      status: [usuario.status, Validators.required],
      foto: [usuario.foto, Validators.nullValidator]
    });

    this.form.disable();
  }

  cancel() {
    this.buildForm(this.usuario);
    this.foto = this.usuario.foto ? { id: this.usuario.foto.id, nome: this.usuario.foto.nome } : null;
  }

  removeFoto() {
    this.foto = null;
    this.form.get('foto')?.patchValue(null);
  }

  submit() {

    const usuario: Usuario = Object.assign({}, this.form.getRawValue());
    const imagem: File = this.foto?.file;

    this._usuarioService.update(usuario, imagem).subscribe({

      next: (usuario) => {
        this.foto ? this.foto.file = null : null;
        this._usuarioService.set(usuario);
        this._notificationService.show(MessageUtils.USUARIO_UPDATE_SUCCESS, NotificationType.SUCCESS);
      },

      error: (error) => {
        console.error(error);
        this._notificationService.show(MessageUtils.USUARIO_UPDATE_FAIL + error.error[0].message, NotificationType.FAIL);
      }
    });
  }

  update() {

    this.form.enable();

    if (this.usuario.cpf === this.user.username) {
      this.form.get('cpf')?.disable();
      this.form.get('setor')?.disable();
      this.form.get('status')?.disable();
    }
  }
}