import { DatePipe } from '@angular/common';
import { AfterViewInit, Component, Inject, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { FeiraAdocao } from 'src/app/entities/feira-adocao';
import { NotificationType } from 'src/app/enums/notification-type';
import { FeiraAdocaoService } from 'src/app/services/feira-adocao.service';
import { NotificationService } from 'src/app/services/notification.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { OperatorUtils } from 'src/app/utils/operator-utils';

@Component({
  selector: 'app-selecionar-feira-adocao',
  templateUrl: './selecionar-feira-adocao.component.html',
  styleUrls: ['./selecionar-feira-adocao.component.sass']
})
export class SelecionarFeiraAdocaoComponent implements AfterViewInit {
  columns!: Array<string>;
  dataSource!: MatTableDataSource<FeiraAdocao>;
  feiraAdocao!: FeiraAdocao | null;
  feirasAdocao!: Array<FeiraAdocao>;
  filterDate!: Date | null;
  filterString!: string;
  isLoadingResults!: boolean;
  resultsLength!: number;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    @Inject(MAT_DIALOG_DATA) private _data: any,
    private _datePipe: DatePipe,
    private _dialogRef: MatDialogRef<SelecionarFeiraAdocaoComponent>,
    private _feiraAdocaoService: FeiraAdocaoService,
    private _notificationService: NotificationService
  ) {
    this.feirasAdocao = [];
    this.columns = ['index', 'dataHora', 'nome'];
    this.dataSource = new MatTableDataSource();
    this.isLoadingResults = true;
    this.resultsLength = 0;
  }

  ngAfterViewInit(): void {
    this.findAll();
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

  isSelected(feiraAdocao: FeiraAdocao) {
    return this._data.multiplus
      ? this.feirasAdocao?.some((f) => f.id === feiraAdocao.id)
      : this.feiraAdocao?.id === feiraAdocao.id;
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

  select(feiraAdocao: FeiraAdocao) {
    if (this._data.multiplus) {
      const exists = this.feirasAdocao.some(f => feiraAdocao.id === f.id);
      this.feirasAdocao = exists ? this.feirasAdocao.filter(f => feiraAdocao.id !== f.id) : [...this.feirasAdocao, feiraAdocao];
    } else {
      this.feiraAdocao = (this.feiraAdocao && this.feiraAdocao.id === feiraAdocao.id) ? null : feiraAdocao;
    }
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

  submit() {
    if (this.feiraAdocao || this.feirasAdocao.length > 0) {
      this._dialogRef.close({
        status: true,
        feiraAdocao: this.feiraAdocao,
        feirasAdocao: this.feirasAdocao,
      });
    }
  }
}
