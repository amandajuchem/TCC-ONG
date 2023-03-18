import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { User } from 'src/app/entities/user';
import { Usuario } from 'src/app/entities/usuario';
import { NotificationType } from 'src/app/enums/notification-type';
import { AuthService } from 'src/app/services/auth.service';
import { NotificationService } from 'src/app/services/notification.service';
import { UsuarioService } from 'src/app/services/usuario.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { OperatorUtils } from 'src/app/utils/operator-utils';
import { environment } from 'src/environments/environment';

import { UsuarioCadastroComponent } from '../usuario-cadastro/usuario-cadastro.component';

@Component({
  selector: 'app-usuarios',
  templateUrl: './usuarios.component.html',
  styleUrls: ['./usuarios.component.sass']
})
export class UsuariosComponent implements AfterViewInit {

  apiURL!: string;
  filterString!: string;
  isLoadingResults!: boolean;
  pageIndex!: number;
  pageSize!: number;
  resultsLength!: number;
  user!: User;
  usuarios!: Array<Usuario>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private _authService: AuthService,
    private _dialog: MatDialog,
    private _notificationService: NotificationService,
    private _usuarioService: UsuarioService
  ) {
    this.apiURL = environment.apiURL;
    this.isLoadingResults = true;
    this.resultsLength = 0;
    this.user = this._authService.getCurrentUser();
  }

  ngAfterViewInit(): void {
    this.findAll();
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
          this.findAll();
        }
      }
    });
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
        this.usuarios= usuarios.content;
        this.resultsLength = usuarios.totalElements;
      },

      error: (error) => {
        this.isLoadingResults = false;
        console.error(error);
        this._notificationService.show(MessageUtils.USUARIOS_GET_FAIL, NotificationType.FAIL);
      }
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

    this._usuarioService.search(this.filterString, page, size, sort, direction).subscribe({

      complete: () => {
        this.isLoadingResults = false;
      },

      next: (usuarios) => {
        this.usuarios = usuarios.content;
        this.resultsLength = usuarios.totalElements;
      },

      error: (err) => {
        this.isLoadingResults = false;
        console.error(err);
        this._notificationService.show(MessageUtils.USUARIOS_GET_FAIL, NotificationType.FAIL);
      }
    });
  }
}