import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Tutor } from 'src/app/entities/tutor';
import { User } from 'src/app/entities/user';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { OperatorUtils } from 'src/app/utils/operator-utils';

import { TutorCadastroComponent } from '../tutor-cadastro/tutor-cadastro.component';

@Component({
  selector: 'app-tutores',
  templateUrl: './tutores.component.html',
  styleUrls: ['./tutores.component.sass']
})
export class TutoresComponent implements AfterViewInit {

  columns!: Array<string>;
  dataSource!: MatTableDataSource<Tutor>;
  filterString!: string;
  isLoadingResults!: boolean;
  resultsLength!: number;
  user!: User;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private _dialog: MatDialog,
    private _facade: FacadeService
  ) {
    this.columns = ['index', 'nome', 'cpf', 'rg', 'acao'];
    this.dataSource = new MatTableDataSource();
    this.isLoadingResults = true;
    this.resultsLength = 0;
    this.user = this._facade.authGetCurrentUser();
  }

  ngAfterViewInit(): void {
    this.findAllTutores();
  }

  add() {

    this._dialog.open(TutorCadastroComponent, {
      data: {
        animal: null
      },
      width: '100%'
    })
    .afterClosed().subscribe({

      next: (result) => {

        if (result && result.status) {
          this.findAllTutores();
        }
      }
    });
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

      next: (tutores) => {
        this.dataSource.data = tutores.content;
        this.resultsLength = tutores.totalElements;
      },

      error: (err) => {
        this.isLoadingResults = false;
        console.error(err);
        this._facade.notificationShowNotification(MessageUtils.TUTORES_GET_FAIL, NotificationType.FAIL);
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

      next: (tutores) => {
        this.dataSource.data = tutores.content;
        this.resultsLength = tutores.totalElements;
      },

      error: (error) => {
        this.isLoadingResults = false;
        console.error(error);
        this._facade.notificationShowNotification(MessageUtils.TUTORES_GET_FAIL, NotificationType.FAIL);
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

  sortChange() {
    
    this.paginator.pageIndex = 0;

    if (this.filterString) {
      this.filter();
      return;
    }

    this.findAllTutores();
  }
}