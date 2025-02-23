import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Empresa } from 'src/app/entities/empresa';
import { NotificationType } from 'src/app/enums/notification-type';
import { EmpresaService } from 'src/app/services/empresa.service';
import { NotificationService } from 'src/app/services/notification.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-empresa',
  templateUrl: './empresa.component.html',
  styleUrls: ['./empresa.component.sass'],
})
export class EmpresaComponent implements OnInit {
  empresa!: Empresa | null;
  isLoading!: boolean;

  constructor(
    private _activatedRoute: ActivatedRoute,
    private _empresaService: EmpresaService,
    private _notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.isLoading = true;

    this._activatedRoute.params.subscribe({
      next: (params: any) => {
        if (params && params.id) {
          this._empresaService.findById(params.id).subscribe({
            next: (empresa) => {
              this.isLoading = false;
              this._empresaService.set(empresa);
            },

            error: (error) => {
              console.error(error);
              this.isLoading = false;
              this._notificationService.show(
                MessageUtils.EMPRESA.GET_FAIL + MessageUtils.getMessage(error),
                NotificationType.FAIL
              );
            },
          });
        }
      },
    });

    this._empresaService.get().subscribe({
      next: (empresa) => {
        this.empresa = empresa;
      },
    });
  }
}