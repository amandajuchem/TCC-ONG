import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { User } from 'src/app/entities/user';
import { Usuario } from 'src/app/entities/usuario';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
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
    private _facade: FacadeService
  ) { }

  ngOnInit(): void {
    
    this.user = this._facade.authGetCurrentUser();

    this._activatedRoute.params.subscribe({

      next: (x: any) => {
          
        if (x && x.id) {
          
          this._facade.usuarioFindById(x.id).subscribe({
            
            next: (usuario) => {
              this._facade.usuarioSet(usuario);
            },

            error: (error) => {
              console.error(error);
              this._facade.notificationShowNotification(MessageUtils.USUARIO_GET_FAIL, NotificationType.FAIL); 
            }
          });
        }
      },
    });

    this._facade.usuarioGet().subscribe({

      next: (usuario) => {
        
        if (usuario) {
          this.usuario = usuario;
        }
      },
    });
  }
}