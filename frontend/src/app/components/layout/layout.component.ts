import { Component, OnInit } from '@angular/core';
import { NotificationType } from 'src/app/enums/notification-type';
import { EmpresaService } from 'src/app/services/empresa.service';
import { NotificationService } from 'src/app/services/notification.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.sass'],
})
export class LayoutComponent implements OnInit {
  
  constructor (
    private _empresaService: EmpresaService,
    private _notificationService: NotificationService
  ) { }

  ngOnInit(): void {
    this.loadEmpresa();
  }

  loadEmpresa() {

    this._empresaService.findAll(0, 10, "nome", "asc").subscribe({

      next: (empresas) => {
        const empresa = empresas.content[0];

        this._empresaService.findById(empresa.id).subscribe({

          next: (empresa) => {
            this._empresaService.set(empresa);    
          },

          error: (error) => {
            console.error(error);
            this._notificationService.show(
              MessageUtils.EMPRESA.GET_FAIL + MessageUtils.getMessage(error),
              NotificationType.FAIL
            );
          },
        });
      },

      error: (error) => {
        console.error(error);
        this._notificationService.show(
          MessageUtils.EMPRESA.GET_FAIL + MessageUtils.getMessage(error),
          NotificationType.FAIL
        );
      },
    });
  }
}
