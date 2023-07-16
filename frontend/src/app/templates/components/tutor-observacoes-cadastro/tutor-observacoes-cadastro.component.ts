import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Observacao } from 'src/app/entities/observacao';
import { NotificationType } from 'src/app/enums/notification-type';
import { NotificationService } from 'src/app/services/notification.service';
import { ObservacaoService } from 'src/app/services/observacao.service';
import { FormUtils } from 'src/app/utils/form-utils';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-tutor-observacoes-cadastro',
  templateUrl: './tutor-observacoes-cadastro.component.html',
  styleUrls: ['./tutor-observacoes-cadastro.component.sass'],
})
export class TutorObservacoesCadastroComponent implements OnInit {
  form!: FormGroup;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _dialogRef: MatDialogRef<TutorObservacoesCadastroComponent>,
    private _formBuilder: FormBuilder,
    private _notificationService: NotificationService,
    private _observacaoService: ObservacaoService
  ) {}

  ngOnInit(): void {
    if (this.data.observacao) {
      this.buildForm(this.data.observacao);
    } else {
      this.buildForm(null);
    }
  }

  buildForm(observacao: Observacao | null) {
    this.form = this._formBuilder.group({
      id: [observacao?.id, Validators.nullValidator],
      conteudo: [observacao?.conteudo, Validators.required],
      tutor: [this.data.tutor, Validators.required],
    });
  }

  getErrorMessage(controlName: string) {
    return FormUtils.getErrorMessage(this.form, controlName);
  }

  hasError(controlName: string) {
    return FormUtils.hasError(this.form, controlName);
  }

  submit() {
    if (this.form.valid) {
      const observacao: Observacao = Object.assign({}, this.form.getRawValue());

      if (observacao.id) {
        this._observacaoService.update(observacao).subscribe({
          complete: () => {
            this._notificationService.show(
              MessageUtils.OBSERVACAO.UPDATE_SUCCESS,
              NotificationType.SUCCESS
            );
            this._dialogRef.close({ status: true });
          },

          error: (error) => {
            console.error(error);
            this._notificationService.show(
              MessageUtils.OBSERVACAO.UPDATE_FAIL +
                MessageUtils.getMessage(error),
              NotificationType.FAIL
            );
          },
        });
      } else {
        this._observacaoService.save(observacao).subscribe({
          complete: () => {
            this._notificationService.show(
              MessageUtils.OBSERVACAO.SAVE_SUCCESS,
              NotificationType.SUCCESS
            );
            this._dialogRef.close({ status: true });
          },

          error: (error) => {
            console.error(error);
            this._notificationService.show(
              MessageUtils.OBSERVACAO.SAVE_FAIL +
                MessageUtils.getMessage(error),
              NotificationType.FAIL
            );
          },
        });
      }
    }
  }
}
