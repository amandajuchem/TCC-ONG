import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Usuario } from 'src/app/entities/usuario';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { OperatorUtils } from 'src/app/utils/operator-utils';

@Component({
  selector: 'app-selecionar-usuario',
  templateUrl: './selecionar-usuario.component.html',
  styleUrls: ['./selecionar-usuario.component.sass']
})
export class SelecionarUsuarioComponent implements AfterViewInit {

  usuario!: Usuario;
  columns!: Array<string>;
  dataSource!: MatTableDataSource<Usuario>;
  filterString!: string;
  isLoadingResults!: boolean;
  resultsLength!: number;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private _facade: FacadeService,
    private _matDialogRef: MatDialogRef<SelecionarUsuarioComponent>
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

    this._facade.usuarioSearch(this.filterString, page, size, sort, direction).subscribe({

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

      next: (animais) => {
        this.dataSource.data = animais.content;
        this.resultsLength = animais.totalElements;
      },

      error: (err) => {
        this.isLoadingResults = false;
        console.error(err);
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

  select(usuario: Usuario) {
    this.usuario = usuario;
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
    this._matDialogRef.close({ status: true, usuario: this.usuario });
  }
}