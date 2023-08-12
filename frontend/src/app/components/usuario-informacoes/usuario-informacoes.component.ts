import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Authentication } from 'src/app/entities/authentication';
import { Usuario } from 'src/app/entities/usuario';
import { NotificationType } from 'src/app/enums/notification-type';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { NotificationService } from 'src/app/services/notification.service';
import { RedirectService } from 'src/app/services/redirect.service';
import { UsuarioService } from 'src/app/services/usuario.service';
import { FormUtils } from 'src/app/utils/form-utils';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-usuario-informacoes',
  templateUrl: './usuario-informacoes.component.html',
  styleUrls: ['./usuario-informacoes.component.sass'],
})
export class UsuarioInformacoesComponent implements OnInit {
  form!: FormGroup;
  hide!: boolean;
  usuario!: Usuario;

  private _authentication!: Authentication;

  constructor(
    private _authenticationService: AuthenticationService,
    private _formBuilder: FormBuilder,
    private _notificationService: NotificationService,
    private _redirectService: RedirectService,
    private _usuarioService: UsuarioService
  ) {}

  ngOnInit(): void {
    this._authentication = this._authenticationService.getAuthentication();
    this.hide = true;

    this._usuarioService.get().subscribe({
      next: (usuario) => {
        if (usuario) {
          this.usuario = usuario;
          this.buildForm(usuario);
        } else {
          this.buildForm(null);
        }
      },
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
    });

    if (usuario?.cpf === this._authentication.username) {
      this.form.get('cpf')?.disable();
      this.form.get('setor')?.disable();
      this.form.get('status')?.disable();
    }
  }

  getErrorMessage(controlName: string) {
    return FormUtils.getErrorMessage(this.form, controlName);
  }

  hasError(controlName: string) {
    return FormUtils.hasError(this.form, controlName);
  }

  redirectToUsuarioList() {
    this._redirectService.toUsuarioList();
  }

  submit() {
    if (this.form.valid) {
      const usuario: Usuario = Object.assign({}, this.form.getRawValue());

      if (usuario.id) {
        this._usuarioService.update(usuario).subscribe({
          next: (usuario) => {
            this._usuarioService.set(usuario);
            this._notificationService.show(
              MessageUtils.USUARIO.UPDATE_SUCCESS,
              NotificationType.SUCCESS
            );
          },

          error: (error) => {
            console.error(error);
            this._notificationService.show(
              MessageUtils.USUARIO.UPDATE_FAIL + MessageUtils.getMessage(error),
              NotificationType.FAIL
            );
          },
        });
      } else {
        this._usuarioService.save(usuario).subscribe({
          next: (usuario) => {
            this._notificationService.show(
              MessageUtils.USUARIO.SAVE_SUCCESS,
              NotificationType.SUCCESS
            );
            this._redirectService.toUsuario(usuario.id);
          },

          error: (error) => {
            console.error(error);
            this._notificationService.show(
              MessageUtils.USUARIO.SAVE_FAIL + MessageUtils.getMessage(error),
              NotificationType.FAIL
            );
          },
        });
      }
    }
  }
}
