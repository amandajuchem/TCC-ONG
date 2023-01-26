import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Usuario } from 'src/app/entities/usuario';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-selecionar-usuario',
  templateUrl: './selecionar-usuario.component.html',
  styleUrls: ['./selecionar-usuario.component.sass']
})
export class SelecionarUsuarioComponent implements OnInit {

  columns!: Array<string>;
  dataSource!: MatTableDataSource<Usuario>;
  usuario!: Usuario;

  constructor(
    @Inject(MAT_DIALOG_DATA) private _data: any,
    private _facade: FacadeService,
    private _matDialogRef: MatDialogRef<SelecionarUsuarioComponent>
  ) { }

  ngOnInit(): void {
    this.columns = ['index', 'nome', 'setor'];
    this.dataSource = new MatTableDataSource();
  }

  search(nome: string) {

    this._facade.usuarioFindByNomeContains(nome).subscribe({

      next: (usuarios) => {

        if (this._data.setor) {
          usuarios = usuarios.filter(u => u.setor === this._data.setor);
        }

        this.dataSource.data = usuarios;    
      },

      error: (error) => {
        console.error(error);
        this._facade.notificationShowNotification(MessageUtils.USUARIOS_GET_FAIL, NotificationType.FAIL); 
      }
    });
  }

  select(usuario: Usuario) {
    this.usuario = usuario;
  }

  submit() {
    this._matDialogRef.close({ status: true, usuario: this.usuario });
  }
}
