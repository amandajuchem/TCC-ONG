import { DatePipe } from '@angular/common';
import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Agendamento } from 'src/app/entities/agendamento';
import { User } from 'src/app/entities/user';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { DateUtils } from 'src/app/utils/date-utils';
import { MessageUtils } from 'src/app/utils/message-utils';
import { OperatorUtils } from 'src/app/utils/operator-utils';

import { AgendamentoCadastroComponent } from '../agendamento-cadastro/agendamento-cadastro.component';
import { AgendamentoExcluirComponent } from '../agendamento-excluir/agendamento-excluir.component';

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
    private _datePipe: DatePipe,
    private _dialog: MatDialog,
    private _facade: FacadeService
  ) {
    this.columns = ['index', 'dataHora', 'animal', 'veterinario', 'acao'];
    this.dataSource = new MatTableDataSource();
    this.isLoadingResults = true;
    this.resultsLength = 0;
    this.user = this._facade.authGetCurrentUser();
  }

  ngAfterViewInit() {
    this.findAllAgendamentos();
  }

  add() {

    this._dialog.open(AgendamentoCadastroComponent, {
      data: {
        agendamento: null
      },
      width: '100%'
    })
    .afterClosed().subscribe({

      next: (result) => {
          
        if (result && result.status) {
          this.findAllAgendamentos();
        }
      }
    });
  }

  delete(agendamento: Agendamento) {

    this._dialog.open(AgendamentoExcluirComponent, {
      data: {
        agendamento: agendamento
      },
      width: '100%'
    })
    .afterClosed().subscribe({

      next: (result) => {
          
        if (result && result.status) {
          this.findAllAgendamentos();
        }
      }
    });
  }

  async filter(by: string) {


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

    this._facade.agendamentoSearch(value, page, size, sort, direction).subscribe({

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
        this._facade.notificationShowNotification(MessageUtils.AGENDAMENTOS_GET_FAIL, NotificationType.FAIL);    
      }
    });
  }

  async findAllAgendamentos() {

    const page = this.paginator.pageIndex;
    const size = this.paginator.pageSize;
    const sort = this.sort.active;
    const direction = this.sort.direction;

    this.isLoadingResults = true;
    await OperatorUtils.delay(1000);

    this._facade.agendamentoFindAll(page, size, sort, direction).subscribe({

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
        this._facade.notificationShowNotification(MessageUtils.AGENDAMENTOS_GET_FAIL, NotificationType.FAIL);    
      }
    });
  }

  getDateWithTimeZone(date: any) {
    return DateUtils.getDateWithTimeZone(date);
  }

  sortChange() {
    this.paginator.pageIndex = 0;
    this.findAllAgendamentos();
  }

  pageChange() {
    
    if (this.filterDate) {
      this.filter('date');
      return;
    }

    if (this.filterString) {
      this.filter('string');
      return;
    }

    this.findAllAgendamentos();
  }

  update(agendamento: Agendamento) {

    this._dialog.open(AgendamentoCadastroComponent, {
      data: {
        agendamento: agendamento
      },
      width: '100%'
    })
    .afterClosed().subscribe({

      next: (result) => {
          
        if (result && result.status) {
          this.findAllAgendamentos();
        }
      }
    });
  }
}