import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { User } from 'src/app/entities/user';
import { Usuario } from 'src/app/entities/usuario';
import { NotificationType } from 'src/app/enums/notification-type';
import { AuthService } from 'src/app/services/auth.service';
import { NotificationService } from 'src/app/services/notification.service';
import { UsuarioService } from 'src/app/services/usuario.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.sass']
})
export class UsuarioComponent implements OnInit {

  user!: User;
  usuario!: Usuario;

  constructor(
    private _activatedRoute: ActivatedRoute,
    private _authService: AuthService,
    private _notificationService: NotificationService,
    private _usuarioService: UsuarioService
  ) { }

  ngOnInit(): void {
    
    this.user = this._authService.getCurrentUser();

    this._activatedRoute.params.subscribe({

      next: (x: any) => {
          
        if (x && x.id) {
          
          this._usuarioService.findById(x.id).subscribe({
            
            next: (usuario) => {
              this._usuarioService.set(usuario);
            },

            error: (error) => {
              console.error(error);
              this._notificationService.show(MessageUtils.USUARIO_GET_FAIL, NotificationType.FAIL); 
            }
          });
        }
      },
    });

    this._usuarioService.get().subscribe({

      next: (usuario) => {
        
        if (usuario) {
          this.usuario = usuario;
        }
      },
    });
  }
}