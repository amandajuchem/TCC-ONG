import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Animal } from 'src/app/entities/animal';
import { User } from 'src/app/entities/user';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { OperatorUtils } from 'src/app/utils/operator-utils';
import { environment } from 'src/environments/environment';

import { AnimalCadastroComponent } from '../animal-cadastro/animal-cadastro.component';

@Component({
  selector: 'app-animais',
  templateUrl: './animais.component.html',
  styleUrls: ['./animais.component.sass']
})
export class AnimaisComponent implements AfterViewInit {

  apiURL!: string;
  columns!: Array<string>;
  dataSource!: MatTableDataSource<Animal>;
  isLoadingResults!: boolean;
  resultsLength!: number;
  user!: User;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private _dialog: MatDialog,
    private _facade: FacadeService
  ) {
    this.apiURL = environment.apiURL;
    this.columns = ['index', 'nome', 'especie', 'porte', 'idade', 'acao'];
    this.dataSource = new MatTableDataSource();
    this.isLoadingResults = true;
    this.resultsLength = 0;
    this.user = this._facade.authGetCurrentUser();
  }

  ngAfterViewInit(): void {
    this.findAllAnimais();
  }

  add() {

    this._dialog.open(AnimalCadastroComponent, {
      data: {
        animal: null
      },
      width: '100%'
    })
    .afterClosed().subscribe({

      next: (result) => {

        if (result && result.status) {
          this.findAllAnimais();
        }
      }
    });
  }

  async filter(value: string) {

    const page = this.paginator.pageIndex;
    const size = this.paginator.pageSize;
    const sort = this.sort.active;
    const direction = this.sort.direction;

    this.isLoadingResults = true;
    await OperatorUtils.delay(1000);

    this._facade.animalFindByNomeContains(value, page, size, sort, direction).subscribe({

      complete: () => {
        this.isLoadingResults = false;
      },

      next: (animais) => {
        this.dataSource.data = animais.content;
        this.resultsLength = animais.totalElements;
      },

      error: (err) => {
        this.isLoadingResults = false;
        console.error(err);
        this._facade.notificationShowNotification(MessageUtils.ANIMAIS_GET_FAIL, NotificationType.FAIL);
      }
    });
  }

  async findAllAnimais() {

    const page = this.paginator.pageIndex;
    const size = this.paginator.pageSize;
    const sort = this.sort.active;
    const direction = this.sort.direction;

    this.isLoadingResults = true;
    await OperatorUtils.delay(1000);

    this._facade.animalFindAll(page, size, sort, direction).subscribe({

      complete: () => {
        this.isLoadingResults = false;
      },

      next: (animais) => {
        this.dataSource.data = animais.content;
        this.resultsLength = animais.totalElements;
      },

      error: (error) => {
        this.isLoadingResults = false;
        console.error(error);
        this._facade.notificationShowNotification(MessageUtils.ANIMAIS_GET_FAIL, NotificationType.FAIL);
      }
    });
  }

  sortChange() {
    this.paginator.pageIndex = 0;
    this.findAllAnimais();
  }

  pageChange() {
    this.findAllAnimais();
  }
}