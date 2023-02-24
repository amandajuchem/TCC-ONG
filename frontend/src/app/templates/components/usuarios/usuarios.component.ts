import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { User } from 'src/app/entities/user';
import { Usuario } from 'src/app/entities/usuario';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { OperatorUtils } from 'src/app/utils/operator-utils';

import { UsuarioCadastroComponent } from '../usuario-cadastro/usuario-cadastro.component';

@Component({
  selector: 'app-usuarios',
  templateUrl: './usuarios.component.html',
  styleUrls: ['./usuarios.component.sass']
})
export class UsuariosComponent implements AfterViewInit {

  columns!: Array<string>;
  dataSource!: MatTableDataSource<Usuario>;
  filterString!: string;
  isLoadingResults!: boolean;
  resultsLength!: number;
  user!: User;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private _dialog: MatDialog,
    private _facade: FacadeService
  ) {
    this.columns = ['index', 'nome', 'cpf', 'setor', 'acao'];
    this.dataSource = new MatTableDataSource();
    this.isLoadingResults = true;
    this.resultsLength = 0;
    this.user = this._facade.authGetCurrentUser();
  }

  ngAfterViewInit(): void {
    this.findAllUsuarios();
  }

  add() {

    this._dialog.open(UsuarioCadastroComponent, {
      data: {
        animal: null
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

  async filter() {

    const page = this.paginator.pageIndex;
    const size = this.paginator.pageSize;
    const sort = this.sort.active;
    const direction = this.sort.direction;

    this.filterString = this.filterString ? this.filterString : '';
    this.isLoadingResults = true;
    await OperatorUtils.delay(1000);

    this._facade.usuarioSearch(this.filterString, page, size, sort, direction).subscribe({

      complete: () => {
        this.isLoadingResults = false;
      },

      next: (usuarioes) => {
        this.dataSource.data = usuarioes.content;
        this.resultsLength = usuarioes.totalElements;
      },

      error: (err) => {
        this.isLoadingResults = false;
        console.error(err);
        this._facade.notificationShowNotification(MessageUtils.USUARIOS_GET_FAIL, NotificationType.FAIL);
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

    this._facade.usuarioFindAll(page, size, sort, direction).subscribe({

      complete: () => {
        this.isLoadingResults = false;
      },

      next: (usuarioes) => {
        this.dataSource.data = usuarioes.content;
        this.resultsLength = usuarioes.totalElements;
      },

      error: (error) => {
        this.isLoadingResults = false;
        console.error(error);
        this._facade.notificationShowNotification(MessageUtils.USUARIOS_GET_FAIL, NotificationType.FAIL);
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

  sortChange() {

    this.paginator.pageIndex = 0;
    
    if (this.filterString) {
      this.filter();
      return;
    }

    this.findAllUsuarios();
  } 
}