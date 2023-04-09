import { DatePipe } from '@angular/common';
import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Agendamento } from 'src/app/entities/agendamento';
import { User } from 'src/app/entities/user';
import { NotificationType } from 'src/app/enums/notification-type';
import { AgendamentoService } from 'src/app/services/agendamento.service';
import { AuthService } from 'src/app/services/auth.service';
import { NotificationService } from 'src/app/services/notification.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { OperatorUtils } from 'src/app/utils/operator-utils';

@Component({
  selector: 'app-agendamentos',
  templateUrl: './agendamentos.component.html',
  styleUrls: ['./agendamentos.component.sass']
})
export class AgendamentosComponent implements AfterViewInit {

  columns!: Array<string>;
  dataSource!: MatTableDataSource<Agendamento>;
  filterDate!: Date | null;
  filterString!: string;
  isLoadingResults!: boolean;
  resultsLength!: number;
  user!: User;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private _agendamentoService: AgendamentoService,
    private _authService: AuthService,
    private _datePipe: DatePipe,
    private _notificationService: NotificationService,
    private _router: Router,
  ) {
    this.columns = ['index', 'dataHora', 'animal', 'veterinario', 'acao'];
    this.dataSource = new MatTableDataSource();
    this.isLoadingResults = true;
    this.resultsLength = 0;
    this.user = this._authService.getCurrentUser();
  }

  ngAfterViewInit() {
    this.findAll();
  }

  add() {
    this._router.navigate(['/' + this.user.role.toLowerCase() + '/agendamentos/cadastro']);
  }

  async findAll() {

    const page = this.paginator.pageIndex;
    const size = this.paginator.pageSize;
    const sort = this.sort.active;
    const direction = this.sort.direction;

    this.isLoadingResults = true;
    await OperatorUtils.delay(1000);

    this._agendamentoService.findAll(page, size, sort, direction).subscribe({

      complete: () => {
        this.isLoadingResults = false;
      },

      next: (agendamentos) => {
        this.dataSource.data = agendamentos.content;
        this.resultsLength = agendamentos.totalElements;
      },

      error: (err) => {
        this.isLoadingResults = false;
        console.error(err);
        this._notificationService.show(MessageUtils.AGENDAMENTOS_GET_FAIL, NotificationType.FAIL);    
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

    this._agendamentoService.search(value, page, size, sort, direction).subscribe({

      complete: () => {
        this.isLoadingResults = false;
      },

      next: (agendamentos) => {
        this.dataSource.data = agendamentos.content;
        this.resultsLength = agendamentos.totalElements;
      },

      error: (err) => {
        this.isLoadingResults = false;
        console.error(err);
        this._notificationService.show(MessageUtils.AGENDAMENTOS_GET_FAIL, NotificationType.FAIL);    
      }
    });
  }

  show(agendamento: Agendamento) {
    this._router.navigate(['/' + this.user.role.toLowerCase() + '/agendamentos/' + agendamento.id]);
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