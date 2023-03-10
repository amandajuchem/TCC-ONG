import { AfterViewInit, Component, Inject, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Usuario } from 'src/app/entities/usuario';
import { NotificationType } from 'src/app/enums/notification-type';
import { NotificationService } from 'src/app/services/notification.service';
import { UsuarioService } from 'src/app/services/usuario.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { OperatorUtils } from 'src/app/utils/operator-utils';

@Component({
  selector: 'app-selecionar-usuario',
  templateUrl: './selecionar-usuario.component.html',
  styleUrls: ['./selecionar-usuario.component.sass']
})
export class SelecionarUsuarioComponent implements AfterViewInit {

  columns!: Array<string>;
  dataSource!: MatTableDataSource<Usuario>;
  filterString!: string;
  isLoadingResults!: boolean;
  resultsLength!: number;
  usuario!: Usuario;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    @Inject(MAT_DIALOG_DATA) private _data: any,
    private _dialogRef: MatDialogRef<SelecionarUsuarioComponent>,
    private _notificationService: NotificationService,
    private _usuarioService: UsuarioService
  ) {
    this.columns = ['index', 'nome', 'cpf', 'setor'];
    this.dataSource = new MatTableDataSource();
    this.isLoadingResults = true;
    this.resultsLength = 0;
  }

  ngAfterViewInit(): void {
    this.findAllUsuarios();
  }

  async filter() {

    const page = this.paginator.pageIndex;
    const size = this.paginator.pageSize;
    const sort = this.sort.active;
    const direction = this.sort.direction;

    this.filterString = this.filterString ? this.filterString : '';
    this.isLoadingResults = true;
    await OperatorUtils.delay(1000);

    this._usuarioService.search(this.filterString, page, size, sort, direction).subscribe({

      complete: () => {
        this.isLoadingResults = false;
      },

      next: (animais) => {
        this.dataSource.data = animais.content;
        this.resultsLength = animais.totalElements;
      },

      error: (err) => {
        this.isLoadingResults = false;
        console.error(err);
        this._notificationService.show(MessageUtils.USUARIOS_GET_FAIL, NotificationType.FAIL);
      }
    });
  }

  async findAllUsuarios() {
    
    const page = this.paginator.pageIndex;
    const size = this.paginator.pageSize;
    const sort = this.sort.active;
    const direction = this.sort.direction;

    this.isLoadingResults = true;
    await OperatorUtils.delay(1000);

    this._usuarioService.findAll(page, size, sort, direction).subscribe({

      complete: () => {
        this.isLoadingResults = false;
      },

      next: (animais) => {
        this.dataSource.data = animais.content;
        this.resultsLength = animais.totalElements;
      },

      error: (err) => {
        this.isLoadingResults = false;
        console.error(err);
        this._notificationService.show(MessageUtils.USUARIOS_GET_FAIL, NotificationType.FAIL);
      }
    });
  }

  pageChange() {
    
    if (this.filterString) {
      this.filter();
      return;
    }    

    this.findAllUsuarios();
  }

  select(usuario: Usuario) {
    
    if (this._data.setor) {

      if (this._data.setor === usuario.setor) {
        this.usuario = usuario;
      }
    }

    else {
      this.usuario = usuario;
    }
  }

  sortChange() {

    this.paginator.pageIndex = 0;
    
    if (this.filterString) {
      this.filter();
      return;
    }

    this.findAllUsuarios();
  }

  submit() {
    this._dialogRef.close({ status: true, usuario: this.usuario });
  }
}