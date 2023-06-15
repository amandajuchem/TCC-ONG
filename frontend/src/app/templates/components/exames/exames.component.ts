import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Authentication } from 'src/app/entities/authentication';
import { Exame } from 'src/app/entities/exame';
import { NotificationType } from 'src/app/enums/notification-type';
import { AuthService } from 'src/app/services/auth.service';
import { ExameService } from 'src/app/services/exame.service';
import { NotificationService } from 'src/app/services/notification.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { OperatorUtils } from 'src/app/utils/operator-utils';

@Component({
  selector: 'app-exames',
  templateUrl: './exames.component.html',
  styleUrls: ['./exames.component.sass']
})
export class ExamesComponent implements AfterViewInit {

  authentication!: Authentication;
  columns!: Array<string>;
  dataSource!: MatTableDataSource<Exame>;
  filterString!: string;
  isLoadingResults!: boolean;
  resultsLength!: number;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private _authService: AuthService,
    private _exameService: ExameService,
    private _notificationService: NotificationService,
    private _router: Router
  ) {
    this.authentication = this._authService.getAuthentication();
    this.columns = ['index', 'nome', 'categoria', 'acao'];
    this.dataSource = new MatTableDataSource();
    this.isLoadingResults = true;
    this.resultsLength = 0;
  }

  ngAfterViewInit(): void {
    this.findAll();
  }

  add() {
    this._router.navigate(['/' + this.authentication.role.toLowerCase() + '/exames/cadastro']);
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
        this._notificationService.show(MessageUtils.EXAME.LIST_GET_FAIL + MessageUtils.getMessage(error), NotificationType.FAIL);
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

    this._exameService.search(this.filterString, page, size, sort, direction).subscribe({

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
        this._notificationService.show(MessageUtils.EXAME.LIST_GET_FAIL + MessageUtils.getMessage(error), NotificationType.FAIL);
      }
    });
  }

  show(exame: Exame) {
    this._router.navigate(['/' + this.authentication.role.toLowerCase() + '/exames/' + exame.id]);
  }

  sortChange() {

    this.paginator.pageIndex = 0;
    
    if (this.filterString) {
      this.search();
      return;
    }
    
    this.findAll();
  }
}