import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Animal } from 'src/app/entities/animal';
import { User } from 'src/app/entities/user';
import { NotificationType } from 'src/app/enums/notification-type';
import { AnimalService } from 'src/app/services/animal.service';
import { AuthService } from 'src/app/services/auth.service';
import { NotificationService } from 'src/app/services/notification.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { OperatorUtils } from 'src/app/utils/operator-utils';

import { AnimalCadastroComponent } from '../animal-cadastro/animal-cadastro.component';

@Component({
  selector: 'app-animais',
  templateUrl: './animais.component.html',
  styleUrls: ['./animais.component.sass']
})
export class AnimaisComponent implements AfterViewInit {

  columns!: Array<string>;
  dataSource!: MatTableDataSource<Animal>;
  filterString!: string;
  isLoadingResults!: boolean;
  resultsLength!: number;
  user!: User;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private _animalService: AnimalService,
    private _authService: AuthService,
    private _dialog: MatDialog,
    private _notificationService: NotificationService
  ) {
    this.columns = ['index', 'nome', 'especie', 'porte', 'idade', 'acao'];
    this.dataSource = new MatTableDataSource();
    this.isLoadingResults = true;
    this.resultsLength = 0;
    this.user = this._authService.getCurrentUser();
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

  async filter() {

    const page = this.paginator.pageIndex;
    const size = this.paginator.pageSize;
    const sort = this.sort.active;
    const direction = this.sort.direction;

    this.filterString = this.filterString ? this.filterString : '';
    this.isLoadingResults = true;
    await OperatorUtils.delay(1000);

    this._animalService.search(this.filterString, page, size, sort, direction).subscribe({

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
        this._notificationService.show(MessageUtils.ANIMAIS_GET_FAIL, NotificationType.FAIL);
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

    this._animalService.findAll(page, size, sort, direction).subscribe({

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
        this._notificationService.show(MessageUtils.ANIMAIS_GET_FAIL, NotificationType.FAIL);
      }
    });
  }

  pageChange() {

    if (this.filterString) {
      this.filter();
      return;
    }

    this.findAllAnimais();
  }

  sortChange() {

    this.paginator.pageIndex = 0;
    
    if (this.filterString) {
      this.filter();
      return;
    }
    
    this.findAllAnimais();
  }
}