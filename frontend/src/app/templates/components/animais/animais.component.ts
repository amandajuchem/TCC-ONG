import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { Router } from '@angular/router';
import { Animal } from 'src/app/entities/animal';
import { Authentication } from 'src/app/entities/authentication';
import { NotificationType } from 'src/app/enums/notification-type';
import { AnimalService } from 'src/app/services/animal.service';
import { AuthService } from 'src/app/services/auth.service';
import { NotificationService } from 'src/app/services/notification.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { OperatorUtils } from 'src/app/utils/operator-utils';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-animais',
  templateUrl: './animais.component.html',
  styleUrls: ['./animais.component.sass']
})
export class AnimaisComponent implements AfterViewInit {

  animais!: Array<Animal>;
  apiURL!: string;
  authentication!: Authentication;
  filterString!: string;
  isLoadingResults!: boolean;
  pageIndex!: number;
  pageSize!: number;
  resultsLength!: number;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private _animalService: AnimalService,
    private _authService: AuthService,
    private _notificationService: NotificationService,
    private _router: Router
  ) {
    this.apiURL = environment.apiURL;
    this.isLoadingResults = true;
    this.resultsLength = 0;
    this.authentication = this._authService.getAuthentication();
  }

  ngAfterViewInit(): void {
    this.findAll();
  }

  add() {
    this._router.navigate(['/' + this.authentication.role.toLowerCase() + '/animais/cadastro']);
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
        this._notificationService.show(MessageUtils.ANIMAL.LIST_GET_FAIL + MessageUtils.getMessage(error), NotificationType.FAIL);
      }
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

    this._animalService.search(this.filterString, page, size, sort, direction).subscribe({

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
        this._notificationService.show(MessageUtils.ANIMAL.LIST_GET_FAIL + MessageUtils.getMessage(error), NotificationType.FAIL);
      }
    });
  }
}