import { DatePipe } from '@angular/common';
import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { FeiraAdocao } from 'src/app/entities/feira-adocao';
import { NotificationType } from 'src/app/enums/notification-type';
import { FeiraAdocaoService } from 'src/app/services/feira-adocao.service';
import { NotificationService } from 'src/app/services/notification.service';
import { RedirectService } from 'src/app/services/redirect.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { OperatorUtils } from 'src/app/utils/operator-utils';

import { FeiraAdocaoExcluirComponent } from '../feira-adocao-excluir/feira-adocao-excluir.component';

@Component({
  selector: 'app-feiras-adocao',
  templateUrl: './feiras-adocao.component.html',
  styleUrls: ['./feiras-adocao.component.sass'],
})
export class FeirasAdocaoComponent implements AfterViewInit {
  columns!: Array<string>;
  dataSource!: MatTableDataSource<FeiraAdocao>;
  filterDate!: Date | null;
  filterString!: string;
  isLoadingResults!: boolean;
  resultsLength!: number;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private _datePipe: DatePipe,
    private _dialog: MatDialog,
    private _feiraAdocaoService: FeiraAdocaoService,
    private _notificationService: NotificationService,
    private _redirectService: RedirectService
  ) {
    this.columns = ['index', 'dataHora', 'nome', 'acao'];
    this.dataSource = new MatTableDataSource();
    this.isLoadingResults = true;
    this.resultsLength = 0;
  }

  ngAfterViewInit(): void {
    this.findAll();
  }

  add() {
    this._redirectService.toFeiraAdocao('cadastro');
  }

  delete(feiraAdocao: FeiraAdocao) {
    this._dialog
      .open(FeiraAdocaoExcluirComponent, {
        data: {
          feiraAdocao: feiraAdocao,
        },
        disableClose: true,
        width: '100%',
        minHeight: 'auto',
        maxHeight: '100vh'
      })
      .afterClosed()
      .subscribe({
        next: (result) => {
          if (result && result.status) {
            this.findAll();
          }
        },
      });
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

  update(feiraAdocao: FeiraAdocao) {
    this._redirectService.toFeiraAdocao(feiraAdocao.id);
  }
}
