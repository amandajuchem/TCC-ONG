import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Exame } from 'src/app/entities/exame';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { OperatorUtils } from 'src/app/utils/operator-utils';

@Component({
  selector: 'app-selecionar-exame',
  templateUrl: './selecionar-exame.component.html',
  styleUrls: ['./selecionar-exame.component.sass']
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
    private _facade: FacadeService,
    private _matDialogRef: MatDialogRef<SelecionarExameComponent>
  ) {
    this.columns = ['index', 'nome', 'categoria'];
    this.dataSource = new MatTableDataSource();
    this.isLoadingResults = true;
    this.resultsLength = 0;
  }

  ngAfterViewInit(): void {
    this.findAllExames();
  }

  async filter() {

    const page = this.paginator.pageIndex;
    const size = this.paginator.pageSize;
    const sort = this.sort.active;
    const direction = this.sort.direction;

    this.filterString = this.filterString ? this.filterString : '';
    this.isLoadingResults = true;
    await OperatorUtils.delay(1000);

    this._facade.exameSearch(this.filterString, page, size, sort, direction).subscribe({

      complete: () => {
        this.isLoadingResults = false;
      },

      next: (exames) => {
        this.dataSource.data = exames.content;
        this.resultsLength = exames.totalElements;
      },

      error: (err) => {
        this.isLoadingResults = false;
        console.error(err);
        this._facade.notificationShowNotification(MessageUtils.EXAMES_GET_FAIL, NotificationType.FAIL);
      }
    });
  }

  async findAllExames() {
    
    const page = this.paginator.pageIndex;
    const size = this.paginator.pageSize;
    const sort = this.sort.active;
    const direction = this.sort.direction;

    this.isLoadingResults = true;
    await OperatorUtils.delay(1000);

    this._facade.exameFindAll(page, size, sort, direction).subscribe({

      complete: () => {
        this.isLoadingResults = false;
      },

      next: (exames) => {
        this.dataSource.data = exames.content;
        this.resultsLength = exames.totalElements;
      },

      error: (err) => {
        this.isLoadingResults = false;
        console.error(err);
        this._facade.notificationShowNotification(MessageUtils.EXAMES_GET_FAIL, NotificationType.FAIL);
      }
    });
  }

  pageChange() {
    
    if (this.filterString) {
      this.filter();
      return;
    }    

    this.findAllExames();
  }

  select(exame: Exame) {
    this.exame = exame;
  }

  sortChange() {

    this.paginator.pageIndex = 0;
    
    if (this.filterString) {
      this.filter();
      return;
    }

    this.findAllExames();
  }

  submit() {
    this._matDialogRef.close({ status: true, exame: this.exame });
  }
}