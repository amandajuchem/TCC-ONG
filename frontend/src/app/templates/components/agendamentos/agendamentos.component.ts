import { Component, OnInit, ViewChild } from '@angular/core';
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

import { AgendamentoCadastroComponent } from '../agendamento-cadastro/agendamento-cadastro.component';
import { AgendamentoExcluirComponent } from '../agendamento-excluir/agendamento-excluir.component';

@Component({
  selector: 'app-agendamentos',
  templateUrl: './agendamentos.component.html',
  styleUrls: ['./agendamentos.component.sass']
})
export class AgendamentosComponent implements OnInit {

  columns!: Array<string>;
  dataSource!: MatTableDataSource<Agendamento>;
  user!: User;

  @ViewChild(MatSort, { static: false }) set sort(value: MatSort) { if (this.dataSource) this.dataSource.sort = value }
  @ViewChild(MatPaginator, { static: false }) set paginator(value: MatPaginator) { if (this.dataSource) this.dataSource.paginator = value }

  constructor(
    private _dialog: MatDialog,
    private _facade: FacadeService
  ) { }

  ngOnInit(): void {
    this.columns = ['index', 'data-hora', 'animal', 'veterinario', 'acao'];
    this.dataSource = new MatTableDataSource();
    this.user = this._facade.authGetCurrentUser();

    this.dataSource.filterPredicate = (data: Agendamento, filter: string) => {
      
      return new Date(data.dataHora).toLocaleString().includes(filter) || 
        data.animal.nome.toUpperCase().includes(filter) || 
        data.veterinario.nome.toUpperCase().includes(filter)
      ;
    }

    this.dataSource.sortingDataAccessor = (item: any, property: any) => {
      
      switch (property) {
        case 'data-hora': return new Date(item.dataHora);
        case 'animal': return item.animal.nome;
        case 'veterinario': return item.veterinario;
        default: return item[property];
      }
    };
    
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

  getDateWithTimeZone(date: any) {
    return DateUtils.getDateWithTimeZone(date);
  }

  filter(value: string) {
    this.dataSource.filter = value.trim().toUpperCase();
  }

  findAllAgendamentos() {

    this._facade.agendamentoFindAll().subscribe({

      next: (agendamentos) => {
        this.dataSource.data = agendamentos;
      },

      error: (error) => {
        console.error(error);
        this._facade.notificationShowNotification(MessageUtils.AGENDAMENTOS_GET_FAIL, NotificationType.FAIL); 
      }
    });
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