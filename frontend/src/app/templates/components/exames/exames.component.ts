import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Exame } from 'src/app/entities/exame';
import { User } from 'src/app/entities/user';
import { FacadeService } from 'src/app/services/facade.service';
import { ExameCadastroComponent } from '../exame-cadastro/exame-cadastro.component';
import { MessageUtils } from 'src/app/utils/message-utils';
import { NotificationType } from 'src/app/enums/notification-type';
import { ExameExcluirComponent } from '../exame-excluir/exame-excluir.component';

@Component({
  selector: 'app-exames',
  templateUrl: './exames.component.html',
  styleUrls: ['./exames.component.sass']
})
export class ExamesComponent implements OnInit {

  columns!: Array<string>;
  dataSource!: MatTableDataSource<Exame>;
  user!: User;

  @ViewChild(MatSort, { static: false }) set sort(value: MatSort) { if (this.dataSource) this.dataSource.sort = value }
  @ViewChild(MatPaginator, { static: false }) set paginator(value: MatPaginator) { if (this.dataSource) this.dataSource.paginator = value }

  constructor(
    private _dialog: MatDialog,
    private _facade: FacadeService
  ) { }

  ngOnInit(): void {

    this.columns = ['index', 'nome', 'categoria', 'acao'];
    this.dataSource = new MatTableDataSource();
    this.user = this._facade.authGetCurrentUser();
    this.findAllExames();
  }

  add() {

    this._dialog.open(ExameCadastroComponent, {
      data: {
        exame: null
      },
      width: '100%'
    })
    .afterClosed().subscribe({

      next: (result) => {
          
        if (result && result.status) {
          this.findAllExames();
        }
      }
    });
  }

  delete(exame: Exame) {

    this._dialog.open(ExameExcluirComponent, {
      data: {
        exame: exame
      },
      width: '100%'
    })
    .afterClosed().subscribe({

      next: (result) => {
          
        if (result && result.status) {
          this.findAllExames();
        }
      }
    });
  }

  filter(value: string) {
    this.dataSource.filter = value.trim().toLowerCase();
  }

  findAllExames() {

    this._facade.exameFindAll().subscribe({

      next: (exames) => {
        this.dataSource.data = exames;
      },

      error: (error) => {
        console.error(error);
        this._facade.notificationShowNotification(MessageUtils.EXAMES_GET_FAIL, NotificationType.FAIL); 
      }
    });
  }

  update(exame: Exame) {
    
    this._dialog.open(ExameCadastroComponent, {
      data: {
        exame: exame
      },
      width: '100%'
    })
    .afterClosed().subscribe({

      next: (result) => {
          
        if (result && result.status) {
          this.findAllExames();
        }
      }
    });
  }
}