import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { FacadeService } from 'src/app/services/facade.service';
import { UsuariosCadastroComponent } from '../usuarios-cadastro/usuarios-cadastro.component';
import { MessageUtils } from 'src/app/utils/message-utils';
import { NotificationType } from 'src/app/enums/notification-type';
import { Usuario } from 'src/app/entities/usuario';
import { environment } from 'src/environments/environment';
import { User } from 'src/app/entities/user';

@Component({
  selector: 'app-usuarios',
  templateUrl: './usuarios.component.html',
  styleUrls: ['./usuarios.component.sass']
})
export class UsuariosComponent implements OnInit {
  
  apiURL!: string;
  user!: User;
  usuarios!: Array<Usuario>;
  usuariosToShow!: Array<Usuario>;

  constructor(
    private _dialog: MatDialog,
    private _facade: FacadeService
  ) { }

  ngOnInit(): void {
    this.apiURL = environment.apiURL;
    this.user = this._facade.authGetCurrentUser();
    this.findAllUsuarios();
  }

  add() {

    this._dialog.open(UsuariosCadastroComponent, {
      data: {
        usuario : null
      },
      width: '100%'
    })
    .afterClosed().subscribe({
      
      next: (result) => {
        
        if (result && result.status) {
          this.findAllUsuarios();
        }
      }
    });
  }

  filter(value: string) {
    value = value.toUpperCase();
    this.usuariosToShow = this.usuarios.filter(u => u.nome.toUpperCase().includes(value) || u.cpf.toUpperCase().includes(value));
  }

  findAllUsuarios() {

    this._facade.usuarioFindAll().subscribe({
      
      next: (usuarios) => {
        this.usuarios = usuarios;
        this.usuariosToShow = this.usuarios;
      },

      error: (error) => {
        console.error(error);
        this._facade.notificationShowNotification(MessageUtils.USUARIOS_GET_FAIL, NotificationType.FAIL); 
      }
    });
  }

  update(usuario: Usuario) {

    this._dialog.open(UsuariosCadastroComponent, {
      data: {
        usuario : usuario
      },
      width: '100%'
    })
    .afterClosed().subscribe({
      
      next: (result) => {
        
        if (result && result.status) {
          this.findAllUsuarios();
        }
      }
    });
  }
}