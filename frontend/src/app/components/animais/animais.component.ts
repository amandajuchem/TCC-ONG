import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { Animal } from 'src/app/entities/animal';
import { NotificationType } from 'src/app/enums/notification-type';
import { AnimalService } from 'src/app/services/animal.service';
import { NotificationService } from 'src/app/services/notification.service';
import { RedirectService } from 'src/app/services/redirect.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { OperatorUtils } from 'src/app/utils/operator-utils';
import { environment } from 'src/environments/environment';

import { AnimalExcluirComponent } from '../animal-excluir/animal-excluir.component';

@Component({
  selector: 'app-animais',
  templateUrl: './animais.component.html',
  styleUrls: ['./animais.component.sass'],
})
export class AnimaisComponent implements AfterViewInit {
  animais!: Array<Animal>;
  apiURL!: string;
  filterString!: string;
  isLoadingResults!: boolean;
  pageIndex!: number;
  pageSize!: number;
  resultsLength!: number;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private _animalService: AnimalService,
    private _dialog: MatDialog,
    private _notificationService: NotificationService,
    private _redirectService: RedirectService
  ) {
    this.apiURL = environment.apiURL;
    this.isLoadingResults = true;
    this.resultsLength = 0;
  }

  ngAfterViewInit(): void {
    this.findAll();
  }

  add() {
    this._redirectService.toAnimal('cadastro');
  }

  delete(animal: Animal) {
    this._dialog
      .open(AnimalExcluirComponent, {
        data: {
          animal: animal,
        },
        width: '100%',
      })
      .afterClosed()
      .subscribe({
        next: (result) => {
          if (result) {
            this.search();
          }
        },
      });
  }

  async findAll() {
    const page = this.paginator.pageIndex;
    const size = this.paginator.pageSize;
    const sort = 'nome';
    const direction = 'asc';

    this.isLoadingResults = true;
    await OperatorUtils.delay(1000);

    this._animalService.findAll(page, size, sort, direction).subscribe({
      complete: () => {
        this.isLoadingResults = false;
      },

      next: (animais) => {
        this.animais = animais.content;
        this.resultsLength = animais.totalElements;
      },

      error: (error) => {
        this.isLoadingResults = false;
        console.error(error);
        this._notificationService.show(
          MessageUtils.ANIMAL.LIST_GET_FAIL + MessageUtils.getMessage(error),
          NotificationType.FAIL
        );
      },
    });
  }

  pageChange(event: any) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;

    if (this.filterString) {
      this.search();
      return;
    }

    this.findAll();
  }

  async search() {
    const page = this.paginator.pageIndex;
    const size = this.paginator.pageSize;
    const sort = 'nome';
    const direction = 'asc';

    this.filterString = this.filterString ? this.filterString : '';
    this.isLoadingResults = true;
    await OperatorUtils.delay(1000);

    this._animalService
      .search(this.filterString, page, size, sort, direction)
      .subscribe({
        complete: () => {
          this.isLoadingResults = false;
        },

        next: (animais) => {
          this.animais = animais.content;
          this.resultsLength = animais.totalElements;
        },

        error: (error) => {
          this.isLoadingResults = false;
          console.error(error);
          this._notificationService.show(
            MessageUtils.ANIMAL.LIST_GET_FAIL + MessageUtils.getMessage(error),
            NotificationType.FAIL
          );
        },
      });
  }

  update(animal: Animal) {
    this._redirectService.toAnimal(animal.id);
  }
}
