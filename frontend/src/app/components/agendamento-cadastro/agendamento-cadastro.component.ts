import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { Agendamento } from 'src/app/entities/agendamento';
import { NotificationType } from 'src/app/enums/notification-type';
import { Setor } from 'src/app/enums/setor';
import { AgendamentoService } from 'src/app/services/agendamento.service';
import { NotificationService } from 'src/app/services/notification.service';
import { RedirectService } from 'src/app/services/redirect.service';
import { DateUtils } from 'src/app/utils/date-utils';
import { FormUtils } from 'src/app/utils/form-utils';
import { MessageUtils } from 'src/app/utils/message-utils';

import { SelecionarAnimalComponent } from '../selecionar-animal/selecionar-animal.component';
import { SelecionarUsuarioComponent } from '../selecionar-usuario/selecionar-usuario.component';

@Component({
  selector: 'app-agendamento-cadastro',
  templateUrl: './agendamento-cadastro.component.html',
  styleUrls: ['./agendamento-cadastro.component.sass'],
})
export class AgendamentoCadastroComponent implements OnInit {
  agendamento!: Agendamento;
  form!: FormGroup;

  constructor(
    private _activatedRoute: ActivatedRoute,
    private _agendamentoService: AgendamentoService,
    private _dialog: MatDialog,
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
            this._agendamentoService.findById(params.id).subscribe({
              next: (agendamento) => {
                this.agendamento = agendamento;
                this.buildForm(agendamento);
              },

              error: (error) => {
                console.error(error);
                this._notificationService.show(
                  MessageUtils.AGENDAMENTO.GET_FAIL +
                    MessageUtils.getMessage(error),
                  NotificationType.FAIL
                );
              },
            });
          }
        }
      },
    });
  }

  buildForm(agendamento: Agendamento | null) {
    this.form = this._formBuilder.group({
      id: [agendamento?.id, Validators.nullValidator],
      dataHora: [agendamento?.dataHora, Validators.required],
      animal: [agendamento?.animal, Validators.required],
      veterinario: [agendamento?.veterinario, Validators.required],
      descricao: [agendamento?.descricao, Validators.required],
    });
  }

  dateChange() {
    if (this.form.get('dataHora')?.value) {
      this.form
        .get('dataHora')
        ?.patchValue(
          DateUtils.getDateTimeWithoutSecondsAndMilliseconds(
            this.form.get('dataHora')?.value
          )
        );
    }
  }

  getErrorMessage(controlName: string) {
    return FormUtils.getErrorMessage(this.form, controlName);
  }

  hasError(controlName: string) {
    return FormUtils.hasError(this.form, controlName);
  }

  selectAnimal() {
    this._dialog
      .open(SelecionarAnimalComponent, {
        data: {
          multiplus: false,
        },
        disableClose: true,
        width: '100%',
        minHeight: 'auto',
        maxHeight: '100vh'
      })
      .afterClosed()
      .subscribe({
        next: (result) => {
          if (result && result.status) {
            this.form.get('animal')?.patchValue(result.animal);
          }
        },
      });
  }

  selectVeterinario() {
    this._dialog
      .open(SelecionarUsuarioComponent, {
        data: {
          setor: Setor.VETERINARIO,
        },
        disableClose: true,
        width: '100%',
        minHeight: 'auto',
        maxHeight: '100vh'
      })
      .afterClosed()
      .subscribe({
        next: (result) => {
          if (result && result.status) {
            this.form.get('veterinario')?.patchValue(result.usuario);
          }
        },
      });
  }

  redirectToAgendamentoList() {
    this._redirectService.toAgendamentoList();
  }

  submit() {
    if (this.form.valid) {
      const agendamento: Agendamento = Object.assign(
        {},
        this.form.getRawValue()
      );
      agendamento.dataHora = DateUtils.addHours(
        agendamento.dataHora,
        DateUtils.offsetBrasilia
      );

      if (agendamento.id) {
        this._agendamentoService.update(agendamento).subscribe({
          complete: () => {
            this._notificationService.show(
              MessageUtils.AGENDAMENTO.UPDATE_SUCCESS,
              NotificationType.SUCCESS
            );
            this.ngOnInit();
          },

          error: (error) => {
            console.error(error);
            this._notificationService.show(
              MessageUtils.AGENDAMENTO.UPDATE_FAIL +
                MessageUtils.getMessage(error),
              NotificationType.FAIL
            );
          },
        });
      } else {
        this._agendamentoService.save(agendamento).subscribe({
          next: (agendamento) => {
            this._notificationService.show(
              MessageUtils.AGENDAMENTO.SAVE_SUCCESS,
              NotificationType.SUCCESS
            );
            this._redirectService.toAgendamento(agendamento.id);
          },

          error: (error) => {
            console.error(error);
            this._notificationService.show(
              MessageUtils.AGENDAMENTO.SAVE_FAIL +
                MessageUtils.getMessage(error),
              NotificationType.FAIL
            );
          },
        });
      }
    }
  }
}
