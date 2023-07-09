import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Authentication } from 'src/app/entities/authentication';
import { Usuario } from 'src/app/entities/usuario';
import { NotificationType } from 'src/app/enums/notification-type';
import { AuthService } from 'src/app/services/auth.service';
import { ImagemService } from 'src/app/services/imagem.service';
import { NotificationService } from 'src/app/services/notification.service';
import { UsuarioService } from 'src/app/services/usuario.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { environment } from 'src/environments/environment';

import { SelecionarImagemComponent } from '../selecionar-imagem/selecionar-imagem.component';
import { FormUtils } from 'src/app/utils/form-utils';

@Component({
  selector: 'app-usuario-informacoes',
  templateUrl: './usuario-informacoes.component.html',
  styleUrls: ['./usuario-informacoes.component.sass']
})
export class UsuarioInformacoesComponent implements OnInit {

  apiURL!: string;
  authentication!: Authentication;
  form!: FormGroup;
  foto!: any;
  hide!: boolean;
  usuario!: Usuario;

  constructor(
    private _authService: AuthService,
    private _dialog: MatDialog,
    private _formBuilder: FormBuilder,
    private _imagemService: ImagemService,
    private _notificationService: NotificationService,
    private _router: Router,
    private _usuarioService: UsuarioService
  ) { }

  ngOnInit(): void {
    
    this.apiURL = environment.apiURL;
    this.authentication = this._authService.getAuthentication();
    this.hide = true;

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

        else {
          this.buildForm(null);
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

  buildForm(usuario: Usuario | null) {

    this.form = this._formBuilder.group({
      id: [usuario?.id, Validators.nullValidator],
      nome: [usuario?.nome, [Validators.required, Validators.maxLength(100)]],
      cpf: [usuario?.cpf, [Validators.required, Validators.maxLength(11)]],
      senha: [usuario?.senha, [Validators.required, Validators.maxLength(255)]],
      setor: [usuario?.setor, [Validators.required, Validators.maxLength(20)]],
      status: [usuario?.status, Validators.required],
      foto: [usuario?.foto, Validators.nullValidator]
    });

    usuario ? this.form.disable() : this.form.enable();
  }

  cancel() {
    this.foto = this.usuario?.foto ? { id: this.usuario?.foto.id, nome: this.usuario?.foto.nome } : null;
    this.usuario ? this.buildForm(this.usuario) : this._router.navigate(['/' + this.authentication.role.toLowerCase() + '/usuarios']);
  }

  getErrorMessage(controlName: string) {
    return FormUtils.getErrorMessage(this.form, controlName);
  }

  hasError(controlName: string) {
    return FormUtils.hasError(this.form, controlName);
  }

  removeFoto() {
    this.foto = null;
    this.form.get('foto')?.patchValue(null);
  }

  submit() {

    if (this.form.valid) {

      const usuario: Usuario = Object.assign({}, this.form.getRawValue());
      const imagem: File = this.foto?.file;

      if (usuario.id) {

        this._usuarioService.update(usuario, imagem).subscribe({

          next: (usuario) => {
            this.foto ? this.foto.file = null : null;
            this._usuarioService.set(usuario);
            this._notificationService.show(MessageUtils.USUARIO.UPDATE_SUCCESS, NotificationType.SUCCESS);
          },
    
          error: (error) => {
            console.error(error);
            this._notificationService.show(MessageUtils.USUARIO.UPDATE_FAIL + MessageUtils.getMessage(error), NotificationType.FAIL);
          }
        });
      }

      else {

        this._usuarioService.save(usuario, imagem).subscribe({

          next: (usuario) => {
            this.foto ? this.foto.file = null : null;
            this._router.navigate(['/' + this.authentication.role.toLowerCase() + '/usuarios/' + usuario.id]);
            this._notificationService.show(MessageUtils.USUARIO.SAVE_SUCCESS, NotificationType.SUCCESS);
          },
    
          error: (error) => {
            console.error(error);
            this._notificationService.show(MessageUtils.USUARIO.SAVE_FAIL + MessageUtils.getMessage(error), NotificationType.FAIL);
          }
        });
      }
    }
  }

  update() {

    this.form.enable();

    if (this.usuario.cpf === this.authentication.username) {
      this.form.get('cpf')?.disable();
      this.form.get('setor')?.disable();
      this.form.get('status')?.disable();
    }
  }
}