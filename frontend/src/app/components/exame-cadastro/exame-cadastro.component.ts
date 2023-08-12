import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Exame } from 'src/app/entities/exame';
import { NotificationType } from 'src/app/enums/notification-type';
import { ExameService } from 'src/app/services/exame.service';
import { NotificationService } from 'src/app/services/notification.service';
import { RedirectService } from 'src/app/services/redirect.service';
import { FormUtils } from 'src/app/utils/form-utils';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-exame-cadastro',
  templateUrl: './exame-cadastro.component.html',
  styleUrls: ['./exame-cadastro.component.sass'],
})
export class ExameCadastroComponent implements OnInit {
  exame!: Exame;
  form!: FormGroup;

  constructor(
    private _activatedRoute: ActivatedRoute,
    private _exameService: ExameService,
    private _formBuilder: FormBuilder,
    private _notificationService: NotificationService,
    private _redirectService: RedirectService
  ) {}

  ngOnInit(): void {
    this._activatedRoute.params.subscribe({
      next: (params: any) => {
        if (params && params.id) {
          if (params.id.includes('cadastro')) {
            this.buildForm(null);
          } else {
            this._exameService.findById(params.id).subscribe({
              next: (exame) => {
                this.exame = exame;
                this.buildForm(exame);
              },

              error: (error) => {
                console.error(error);
                this._notificationService.show(
                  MessageUtils.EXAME.GET_FAIL + MessageUtils.getMessage(error),
                  NotificationType.FAIL
                );
              },
            });
          }
        }
      },
    });
  }

  buildForm(exame: Exame | null) {
    this.form = this._formBuilder.group({
      id: [exame?.id, Validators.nullValidator],
      nome: [exame?.nome, [Validators.required, Validators.maxLength(100)]],
      categoria: [
        exame?.categoria,
        [Validators.required, Validators.maxLength(25)],
      ],
    });
  }

  redirectToExameList() {
    this._redirectService.toExameList();
  }

  getErrorMessage(controlName: string) {
    return FormUtils.getErrorMessage(this.form, controlName);
  }

  hasError(controlName: string) {
    return FormUtils.hasError(this.form, controlName);
  }

  submit() {
    if (this.form.valid) {
      const exame: Exame = Object.assign({}, this.form.getRawValue());

      if (exame.id) {
        this._exameService.update(exame).subscribe({
          complete: () => {
            this._notificationService.show(
              MessageUtils.EXAME.UPDATE_SUCCESS,
              NotificationType.SUCCESS
            );
            this.ngOnInit();
          },

          error: (error) => {
            console.error(error);
            this._notificationService.show(
              MessageUtils.EXAME.UPDATE_FAIL + MessageUtils.getMessage(error),
              NotificationType.FAIL
            );
          },
        });
      } else {
        this._exameService.save(exame).subscribe({
          next: (exame) => {
            this._notificationService.show(
              MessageUtils.EXAME.SAVE_SUCCESS,
              NotificationType.SUCCESS
            );
            this._redirectService.toExame(exame.id);
          },

          error: (error) => {
            console.error(error);
            this._notificationService.show(
              MessageUtils.EXAME.SAVE_FAIL + MessageUtils.getMessage(error),
              NotificationType.FAIL
            );
          },
        });
      }
    }
  }
}
