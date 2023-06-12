import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { Router } from '@angular/router';
import { Authentication } from 'src/app/entities/authentication';
import { Tutor } from 'src/app/entities/tutor';
import { NotificationType } from 'src/app/enums/notification-type';
import { AuthService } from 'src/app/services/auth.service';
import { NotificationService } from 'src/app/services/notification.service';
import { TutorService } from 'src/app/services/tutor.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { OperatorUtils } from 'src/app/utils/operator-utils';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-tutores',
  templateUrl: './tutores.component.html',
  styleUrls: ['./tutores.component.sass']
})
export class TutoresComponent implements AfterViewInit {

  apiURL!: string;
  authentication!: Authentication;
  filterString!: string;
  isLoadingResults!: boolean;
  pageIndex!: number;
  pageSize!: number;
  resultsLength!: number;
  tutores!: Array<Tutor>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private _authService: AuthService,
    private _notificationService: NotificationService,
    private _router: Router,
    private _tutorService: TutorService
  ) {
    this.apiURL = environment.apiURL;
    this.authentication = this._authService.getAuthentication();
    this.isLoadingResults = true;
    this.resultsLength = 0;
  }

  ngAfterViewInit(): void {
    this.findAll();
  }

  add() {
    this._router.navigate(['/' + this.authentication.role.toLowerCase() + '/tutores/cadastro']);
  }

  async findAll() {

    const page = this.paginator.pageIndex;
    const size = this.paginator.pageSize;
    const sort = 'nome';
    const direction = 'asc';

    this.isLoadingResults = true;
    await OperatorUtils.delay(1000);

    this._tutorService.findAll(page, size, sort, direction).subscribe({

      complete: () => {
        this.isLoadingResults = false;
      },

      next: (tutores) => {
        this.tutores = tutores.content;
        this.resultsLength = tutores.totalElements;
      },

      error: (error) => {
        this.isLoadingResults = false;
        console.error(error);
        this._notificationService.show(MessageUtils.TUTORES_GET_FAIL, NotificationType.FAIL);
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

    this._tutorService.search(this.filterString, page, size, sort, direction).subscribe({

      complete: () => {
        this.isLoadingResults = false;
      },

      next: (tutores) => {
        this.tutores = tutores.content;
        this.resultsLength = tutores.totalElements;
      },

      error: (err) => {
        this.isLoadingResults = false;
        console.error(err);
        this._notificationService.show(MessageUtils.TUTORES_GET_FAIL, NotificationType.FAIL);
      }
    });
  }
}