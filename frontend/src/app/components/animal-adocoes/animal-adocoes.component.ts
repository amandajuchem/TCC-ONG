import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Adocao } from 'src/app/entities/adocao';
import { Animal } from 'src/app/entities/animal';
import { NotificationType } from 'src/app/enums/notification-type';
import { AdocaoService } from 'src/app/services/adocao.service';
import { AnimalService } from 'src/app/services/animal.service';
import { NotificationService } from 'src/app/services/notification.service';
import { RedirectService } from 'src/app/services/redirect.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { OperatorUtils } from 'src/app/utils/operator-utils';

import { AnimalAdocoesCadastroComponent } from '../animal-adocoes-cadastro/animal-adocoes-cadastro.component';
import { AnimalAdocoesExcluirComponent } from '../animal-adocoes-excluir/animal-adocoes-excluir.component';

@Component({
  selector: 'app-animal-adocoes',
  templateUrl: './animal-adocoes.component.html',
  styleUrls: ['./animal-adocoes.component.sass'],
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
    private _adocaoService: AdocaoService,
    private _animalService: AnimalService,
    private _dialog: MatDialog,
    private _notificationService: NotificationService,
    private _redirectService: RedirectService
  ) {
    this.columns = ['index', 'dataHora', 'localAdocao', 'tutor', 'acao'];
    this.dataSource = new MatTableDataSource();
    this.isLoadingResults = true;
    this.resultsLength = 0;
  }

  ngAfterViewInit(): void {
    this._animalService.get().subscribe({
      next: (animal) => {
        if (animal) {
          this.animal = animal;
          this.findAll();
        }
      },
    });
  }

  add() {
    this._dialog
      .open(AnimalAdocoesCadastroComponent, {
        data: {
          adocao: null,
          animal: this.animal,
        },
        disableClose: true,
        width: '100%',
        minHeight: 'auto',
        maxHeight: '100vh'
      })
      .afterClosed()
      .subscribe({
        next: (result) => {
          if (result && result.status) {
            this._animalService.set(result.adocao.animal);  
          }
        },
      });
  }

  delete(adocao: Adocao) {
    this._dialog
      .open(AnimalAdocoesExcluirComponent, {
        data: {
          adocao: adocao,
        },
        disableClose: true,
        width: '100%',
        minHeight: 'auto',
        maxHeight: '100vh'
      })
      .afterClosed()
      .subscribe({
        next: (result) => {
          if (result && result.status) {
            this.findAll();
          }
        },
      });
  }

  async findAll() {
    const page = this.paginator.pageIndex;
    const size = this.paginator.pageSize;
    const sort = this.sort.active;
    const direction = this.sort.direction;

    this.isLoadingResults = true;
    await OperatorUtils.delay(1000);

    this._adocaoService
      .search(this.animal.id, page, size, sort, direction)
      .subscribe({
        complete: () => {
          this.isLoadingResults = false;
        },

        next: (adocoes) => {
          this.dataSource.data = adocoes.content;
          this.resultsLength = adocoes.totalElements;
        },

        error: (error) => {
          this.isLoadingResults = false;
          console.error(error);
          this._notificationService.show(
            MessageUtils.ADOCAO.LIST_GET_FAIL + MessageUtils.getMessage(error),
            NotificationType.FAIL
          );
        },
      });
  }

  pageChange() {
    this.findAll();
  }

  redirectToAnimalList() {
    this._redirectService.toAnimalList();
  }

  sortChange() {
    this.paginator.pageIndex = 0;
    this.findAll();
  }

  update(adocao: Adocao) {
    this._dialog
      .open(AnimalAdocoesCadastroComponent, {
        data: {
          adocao: adocao,
          animal: this.animal,
        },
        disableClose: true,
        width: '100%',
        minHeight: 'auto',
        maxHeight: '100vh'
      })
      .afterClosed()
      .subscribe({
        next: (result) => {
          if (result && result.status) {
            this._animalService.set(result.adocao.animal);  
          }
        },
      });
  }
}
