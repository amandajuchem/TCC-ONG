import { AfterViewInit, Component, Inject, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Animal } from 'src/app/entities/animal';
import { NotificationType } from 'src/app/enums/notification-type';
import { AnimalService } from 'src/app/services/animal.service';
import { NotificationService } from 'src/app/services/notification.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { OperatorUtils } from 'src/app/utils/operator-utils';

@Component({
  selector: 'app-selecionar-animal',
  templateUrl: './selecionar-animal.component.html',
  styleUrls: ['./selecionar-animal.component.sass'],
})
export class SelecionarAnimalComponent implements AfterViewInit {
  animal!: Animal;
  animais!: Array<Animal>;
  columns!: Array<string>;
  dataSource!: MatTableDataSource<Animal>;
  filterString!: string;
  isLoadingResults!: boolean;
  resultsLength!: number;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    @Inject(MAT_DIALOG_DATA) private _data: any,
    private _animalService: AnimalService,
    private _dialogRef: MatDialogRef<SelecionarAnimalComponent>,
    private _notificationService: NotificationService
  ) {
    this.animais = [];
    this.columns = ['index', 'nome', 'especie', 'porte', 'idade'];
    this.dataSource = new MatTableDataSource();
    this.isLoadingResults = true;
    this.resultsLength = 0;
  }

  ngAfterViewInit(): void {
    this.findAll();
  }

  async findAll() {
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
        this._notificationService.show(
          MessageUtils.ANIMAL.LIST_GET_FAIL + MessageUtils.getMessage(error),
          NotificationType.FAIL
        );
      },
    });
  }

  pageChange() {
    if (this.filterString) {
      this.search();
      return;
    }

    this.findAll();
  }

  async search() {
    const page = this.paginator.pageIndex;
    const size = this.paginator.pageSize;
    const sort = this.sort.active;
    const direction = this.sort.direction;

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
          this.dataSource.data = animais.content;
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

  select(animal: Animal) {
    this._data.multiplus ? this.animais.push(animal) : (this.animal = animal);
  }

  selected(animal: Animal) {
    return this._data.multiplus
      ? this.animais?.some((a) => a.id === animal.id)
      : this.animal?.id === animal.id;
  }

  sortChange() {
    this.paginator.pageIndex = 0;

    if (this.filterString) {
      this.search();
      return;
    }

    this.findAll();
  }

  submit() {
    this._dialogRef.close({
      status: true,
      animal: this.animal,
      animais: this.animais,
    });
  }
}
