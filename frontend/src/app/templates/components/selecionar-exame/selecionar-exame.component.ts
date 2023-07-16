import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Exame } from 'src/app/entities/exame';
import { NotificationType } from 'src/app/enums/notification-type';
import { ExameService } from 'src/app/services/exame.service';
import { NotificationService } from 'src/app/services/notification.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { OperatorUtils } from 'src/app/utils/operator-utils';

@Component({
  selector: 'app-selecionar-exame',
  templateUrl: './selecionar-exame.component.html',
  styleUrls: ['./selecionar-exame.component.sass'],
})
export class SelecionarExameComponent implements AfterViewInit {
  columns!: Array<string>;
  dataSource!: MatTableDataSource<Exame>;
  exame!: Exame;
  filterString!: string;
  isLoadingResults!: boolean;
  resultsLength!: number;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private _dialogRef: MatDialogRef<SelecionarExameComponent>,
    private _exameService: ExameService,
    private _notificationService: NotificationService
  ) {
    this.columns = ['index', 'nome', 'categoria'];
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

    this._exameService.findAll(page, size, sort, direction).subscribe({
      complete: () => {
        this.isLoadingResults = false;
      },

      next: (exames) => {
        this.dataSource.data = exames.content;
        this.resultsLength = exames.totalElements;
      },

      error: (error) => {
        this.isLoadingResults = false;
        console.error(error);
        this._notificationService.show(
          MessageUtils.EXAME.LIST_GET_FAIL + MessageUtils.getMessage(error),
          NotificationType.FAIL
        );
      },
    });
  }

  pageChange() {
    if (this.filterString) {
      this.search();
      return;
    }

    this.findAll();
  }

  async search() {
    const page = this.paginator.pageIndex;
    const size = this.paginator.pageSize;
    const sort = this.sort.active;
    const direction = this.sort.direction;

    this.filterString = this.filterString ? this.filterString : '';
    this.isLoadingResults = true;
    await OperatorUtils.delay(1000);

    this._exameService
      .search(this.filterString, page, size, sort, direction)
      .subscribe({
        complete: () => {
          this.isLoadingResults = false;
        },

        next: (exames) => {
          this.dataSource.data = exames.content;
          this.resultsLength = exames.totalElements;
        },

        error: (error) => {
          this.isLoadingResults = false;
          console.error(error);
          this._notificationService.show(
            MessageUtils.EXAME.LIST_GET_FAIL + MessageUtils.getMessage(error),
            NotificationType.FAIL
          );
        },
      });
  }

  select(exame: Exame) {
    this.exame = exame;
  }

  sortChange() {
    this.paginator.pageIndex = 0;

    if (this.filterString) {
      this.search();
      return;
    }

    this.findAll();
  }

  submit() {
    this._dialogRef.close({ status: true, exame: this.exame });
  }
}
