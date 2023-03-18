import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Agendamento } from 'src/app/entities/agendamento';
import { NotificationType } from 'src/app/enums/notification-type';
import { Setor } from 'src/app/enums/setor';
import { AgendamentoService } from 'src/app/services/agendamento.service';
import { NotificationService } from 'src/app/services/notification.service';
import { DateUtils } from 'src/app/utils/date-utils';
import { MessageUtils } from 'src/app/utils/message-utils';

import { SelecionarAnimalComponent } from '../selecionar-animal/selecionar-animal.component';
import { SelecionarUsuarioComponent } from '../selecionar-usuario/selecionar-usuario.component';

@Component({
  selector: 'app-agendamento-cadastro',
  templateUrl: './agendamento-cadastro.component.html',
  styleUrls: ['./agendamento-cadastro.component.sass']
})
export class AgendamentoCadastroComponent implements OnInit {

  form!: FormGroup;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _agendamentoService: AgendamentoService,
    private _dialog: MatDialog,
    private _dialogRef: MatDialogRef<AgendamentoCadastroComponent>,
    private _formBuilder: FormBuilder,
    private _notificationService: NotificationService
  ) { }

  ngOnInit(): void {

    if (this.data.agendamento) {
      this.buildForm(this.data.agendamento);
    } else {
      this.buildForm(null);
    }
  }

  buildForm(agendamento: Agendamento | null) {

    this.form = this._formBuilder.group({
      id: [agendamento?.id, Validators.nullValidator],
      dataHora: [agendamento?.dataHora ? this.getDateWithTimeZone(agendamento.dataHora) : null, Validators.required],
      animal: [agendamento?.animal, Validators.required],
      veterinario: [agendamento?.veterinario, Validators.required],
      descricao: [agendamento?.descricao, Validators.required]
    });
  }

  dateChange() {

    if (this.form.get('dataHora')?.value) {
      
      this.form.get('dataHora')?.patchValue(
        DateUtils.getDateTimeWithoutSecondsAndMilliseconds(this.form.get('dataHora')?.value));
    }
  }

  getDateWithTimeZone(date: any) {
    return DateUtils.getDateWithTimeZone(date);
  }

  selectAnimal() {

    this._dialog.open(SelecionarAnimalComponent, {
      width: '100%'
    })
    .afterClosed().subscribe({

      next: (result) => {

        if (result && result.status) {
          this.form.get('animal')?.patchValue(result.animal);
        }
      },
    });
  }

  selectVeterinario() {

    this._dialog.open(SelecionarUsuarioComponent, {
      data: {
        setor: Setor.VETERINARIO
      },
      width: '100%'
    })
    .afterClosed().subscribe({

      next: (result) => {

        if (result && result.status) {
          this.form.get('veterinario')?.patchValue(result.usuario);
        }
      }
    });
  }

  submit() {

    const agendamento: Agendamento = Object.assign({}, this.form.getRawValue());

    if (agendamento.id) {

      this._agendamentoService.update(agendamento).subscribe({

        complete: () => {
          this._notificationService.show(MessageUtils.AGENDAMENTO_UPDATE_SUCCESS, NotificationType.SUCCESS);
          this._dialogRef.close({ status: true });
        },

        error: (error) => {
          console.error(error);
          this._notificationService.show(MessageUtils.AGENDAMENTO_UPDATE_FAIL + error.error[0].message, NotificationType.FAIL);
        }
      });
    }

    else {

      this._agendamentoService.save(agendamento).subscribe({

        complete: () => {
          this._notificationService.show(MessageUtils.AGENDAMENTO_SAVE_SUCCESS, NotificationType.SUCCESS);
          this._dialogRef.close({ status: true });
        },

        error: (error) => {
          console.error(error);
          this._notificationService.show(MessageUtils.AGENDAMENTO_SAVE_FAIL + error.error[0].message, NotificationType.FAIL);
        }
      });
    }
  }
}