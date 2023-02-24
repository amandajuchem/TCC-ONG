import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Atendimento } from 'src/app/entities/atendimento';
import { Exame } from 'src/app/entities/exame';
import { NotificationType } from 'src/app/enums/notification-type';
import { Setor } from 'src/app/enums/setor';
import { FacadeService } from 'src/app/services/facade.service';
import { DateUtils } from 'src/app/utils/date-utils';
import { MessageUtils } from 'src/app/utils/message-utils';
import { environment } from 'src/environments/environment';

import { SelecionarAnimalComponent } from '../selecionar-animal/selecionar-animal.component';
import { SelecionarExameComponent } from '../selecionar-exame/selecionar-exame.component';
import { SelecionarImagemComponent } from '../selecionar-imagem/selecionar-imagem.component';
import { SelecionarUsuarioComponent } from '../selecionar-usuario/selecionar-usuario.component';

@Component({
  selector: 'app-atendimento-cadastro',
  templateUrl: './atendimento-cadastro.component.html',
  styleUrls: ['./atendimento-cadastro.component.sass']
})
export class AtendimentoCadastroComponent implements OnInit {

  apiURL!: string;
  form!: FormGroup;
  documentosToDelete!: Array<string>;
  documentosToSave!: Array<any>;
  documentosToShow!: Array<any>;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _dialog: MatDialog,
    private _facade: FacadeService,
    private _formBuilder: FormBuilder,
    private _matDialogRef: MatDialogRef<AtendimentoCadastroComponent>
  ) { }

  ngOnInit(): void {

    this.apiURL = environment.apiURL;
    this.documentosToDelete = [];
    this.documentosToSave = [];
    this.documentosToShow = [];

    if (this.data.atendimento) {
      this.buildForm(this.data.atendimento);

      if (this.data.atendimento.documentos) {

        this.data.atendimento.documentos.forEach((d: any) => {
          this.documentosToShow.push({ id: d.id, data: d.nome, salvo: true });
        });
      }
    } else {
      this.buildForm(null);
    }
  }

  addImagem() {

    this._dialog.open(SelecionarImagemComponent, {
      data: {
        multiple: true
      },
      width: '100%'
    })
      .afterClosed().subscribe({

        next: (result) => {

          if (result && result.status) {

            result.images.forEach((image: any) => {

              this._facade.imagemToBase64(image)?.then((data: any) => {
                data = { id: new Date().getTime(), data: data, salvo: false };
                this.documentosToShow.push(data);
                this.documentosToSave.push(image);
              })
            });
          }
        }
      });
  }

  addExame() {

    this._dialog.open(SelecionarExameComponent, {
      width: '100%'
    })
    .afterClosed().subscribe({

      next: (result) => {

        if (result && result.status) {

          let exames: Array<Exame> = this.form.get('exames')?.value;
          
          if (!exames){
            exames = [];
          }
          
          exames.push(result.exame);
          this.form.get('exames')?.patchValue(exames);
        }
      }
    });
  }

  buildForm(atendimento: Atendimento | null) {

    this.form = this._formBuilder.group({
      id: [atendimento?.id, Validators.nullValidator],
      animal: [atendimento?.animal, Validators.required],
      veterinario: [atendimento?.veterinario, Validators.required],
      comorbidades: [atendimento?.animal.fichaMedica.comorbidades, Validators.nullValidator],
      dataHora: [atendimento?.dataHora ? this.getDateWithTimeZone(atendimento.dataHora) : null, Validators.required],
      motivo: [atendimento?.motivo, Validators.required],
      diagnostico: [atendimento?.diagnostico, Validators.required],
      posologia: [atendimento?.posologia, Validators.required],
      exames: [atendimento?.exames, Validators.required],
      documentos: [atendimento?.documentos, Validators.nullValidator]
    });
  }

  dateChange() {

    if (this.form.get('dataHora')?.value) {

      this.form.get('dataHora')?.patchValue(
        DateUtils.getDateTimeWithoutSecondsAndMilliseconds(this.form.get('dataHora')?.value));
    }
  }

  deleteImagem(imagem: any) {

    if (imagem.salvo) {
      this.documentosToDelete.push(imagem.id);
    }

    this.documentosToSave = this.documentosToSave.filter((d: any) => d.id !== imagem.id);
    this.documentosToShow = this.documentosToShow.filter((d: any) => d.id !== imagem.id);
  }

  downloadImagem(imagem: any) {

    const link = document.createElement('a');

    link.href = this.apiURL + '/imagens/search?nome=' + imagem.data;
    link.download = imagem.data;
    link.click();
    link.remove();
  }

  getDateWithTimeZone(date: any) {
    return DateUtils.getDateWithTimeZone(date);
  }

  removeExame(exame: Exame) {
    let exames: Array<Exame> = this.form.get('exames')?.value;
    exames = exames.filter(e => e.id != exame.id);
    this.form.get('exames')?.patchValue(exames);
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