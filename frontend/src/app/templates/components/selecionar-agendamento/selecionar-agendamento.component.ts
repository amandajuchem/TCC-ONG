import { DatePipe } from '@angular/common';
import { AfterViewInit, Component, Inject, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Agendamento } from 'src/app/entities/agendamento';
import { NotificationType } from 'src/app/enums/notification-type';
import { AgendamentoService } from 'src/app/services/agendamento.service';
import { NotificationService } from 'src/app/services/notification.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { OperatorUtils } from 'src/app/utils/operator-utils';

@Component({
  selector: 'app-selecionar-agendamento',
  templateUrl: './selecionar-agendamento.component.html',
  styleUrls: ['./selecionar-agendamento.component.sass'],
})
export class SelecionarAgendamentoComponent implements AfterViewInit {
  agendamento!: Agendamento;
  agendamentos!: Array<Agendamento>;
  columns!: Array<string>;
  dataSource!: MatTableDataSource<Agendamento>;
  filterDate!: Date | null;
  filterString!: string;
  isLoadingResults!: boolean;
  resultsLength!: number;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    @Inject(MAT_DIALOG_DATA) private _data: any,
    private _agendamentoService: AgendamentoService,
    private _datePipe: DatePipe,
    private _dialogRef: MatDialogRef<SelecionarAgendamentoComponent>,
    private _notificationService: NotificationService
  ) {
    this.agendamentos = [];
    this.columns = ['index', 'dataHora', 'animal', 'veterinario'];
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

    this._agendamentoService.findAll(page, size, sort, direction).subscribe({
      complete: () => {
        this.isLoadingResults = false;
      },

      next: (agendamentos) => {
        this.dataSource.data = agendamentos.content;
        this.resultsLength = agendamentos.totalElements;
      },

      error: (error) => {
        this.isLoadingResults = false;
        console.error(error);
        this._notificationService.show(
          MessageUtils.AGENDAMENTO.LIST_GET_FAIL +
            MessageUtils.getMessage(error),
          NotificationType.FAIL
        );
      },
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

    this._agendamentoService
      .search(value, page, size, sort, direction)
      .subscribe({
        complete: () => {
          this.isLoadingResults = false;
        },

        next: (agendamentos) => {
          this.dataSource.data = agendamentos.content;
          this.resultsLength = agendamentos.totalElements;
        },

        error: (error) => {
          this.isLoadingResults = false;
          console.error(error);
          this._notificationService.show(
            MessageUtils.AGENDAMENTO.LIST_GET_FAIL +
              MessageUtils.getMessage(error),
            NotificationType.FAIL
          );
        },
      });
  }

  select(agendamento: Agendamento) {
    this._data.multiplus
      ? this.agendamentos.push(agendamento)
      : (this.agendamento = agendamento);
  }

  isSelected(agendamento: Agendamento) {
    return this._data.multiplus
      ? this.agendamentos?.some((a) => a.id === agendamento.id)
      : this.agendamento?.id === agendamento.id;
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
    this._dialogRef.close({
      status: true,
      agendamento: this.agendamento,
      agendamentos: this.agendamentos,
    });
  }
}
