import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Observacao } from 'src/app/entities/observacao';
import { Tutor } from 'src/app/entities/tutor';
import { NotificationType } from 'src/app/enums/notification-type';
import { NotificationService } from 'src/app/services/notification.service';
import { ObservacaoService } from 'src/app/services/observacao.service';
import { TutorService } from 'src/app/services/tutor.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { OperatorUtils } from 'src/app/utils/operator-utils';

import { TutorObservacoesCadastroComponent } from '../tutor-observacoes-cadastro/tutor-observacoes-cadastro.component';
import { TutorObservacoesExcluirComponent } from '../tutor-observacoes-excluir/tutor-observacoes-excluir.component';

@Component({
  selector: 'app-tutor-observacoes',
  templateUrl: './tutor-observacoes.component.html',
  styleUrls: ['./tutor-observacoes.component.sass']
})
export class TutorObservacoesComponent implements AfterViewInit {

  columns!: Array<string>;
  dataSource!: MatTableDataSource<Observacao>;
  isLoadingResults!: boolean;
  resultsLength!: number;
  tutor!: Tutor;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private _dialog: MatDialog,
    private _notificationService: NotificationService,
    private _observacaoservice: ObservacaoService,
    private _tutorService: TutorService,
  ) {
    this.columns = ['index', 'createdDate', 'empty', 'acao'];
    this.dataSource = new MatTableDataSource();
    this.isLoadingResults = true;
    this.resultsLength = 0;
  }

  ngAfterViewInit(): void {

    this._tutorService.get().subscribe({

      next: (tutor) => {
        
        if (tutor) {
          this.tutor = tutor;
        }
      }
    });

    this.findAll();
  }

  add() {

    this._dialog.open(TutorObservacoesCadastroComponent, {
      data: {
        observacao: null,
        tutor: this.tutor
      },
      width: '100%'
    })
    .afterClosed().subscribe({

      next: (result) => {

        if (result && result.status) {
          this.findAll();
        }
      }
    });
  }

  delete(observacao: Observacao) {
    
    this._dialog.open(TutorObservacoesExcluirComponent, {
      data: {
        observacao: observacao
      },
      width: '100%'
    })
    .afterClosed().subscribe({

      next: (result) => {

        if (result && result.status) {
          this.findAll();
        }
      }
    });
  }

  async findAll() {

    const page = this.paginator.pageIndex;
    const size = this.paginator.pageSize;
    const sort = this.sort.active;
    const direction = this.sort.direction;

    this.isLoadingResults = true;
    await OperatorUtils.delay(1000);

    this._observacaoservice.search(this.tutor.id, page, size, sort, direction).subscribe({

      complete: () => {
        this.isLoadingResults = false;
      },

      next: (observacoes) => {
        this.dataSource.data = observacoes.content;
        this.resultsLength = observacoes.totalElements;
      },

      error: (error) => {
        this.isLoadingResults = false;
        console.error(error);
        this._notificationService.show(MessageUtils.OBSERVACOES_GET_FAIL, NotificationType.FAIL);
      }
    });
  }

  pageChange() {
    this.findAll();
  }

  sortChange() {
    this.paginator.pageIndex = 0;
    this.findAll();
  }

  update(observacao: Observacao) {

    this._dialog.open(TutorObservacoesCadastroComponent, {
      data: {
        observacao: observacao,
        tutor: this.tutor
      },
      width: '100%'
    })
    .afterClosed().subscribe({

      next: (result) => {

        if (result && result.status) {
          this.findAll();
        }
      }
    });
  }
}