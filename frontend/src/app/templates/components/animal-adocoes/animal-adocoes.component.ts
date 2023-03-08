import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Adocao } from 'src/app/entities/adocao';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { OperatorUtils } from 'src/app/utils/operator-utils';

import { Animal } from '../../../entities/animal';
import { AnimalAdocoesCadastroComponent } from '../animal-adocoes-cadastro/animal-adocoes-cadastro.component';
import { AnimalAdocoesExcluirComponent } from '../animal-adocoes-excluir/animal-adocoes-excluir.component';

@Component({
  selector: 'app-animal-adocoes',
  templateUrl: './animal-adocoes.component.html',
  styleUrls: ['./animal-adocoes.component.sass']
})
export class AnimalAdocoesComponent implements AfterViewInit {

  animal!: Animal;
  columns!: Array<string>;
  dataSource!: MatTableDataSource<Adocao>;
  isLoadingResults!: boolean;
  resultsLength!: number;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private _dialog: MatDialog,
    private _facade: FacadeService
  ) {
    this.columns = ['index', 'dataHora', 'localAdocao', 'tutor', 'acao'];
    this.dataSource = new MatTableDataSource();
    this.isLoadingResults = true;
    this.resultsLength = 0;
  }

  ngAfterViewInit(): void {

    this._facade.animalGet().subscribe({

      next: (animal) => {

        if (animal) {
          this.animal = animal;
        }
      }
    });

    this.findAllAdocoes();
  }

  add() {

    this._dialog.open(AnimalAdocoesCadastroComponent, {
      data: {
        adocao: null
      },
      width: '100%'
    })
      .afterClosed().subscribe({

      next: (result) => {

        if (result && result.status) {
          this.findAllAdocoes();
        }
      }
    });
  }

  delete(adocao: Adocao) {

    this._dialog.open(AnimalAdocoesExcluirComponent, {
      data: {
        adocao: adocao
      },
      width: '100%'
    })
      .afterClosed().subscribe({

      next: (result) => {

        if (result && result.status) {
          this.findAllAdocoes();
        }
      }
    });
  }

  async findAllAdocoes() {

    const page = this.paginator.pageIndex;
    const size = this.paginator.pageSize;
    const sort = this.sort.active;
    const direction = this.sort.direction;

    this.isLoadingResults = true;
    await OperatorUtils.delay(1000);

    this._facade.animalAdocaoFindAll(this.animal.id, page, size, sort, direction).subscribe({

      complete: () => {
        this.isLoadingResults = false;
      },

      next: (adocoes) => {
        this.dataSource.data = adocoes.content;
        this.resultsLength = adocoes.totalElements;
      },

      error: (err) => {
        this.isLoadingResults = false;
        console.error(err);
        this._facade.notificationShowNotification(MessageUtils.ADOCOES_GET_FAIL, NotificationType.FAIL);
      }
    });
  }

  pageChange() {
    this.findAllAdocoes();
  }

  sortChange() {
    this.paginator.pageIndex = 0;
    this.findAllAdocoes();
  }

  update(adocao: Adocao) {

    this._dialog.open(AnimalAdocoesCadastroComponent, {
      data: {
        adocao: adocao
      },
      width: '100%'
    })
      .afterClosed().subscribe({

      next: (result) => {

        if (result && result.status) {
          this.findAllAdocoes();
        }
      }
    });
  }
}
