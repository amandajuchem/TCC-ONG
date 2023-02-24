import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Tutor } from 'src/app/entities/tutor';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { OperatorUtils } from 'src/app/utils/operator-utils';

@Component({
  selector: 'app-selecionar-tutor',
  templateUrl: './selecionar-tutor.component.html',
  styleUrls: ['./selecionar-tutor.component.sass']
})
export class SelecionarTutorComponent implements AfterViewInit {

  tutor!: Tutor;
  columns!: Array<string>;
  dataSource!: MatTableDataSource<Tutor>;
  filterString!: string;
  isLoadingResults!: boolean;
  resultsLength!: number;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private _facade: FacadeService,
    private _matDialogRef: MatDialogRef<SelecionarTutorComponent>
  ) {
    this.columns = ['index', 'nome', 'cpf', 'rg'];
    this.dataSource = new MatTableDataSource();
    this.isLoadingResults = true;
    this.resultsLength = 0;
  }

  ngAfterViewInit(): void {
    this.findAllTutores();
  }

  async filter() {

    const page = this.paginator.pageIndex;
    const size = this.paginator.pageSize;
    const sort = this.sort.active;
    const direction = this.sort.direction;

    this.filterString = this.filterString ? this.filterString : '';
    this.isLoadingResults = true;
    await OperatorUtils.delay(1000);

    this._facade.tutorSearch(this.filterString, page, size, sort, direction).subscribe({

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
        this._facade.notificationShowNotification(MessageUtils.TUTOR_GET_FAIL, NotificationType.FAIL);
      }
    });
  }

  async findAllTutores() {
    
    const page = this.paginator.pageIndex;
    const size = this.paginator.pageSize;
    const sort = this.sort.active;
    const direction = this.sort.direction;

    this.isLoadingResults = true;
    await OperatorUtils.delay(1000);

    this._facade.tutorFindAll(page, size, sort, direction).subscribe({

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
        this._facade.notificationShowNotification(MessageUtils.TUTOR_GET_FAIL, NotificationType.FAIL);
      }
    });
  }

  pageChange() {
    
    if (this.filterString) {
      this.filter();
      return;
    }    

    this.findAllTutores();
  }

  select(tutor: Tutor) {
    this.tutor = tutor;
  }

  sortChange() {

    this.paginator.pageIndex = 0;
    
    if (this.filterString) {
      this.filter();
      return;
    }

    this.findAllTutores();
  }

  submit() {
    this._matDialogRef.close({ status: true, tutor: this.tutor });
  }
}