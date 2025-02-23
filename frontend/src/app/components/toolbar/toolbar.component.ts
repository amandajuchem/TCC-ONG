import { Component, OnInit } from '@angular/core';
import { Authentication } from 'src/app/entities/authentication';
import { Usuario } from 'src/app/entities/usuario';
import { NotificationType } from 'src/app/enums/notification-type';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { NotificationService } from 'src/app/services/notification.service';
import { RedirectService } from 'src/app/services/redirect.service';
import { UsuarioService } from 'src/app/services/usuario.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.sass'],
})
export class ToolbarComponent implements OnInit {
  usuario!: Usuario;
  private _authentication!: Authentication;

  constructor(
    private _authenticationService: AuthenticationService,
    private _notificationService: NotificationService,
    private _redirectService: RedirectService,
    private _usuarioService: UsuarioService
  ) {}

  ngOnInit(): void {
    this._authenticationService.getAuthenticationAsObservable().subscribe({
      next: (authentication) => {
        if (authentication) {
          this._authentication = authentication;
          this.loadUser();
        }
      },
    });
  }

  loadUser() {
    this._usuarioService
      .search(this._authentication.username, 0, 1, 'nome', 'asc')
      .subscribe({
        next: (usuarios) => {
          this.usuario = usuarios.content[0];
        },

        error: (error) => {
          console.error(error);
          this._notificationService.show(
            MessageUtils.USUARIO.GET_FAIL + MessageUtils.getMessage(error),
            NotificationType.FAIL
          );
        },
      });
  }

  logout() {
    this._authenticationService.logout();
    this._redirectService.toLogin();
  }

  redirectToPainel() {
    this._redirectService.toPainel();
  }
}
