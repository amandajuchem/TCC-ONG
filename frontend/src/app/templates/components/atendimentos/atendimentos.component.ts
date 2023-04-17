import { DatePipe } from '@angular/common';
import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Atendimento } from 'src/app/entities/atendimento';
import { User } from 'src/app/entities/user';
import { NotificationType } from 'src/app/enums/notification-type';
import { AtendimentoService } from 'src/app/services/atendimento.service';
import { AuthService } from 'src/app/services/auth.service';
import { NotificationService } from 'src/app/services/notification.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { OperatorUtils } from 'src/app/utils/operator-utils';

@Component({
  selector: 'app-atendimentos',
  templateUrl: './atendimentos.component.html',
  styleUrls: ['./atendimentos.component.sass']
})
export class AtendimentosComponent implements AfterViewInit {
  
  columns!: Array<string>;
  dataSource!: MatTableDataSource<Atendimento>;
  filterDate!: Date | null;
  filterString!: string;
  isLoadingResults!: boolean;
  resultsLength!: number;
  user!: User;
  
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private _atendimentoService: AtendimentoService,
    private _authService: AuthService,
    private _datePipe: DatePipe,
    private _dialog: MatDialog,
    private _notificationService: NotificationService,
    private _router: Router
  ) {
    this.columns = ['index', 'dataHora', 'animal', 'veterinario', 'acao'];
    this.dataSource = new MatTableDataSource();
    this.isLoadingResults = true;
    this.resultsLength = 0;
    this.user = this._authService.getCurrentUser();
  }

  ngAfterViewInit(): void {
    this.findAll();
  }

  add() {
    this._router.navigate(['/' + this.user.role.toLowerCase() + '/atendimentos/cadastro']);
  }

  async findAll() {

    const page = this.paginator.pageIndex;
    const size = this.paginator.pageSize;
    const sort = this.sort.active;
    const direction = this.sort.direction;

    this.isLoadingResults = true;
    await OperatorUtils.delay(1000);

    this._atendimentoService.findAll(page, size, sort, direction).subscribe({

      complete: () => {
        this.isLoadingResults = false;
      },

      next: (atendimentos) => {
        this.dataSource.data = atendimentos.content;
        this.resultsLength = atendimentos.totalElements;
      },

      error: (err) => {
        this.isLoadingResults = false;
        console.error(err);
        this._notificationService.show(MessageUtils.ATENDIMENTOS_GET_FAIL, NotificationType.FAIL);    
      }
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

    this._atendimentoService.search(value, page, size, sort, direction).subscribe({

      complete: () => {
        this.isLoadingResults = false;
      },

      next: (atendimentos) => {
        this.dataSource.data = atendimentos.content;
        this.resultsLength = atendimentos.totalElements;
      },

      error: (err) => {
        this.isLoadingResults = false;
        console.error(err);
        this._notificationService.show(MessageUtils.ATENDIMENTOS_GET_FAIL, NotificationType.FAIL);    
      }
    });
  }

  show(atendimento: Atendimento) {
    this._router.navigate(['/' + this.user.role.toLowerCase() + '/atendimentos/' + atendimento.id]);
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