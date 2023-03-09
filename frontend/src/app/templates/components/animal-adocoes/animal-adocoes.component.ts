import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Adocao } from 'src/app/entities/adocao';
import { AnimalService } from 'src/app/services/animal.service';
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

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private _animalService: AnimalService,
    private _dialog: MatDialog,

  ) {
    this.columns = ['index', 'dataHora', 'localAdocao', 'tutor', 'acao'];
    this.dataSource = new MatTableDataSource();
    this.isLoadingResults = true;
  }

  ngAfterViewInit(): void {

    this._animalService.get().subscribe({

      next: (animal) => {

        if (animal) {
          this.animal = animal;
          this.findAllAdocoes();
        }
      }
    });
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

    this.isLoadingResults = true;
    await OperatorUtils.delay(1000);

    this.dataSource.data = this.animal.adocoes;
    this.isLoadingResults = false;
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