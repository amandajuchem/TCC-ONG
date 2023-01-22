import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/entities/user';
import { Usuario } from 'src/app/entities/usuario';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.sass']
})
export class ToolbarComponent implements OnInit {

  user!: User;
  usuario!: Usuario;

  constructor(
    private _facade: FacadeService,
    private _router: Router
  ) { }

  ngOnInit(): void {
    
    this.user = this._facade.authGetCurrentUser();
    
    this._facade.usuarioSearch(this.user.username).subscribe({

      next: (usuario) => {
        this.usuario = usuario;    
      },

      error: (error) => {
        console.error(error);
        this._facade.notificationShowNotification(MessageUtils.USUARIO_GET_FAIL, NotificationType.FAIL); 
      }
    });
  }

  logout() {
    this._facade.authLogout();
    this._router.navigate(['']);
  }
}