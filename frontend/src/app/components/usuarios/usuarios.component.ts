import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { Usuario } from 'src/app/entities/usuario';
import { NotificationType } from 'src/app/enums/notification-type';
import { NotificationService } from 'src/app/services/notification.service';
import { RedirectService } from 'src/app/services/redirect.service';
import { UsuarioService } from 'src/app/services/usuario.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { OperatorUtils } from 'src/app/utils/operator-utils';

@Component({
  selector: 'app-usuarios',
  templateUrl: './usuarios.component.html',
  styleUrls: ['./usuarios.component.sass'],
})
export class UsuariosComponent implements AfterViewInit {
  filterString!: string;
  isLoadingResults!: boolean;
  pageIndex!: number;
  pageSize!: number;
  resultsLength!: number;
  usuarios!: Array<Usuario>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private _notificationService: NotificationService,
    private _redirectService: RedirectService,
    private _usuarioService: UsuarioService
  ) {
    this.isLoadingResults = true;
    this.resultsLength = 0;
  }

  ngAfterViewInit(): void {
    this.findAll();
  }

  add() {
    this._redirectService.toUsuario('cadastro');
  }

  async findAll() {
    const page = this.paginator.pageIndex;
    const size = this.paginator.pageSize;
    const sort = 'nome';
    const direction = 'asc';

    this.isLoadingResults = true;
    await OperatorUtils.delay(1000);

    this._usuarioService.findAll(page, size, sort, direction).subscribe({
      complete: () => {
        this.isLoadingResults = false;
      },

      next: (usuarios) => {
        this.usuarios = usuarios.content;
        this.resultsLength = usuarios.totalElements;
      },

      error: (error) => {
        this.isLoadingResults = false;
        console.error(error);
        this._notificationService.show(
          MessageUtils.USUARIO.LIST_GET_FAIL + MessageUtils.getMessage(error),
          NotificationType.FAIL
        );
      },
    });
  }

  pageChange(event: any) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;

    if (this.filterString) {
      this.search();
      return;
    }

    this.findAll();
  }

  async search() {
    const page = this.paginator.pageIndex;
    const size = this.paginator.pageSize;
    const sort = 'nome';
    const direction = 'asc';

    this.filterString = this.filterString ? this.filterString : '';
    this.isLoadingResults = true;
    await OperatorUtils.delay(1000);

    this._usuarioService
      .search(this.filterString, page, size, sort, direction)
      .subscribe({
        next: (usuarios) => {
          this.isLoadingResults = false;
          this.usuarios = usuarios.content;
          this.resultsLength = usuarios.totalElements;
        },

        error: (error) => {
          this.isLoadingResults = false;
          console.error(error);
          this._notificationService.show(
            MessageUtils.USUARIO.LIST_GET_FAIL + MessageUtils.getMessage(error),
            NotificationType.FAIL
          );
        },
      });
  }

  update(usuario: Usuario) {
    this._redirectService.toUsuario(usuario.id);
  }
}
