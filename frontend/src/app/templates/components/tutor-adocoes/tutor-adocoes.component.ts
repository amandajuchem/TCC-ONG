import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Adocao } from 'src/app/entities/adocao';
import { Authentication } from 'src/app/entities/authentication';
import { Tutor } from 'src/app/entities/tutor';
import { NotificationType } from 'src/app/enums/notification-type';
import { AdocaoService } from 'src/app/services/adocao.service';
import { AuthService } from 'src/app/services/auth.service';
import { NotificationService } from 'src/app/services/notification.service';
import { TutorService } from 'src/app/services/tutor.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { OperatorUtils } from 'src/app/utils/operator-utils';

@Component({
  selector: 'app-tutor-adocoes',
  templateUrl: './tutor-adocoes.component.html',
  styleUrls: ['./tutor-adocoes.component.sass']
})
export class TutorAdocoesComponent implements AfterViewInit {

  authentication!: Authentication;
  columns!: Array<string>;
  dataSource!: MatTableDataSource<Adocao>;
  isLoadingResults!: boolean;
  resultsLength!: number;
  tutor!: Tutor;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private _adocaoService: AdocaoService,
    private _authService: AuthService,
    private _notificationService: NotificationService,
    private _router: Router,
    private _tutorService: TutorService
  ) {
    this.authentication = this._authService.getAuthentication();
    this.columns = ['index', 'dataHora', 'localAdocao', 'animal', 'acao'];
    this.dataSource = new MatTableDataSource();
    this.isLoadingResults = true;
    this.resultsLength = 0;
  }

  ngAfterViewInit(): void {
    
    this._tutorService.get().subscribe({

      next: (tutor) => {
        
        if (tutor) {
          this.tutor = tutor;
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

    this._adocaoService.search(this.tutor.id, page, size, sort, direction).subscribe({

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
        this._notificationService.show(MessageUtils.ADOCAO.LIST_GET_FAIL + MessageUtils.getMessage(error), NotificationType.FAIL);
      }
    });
  }

  pageChange() {
    this.findAll();
  }

  show(adocao: Adocao) {
    this._router.navigate(['/' + this.authentication.role.toLowerCase() + '/animais/' + adocao.animal.id]);
  }

  sortChange() {
    this.paginator.pageIndex = 0;
    this.findAll();
  }
}