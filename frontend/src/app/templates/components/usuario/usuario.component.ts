import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Authentication } from 'src/app/entities/authentication';
import { Usuario } from 'src/app/entities/usuario';
import { NotificationType } from 'src/app/enums/notification-type';
import { AuthService } from 'src/app/services/auth.service';
import { NotificationService } from 'src/app/services/notification.service';
import { UsuarioService } from 'src/app/services/usuario.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.sass'],
})
export class UsuarioComponent implements OnInit {
  authentication!: Authentication;
  isLoading!: boolean;
  usuario!: Usuario | null;

  constructor(
    private _activatedRoute: ActivatedRoute,
    private _authService: AuthService,
    private _notificationService: NotificationService,
    private _usuarioService: UsuarioService
  ) {}

  ngOnInit(): void {
    this.authentication = this._authService.getAuthentication();
    this.isLoading = true;

    this._activatedRoute.params.subscribe({
      next: (params: any) => {
        if (params && params.id) {
          if (params.id.includes('cadastro')) {
            this._usuarioService.set(null);
            this.isLoading = false;
          } else {
            this._usuarioService.findById(params.id).subscribe({
              next: (usuario) => {
                this._usuarioService.set(usuario);
                this.isLoading = false;
              },

              error: (error) => {
                console.error(error);
                this.isLoading = false;
                this._notificationService.show(
                  MessageUtils.USUARIO.GET_FAIL +
                    MessageUtils.getMessage(error),
                  NotificationType.FAIL
                );
              },
            });
          }
        }
      },
    });

    this._usuarioService.get().subscribe({
      next: (usuario) => {
        this.usuario = usuario;
      },
    });
  }
}
