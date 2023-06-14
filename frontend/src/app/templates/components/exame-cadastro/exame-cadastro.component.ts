import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Authentication } from 'src/app/entities/authentication';
import { Exame } from 'src/app/entities/exame';
import { NotificationType } from 'src/app/enums/notification-type';
import { AuthService } from 'src/app/services/auth.service';
import { ExameService } from 'src/app/services/exame.service';
import { NotificationService } from 'src/app/services/notification.service';
import { MessageUtils } from 'src/app/utils/message-utils';

import { ExameExcluirComponent } from '../exame-excluir/exame-excluir.component';

@Component({
  selector: 'app-exame-cadastro',
  templateUrl: './exame-cadastro.component.html',
  styleUrls: ['./exame-cadastro.component.sass']
})
export class ExameCadastroComponent implements OnInit {

  exame!: Exame;
  form!: FormGroup;
  authentication!: Authentication;

  constructor(
    private _activatedRoute: ActivatedRoute,
    private _authService: AuthService,
    private _dialog: MatDialog,
    private _exameService: ExameService,
    private _formBuilder: FormBuilder,
    private _notificationService: NotificationService,
    private _router: Router
  ) { }

  ngOnInit(): void {
    
    this.authentication = this._authService.getAuthentication();
    
    this._activatedRoute.params.subscribe({

      next: (params: any) => {
        
        if (params && params.id) {

          if (params.id.includes('cadastro')) {
            this.buildForm(null);
          }

          else {

            this._exameService.findById(params.id).subscribe({

              next: (exame) => {
                this.exame = exame;
                this.buildForm(exame);
              },

              error: (error) => {
                console.error(error);
                this._notificationService.show(MessageUtils.EXAME.GET_FAIL + MessageUtils.getMessage(error), NotificationType.FAIL);
              }
            });
          }
        }
      }
    });
  }
  
  buildForm(exame: Exame | null) {

    this.form = this._formBuilder.group({
      id: [exame?.id, Validators.nullValidator],
      nome: [exame?.nome, Validators.required],
      categoria: [exame?.categoria, Validators.required]
    });

    exame ? this.form.disable() : this.form.enable();
  }

  cancel() {
    this.exame ? this.buildForm(this.exame) : this._router.navigate(['/' + this.authentication.role.toLowerCase() + '/exames']);
  }

  delete() {

    this._dialog.open(ExameExcluirComponent, {
      data: {
        exame: this.exame
      },
      width: '100%'
    })
    .afterClosed().subscribe({

      next: (result) => {

        if (result && result.status) {
          this._router.navigate(['/' + this.authentication.role.toLowerCase() + '/exames']);
        }
      }
    });
  }

  submit() {

    const exame: Exame = Object.assign({}, this.form.getRawValue());

    if (exame.id) {

      this._exameService.update(exame).subscribe({
        
        complete: () => {
          this._notificationService.show(MessageUtils.EXAME.UPDATE_SUCCESS, NotificationType.SUCCESS);
          this.ngOnInit();
        },
  
        error: (error) => {
          console.error(error);
          this._notificationService.show(MessageUtils.EXAME.UPDATE_FAIL + MessageUtils.getMessage(error), NotificationType.FAIL);
        }
      });
    } 
    
    else {

      this._exameService.save(exame).subscribe({
        
        next: (exame) => {
          this._notificationService.show(MessageUtils.EXAME.SAVE_SUCCESS, NotificationType.SUCCESS);
          this._router.navigate(['/' + this.authentication.role.toLowerCase() + '/exames/' + exame.id]);
        },
  
        error: (error) => {
          console.error(error);
          this._notificationService.show(MessageUtils.EXAME.SAVE_FAIL + MessageUtils.getMessage(error), NotificationType.FAIL);
        }
      });
    }
  }
}