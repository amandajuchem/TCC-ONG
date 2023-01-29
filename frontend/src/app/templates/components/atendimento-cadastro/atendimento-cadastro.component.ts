import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Atendimento } from 'src/app/entities/atendimento';
import { NotificationType } from 'src/app/enums/notification-type';
import { Setor } from 'src/app/enums/setor';
import { FacadeService } from 'src/app/services/facade.service';
import { DateUtils } from 'src/app/utils/date-utils';
import { MessageUtils } from 'src/app/utils/message-utils';

import { SelecionarAnimalComponent } from '../selecionar-animal/selecionar-animal.component';
import { SelecionarUsuarioComponent } from '../selecionar-usuario/selecionar-usuario.component';

@Component({
  selector: 'app-atendimento-cadastro',
  templateUrl: './atendimento-cadastro.component.html',
  styleUrls: ['./atendimento-cadastro.component.sass']
})
export class AtendimentoCadastroComponent implements OnInit {

  form!: FormGroup;
  documentosToDelete!: Array<string>;
  documentosToSave!: Array<File>;
  documentosToShow!: Array<any>;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _dialog: MatDialog,
    private _facade: FacadeService,
    private _formBuilder: FormBuilder,
    private _matDialogRef: MatDialogRef<AtendimentoCadastroComponent>
  ) { }

  ngOnInit(): void {

    if (this.data.atendimento) {
      this.buildForm(this.data.atendimento);
    } else {
      this.buildForm(null);
    }
  }

  buildForm(atendimento: Atendimento | null) {

    this.form = this._formBuilder.group({
      id: [atendimento?.id, Validators.nullValidator],
      dataHora: [atendimento?.dataHora ? this.getDateWithTimeZone(atendimento.dataHora) : null, Validators.required],
      dataHoraRetorno: [atendimento?.dataHoraRetorno, Validators.nullValidator],
      motivo: [atendimento?.motivo, Validators.required],
      comorbidades: [atendimento?.animal.fichaMedica.comorbidades, Validators.nullValidator],
      diagnostico: [atendimento?.diagnostico, Validators.required],
      exames: [atendimento?.exames, Validators.required],
      procedimentos: [atendimento?.procedimentos, Validators.required],
      posologia: [atendimento?.posologia, Validators.required],
      documentos: [atendimento?.documentos, Validators.nullValidator],
      animal: [atendimento?.animal, Validators.required],
      veterinario: [atendimento?.veterinario, Validators.required]
    });
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
            this.form.get('comorbidades')?.patchValue(result.animal.fichaMedica.comorbidades);
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

    let atendimento: Atendimento = Object.assign({}, this.form.getRawValue());

    if (atendimento.id) {

      this._facade.atendimentoUpdate(atendimento, this.documentosToSave, this.documentosToDelete).subscribe({

        complete: () => {
          this._facade.notificationShowNotification(MessageUtils.ATENDIMENTO_UPDATE_SUCCESS, NotificationType.SUCCESS);
          this._matDialogRef.close({ status: true });
        },

        error: (error) => {
          console.error(error);
          this._facade.notificationShowNotification(MessageUtils.ATENDIMENTO_UPDATE_FAIL + error.error[0].message, NotificationType.FAIL);   
        }
      });
    }

    else {

      this._facade.atendimentoSave(atendimento, this.documentosToSave).subscribe({

        complete: () => {
          this._facade.notificationShowNotification(MessageUtils.ATENDIMENTO_SAVE_SUCCESS, NotificationType.SUCCESS);
          this._matDialogRef.close({ status: true });
        },

        error: (error) => {
          console.error(error);
          this._facade.notificationShowNotification(MessageUtils.ATENDIMENTO_SAVE_FAIL + error.error[0].message, NotificationType.FAIL);   
        }
      });
    }
  }
}