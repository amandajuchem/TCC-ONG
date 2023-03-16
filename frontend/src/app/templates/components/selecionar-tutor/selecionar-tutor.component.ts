import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Tutor } from 'src/app/entities/tutor';
import { NotificationType } from 'src/app/enums/notification-type';
import { NotificationService } from 'src/app/services/notification.service';
import { TutorService } from 'src/app/services/tutor.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { OperatorUtils } from 'src/app/utils/operator-utils';

@Component({
  selector: 'app-selecionar-tutor',
  templateUrl: './selecionar-tutor.component.html',
  styleUrls: ['./selecionar-tutor.component.sass']
})
export class SelecionarTutorComponent implements AfterViewInit {

  columns!: Array<string>;
  dataSource!: MatTableDataSource<Tutor>;
  filterString!: string;
  isLoadingResults!: boolean;
  resultsLength!: number;
  tutor!: Tutor;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private _dialogRef: MatDialogRef<SelecionarTutorComponent>,
    private _notificationService: NotificationService,
    private _tutorService: TutorService
  ) {
    this.columns = ['index', 'nome', 'cpf', 'rg'];
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

    this._tutorService.findAll(page, size, sort, direction).subscribe({

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
        this._notificationService.show(MessageUtils.TUTOR_GET_FAIL, NotificationType.FAIL);
      }
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

    this._tutorService.search(this.filterString, page, size, sort, direction).subscribe({

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
        this._notificationService.show(MessageUtils.TUTOR_GET_FAIL, NotificationType.FAIL);
      }
    });
  }

  select(tutor: Tutor) {
    this.tutor = tutor;
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
    this._dialogRef.close({ status: true, tutor: this.tutor });
  }
}