import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Authentication } from 'src/app/entities/authentication';
import { Usuario } from 'src/app/entities/usuario';
import { NotificationType } from 'src/app/enums/notification-type';
import { AuthService } from 'src/app/services/auth.service';
import { NotificationService } from 'src/app/services/notification.service';
import { UsuarioService } from 'src/app/services/usuario.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.sass'],
})
export class ToolbarComponent implements OnInit {
  authentication!: Authentication;
  usuario!: Usuario;

  constructor(
    private _authService: AuthService,
    private _router: Router,
    private _notificationService: NotificationService,
    private _usuarioService: UsuarioService
  ) {}

  ngOnInit(): void {
    this.authentication = this._authService.getAuthentication();

    this._usuarioService
      .search(this.authentication.username, 0, 1, 'nome', 'asc')
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
    this._authService.logout();
    this._router.navigate(['']);
  }
}
