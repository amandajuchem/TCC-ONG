import { DatePipe } from '@angular/common';
import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Authentication } from 'src/app/entities/authentication';
import { FeiraAdocao } from 'src/app/entities/feira-adocao';
import { NotificationType } from 'src/app/enums/notification-type';
import { AuthService } from 'src/app/services/auth.service';
import { FeiraAdocaoService } from 'src/app/services/feira-adocao.service';
import { NotificationService } from 'src/app/services/notification.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { OperatorUtils } from 'src/app/utils/operator-utils';

@Component({
  selector: 'app-feiras-adocao',
  templateUrl: './feiras-adocao.component.html',
  styleUrls: ['./feiras-adocao.component.sass'],
})
export class FeirasAdocaoComponent implements AfterViewInit {
  authentication!: Authentication;
  columns!: Array<string>;
  dataSource!: MatTableDataSource<FeiraAdocao>;
  filterDate!: Date | null;
  filterString!: string;
  isLoadingResults!: boolean;
  resultsLength!: number;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private _authService: AuthService,
    private _datePipe: DatePipe,
    private _feiraAdocaoService: FeiraAdocaoService,
    private _notificationService: NotificationService,
    private _router: Router
  ) {
    this.authentication = this._authService.getAuthentication();
    this.columns = [
      'index',
      'dataHora',
      'nome',
      'totalAnimais',
      'totalUsuarios',
      'acao',
    ];
    this.dataSource = new MatTableDataSource();
    this.isLoadingResults = true;
    this.resultsLength = 0;
  }

  ngAfterViewInit(): void {
    this.findAll();
  }

  add() {
    this._router.navigate([
      '/' + this.authentication.role.toLowerCase() + '/feiras-adocao/cadastro',
    ]);
  }

  async findAll() {
    const page = this.paginator.pageIndex;
    const size = this.paginator.pageSize;
    const sort = this.sort.active;
    const direction = this.sort.direction;

    this.isLoadingResults = true;
    await OperatorUtils.delay(1000);

    this._feiraAdocaoService.findAll(page, size, sort, direction).subscribe({
      complete: () => {
        this.isLoadingResults = false;
      },

      next: (feirasAdocao) => {
        this.dataSource.data = feirasAdocao.content;
        this.resultsLength = feirasAdocao.totalElements;
      },

      error: (error) => {
        this.isLoadingResults = false;
        console.error(error);
        this._notificationService.show(
          MessageUtils.FEIRA_ADOCAO.LIST_GET_FAIL +
            MessageUtils.getMessage(error),
          NotificationType.FAIL
        );
      },
    });
  }

  pageChange() {
    if (this.filterDate) {
      this.search('date');
      return;
    }

    if (this.filterString) {
      this.search('string');
      return;
    }

    this.findAll();
  }

  async search(by: string) {
    let value: any = null;

    if (by === 'date') {
      value = this._datePipe.transform(this.filterDate, 'yyyy-MM-dd');
    }

    if (by === 'string') {
      value = this.filterString ? this.filterString : '';
    }

    const page = this.paginator.pageIndex;
    const size = this.paginator.pageSize;
    const sort = this.sort.active;
    const direction = this.sort.direction;

    this.isLoadingResults = true;
    await OperatorUtils.delay(1000);

    this._feiraAdocaoService
      .search(value, page, size, sort, direction)
      .subscribe({
        complete: () => {
          this.isLoadingResults = false;
        },

        next: (feirasAdocao) => {
          this.dataSource.data = feirasAdocao.content;
          this.resultsLength = feirasAdocao.totalElements;
        },

        error: (error) => {
          this.isLoadingResults = false;
          console.error(error);
          this._notificationService.show(
            MessageUtils.FEIRA_ADOCAO.LIST_GET_FAIL +
              MessageUtils.getMessage(error),
            NotificationType.FAIL
          );
        },
      });
  }

  show(feiraAdocao: FeiraAdocao) {
    this._router.navigate([
      '/' +
        this.authentication.role.toLowerCase() +
        '/feiras-adocao/' +
        feiraAdocao.id,
    ]);
  }

  sortChange() {
    this.paginator.pageIndex = 0;

    if (this.filterDate) {
      this.search('date');
      return;
    }

    if (this.filterString) {
      this.search('string');
      return;
    }

    this.findAll();
  }
}
