import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Atendimento } from 'src/app/entities/atendimento';
import { User } from 'src/app/entities/user';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';

import { AtendimentoCadastroComponent } from '../atendimento-cadastro/atendimento-cadastro.component';

@Component({
  selector: 'app-atendimentos',
  templateUrl: './atendimentos.component.html',
  styleUrls: ['./atendimentos.component.sass']
})
export class AtendimentosComponent implements OnInit {
  
  columns!: Array<string>;
  dataSource!: MatTableDataSource<Atendimento>;
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

    this.dataSource.sortingDataAccessor = (item: any, property: any) => {
      
      switch (property) {
        case 'data-hora': return new Date(item.dataHora);
        case 'animal': return item.animal.nome;
        case 'veterinario': return item.veterinario;
        default: return item[property];
      }
    };

    this.findAllAtendimentos();
  }

  add() {

    this._dialog.open(AtendimentoCadastroComponent, {
      data: {
        atendimento: null
      },
      width: '100%'
    })
    .afterClosed().subscribe({

      next: (result) => {
          
        if (result && result.status) {
          this.findAllAtendimentos();
        }
      }
    });
  }

  delete(atendimento: Atendimento) {

  }

  filter(value: string) {
    this.dataSource.filter = value.trim().toLowerCase();
  }

  findAllAtendimentos() {

    this._facade.atendimentoFindAll().subscribe({

      next: (atendimentos) => {
        this.dataSource.data = atendimentos;
      },

      error: (error) => {
        console.error(error);
        this._facade.notificationShowNotification(MessageUtils.ATENDIMENTOS_GET_FAIL, NotificationType.FAIL); 
      }
    });
  }

  update(atendimento: Atendimento) {

  }
}