import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {
  MAT_DIALOG_DATA,
  MatDialog,
  MatDialogRef,
} from '@angular/material/dialog';
import { Adocao } from 'src/app/entities/adocao';
import { FeiraAdocao } from 'src/app/entities/feira-adocao';
import { Arquivo } from 'src/app/entities/imagem';
import { LocalAdocao } from 'src/app/enums/local-adocao';
import { NotificationType } from 'src/app/enums/notification-type';
import { AdocaoService } from 'src/app/services/adocao.service';
import { NotificationService } from 'src/app/services/notification.service';
import { DateUtils } from 'src/app/utils/date-utils';
import { FormUtils } from 'src/app/utils/form-utils';
import { MessageUtils } from 'src/app/utils/message-utils';
import { environment } from 'src/environments/environment';

import { SelecionarArquivoComponent } from '../selecionar-arquivo/selecionar-arquivo.component';
import { SelecionarFeiraAdocaoComponent } from '../selecionar-feira-adocao/selecionar-feira-adocao.component';
import { SelecionarTutorComponent } from '../selecionar-tutor/selecionar-tutor.component';
import { FileUtils } from 'src/app/utils/file-utils';

@Component({
  selector: 'app-animal-adocoes-cadastro',
  templateUrl: './animal-adocoes-cadastro.component.html',
  styleUrls: ['./animal-adocoes-cadastro.component.sass'],
})
export class AnimalAdocoesCadastroComponent implements OnInit {
  apiURL!: string;
  form!: FormGroup;
  feirasAdocao!: Array<FeiraAdocao>;
  termoResponsabilidade!: Array<any>;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _adocaoService: AdocaoService,
    private _dialog: MatDialog,
    private _dialogRef: MatDialogRef<AnimalAdocoesCadastroComponent>,
    private _formBuilder: FormBuilder,
    private _notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.apiURL = environment.apiURL;
    this.termoResponsabilidade = [];

    if (this.data.adocao) {
      this.buildForm(this.data.adocao);

      if (this.data.adocao.termoResponsabilidade) {
        this.data.adocao.termoResponsabilidade.forEach((t: any) => {
          this.termoResponsabilidade.push({ id: t.id, nome: t.nome });
        });
      }
    } else {
      this.buildForm(null);
    }
  }

  addArquivos() {
    this._dialog
      .open(SelecionarArquivoComponent, {
        data: {
          multiple: true,
        },
        disableClose: true,
        width: '100%',
        minHeight: 'auto',
        maxHeight: '100vh',
      })
      .afterClosed()
      .subscribe({
        next: (result) => {
          if (result && result.status) {
            result.arquivos.forEach((arquivo: any) => {
              const file = { id: new Date().getTime(), file: arquivo };
              this.termoResponsabilidade.push(file);
            });
          }
        },
      });
  }

  buildForm(adocao: Adocao | null) {
    this.form = this._formBuilder.group({
      id: [adocao?.id, Validators.nullValidator],
      dataHora: [adocao?.dataHora, Validators.required],
      local: [adocao?.local, [Validators.required, Validators.maxLength(10)]],
      localAdocao: [
        adocao?.localAdocao,
        [Validators.required, Validators.maxLength(10)],
      ],
      feiraAdocao: [
        adocao?.feiraAdocao,
        adocao?.localAdocao === LocalAdocao.FEIRA
          ? Validators.required
          : Validators.nullValidator,
      ],
      valeCastracao: [adocao?.valeCastracao, Validators.required],
      animal: [this.data.animal, Validators.required],
      tutor: [adocao?.tutor, Validators.required],
      termoResponsabilidade: [
        adocao?.termoResponsabilidade,
        Validators.nullValidator,
      ],
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

  downloadArquivo(arquivo: Arquivo) {
    const link = document.createElement('a');

    link.href = this.apiURL + '/arquivos/search?value=' + arquivo.nome;
    link.download = arquivo.nome;
    link.click();
    link.remove();
  }

  getErrorMessage(controlName: string) {
    return FormUtils.getErrorMessage(this.form, controlName);
  }

  hasError(controlName: string) {
    return FormUtils.hasError(this.form, controlName);
  }

  isArquivo(arquivo: any): string {
    if (
      ('file' in arquivo && FileUtils.isImage(arquivo.file)) ||
      ('nome' in arquivo && FileUtils.isImage(arquivo.nome))
    ) {
      return 'image';
    }

    if (
      ('file' in arquivo && FileUtils.isPDF(arquivo.file)) ||
      ('nome' in arquivo && FileUtils.isPDF(arquivo.nome))
    ) {
      return 'pdf';
    }

    return '';
  }

  localAdocaoChange(event: any) {
    if (event.value === LocalAdocao.FEIRA) {
      this.form.get('feiraAdocao')?.removeValidators(Validators.nullValidator);
      this.form.get('feiraAdocao')?.addValidators(Validators.required);
    } else {
      this.form.get('feiraAdocao')?.removeValidators(Validators.required);
      this.form.get('feiraAdocao')?.addValidators(Validators.nullValidator);
    }

    this.form.get('feiraAdocao')?.patchValue(undefined);
  }

  removeArquivo(arquivo: Arquivo) {
    if (this.form.get('termoResponsabilidade')?.value) {
      this.form
        .get('termoResponsabilidade')
        ?.patchValue(
          this.form
            .get('termoResponsabilidade')
            ?.value.filter((t: any) => t.id !== arquivo.id)
        );
    }

    this.termoResponsabilidade = this.termoResponsabilidade.filter(
      (t: any) => t.id !== arquivo.id
    );
  }

  selectFeiraAdocao() {
    this._dialog
      .open(SelecionarFeiraAdocaoComponent, {
        data: {
          multiplus: false,
        },
        disableClose: true,
        width: '100%',
        minHeight: 'auto',
        maxHeight: '100vh',
      })
      .afterClosed()
      .subscribe({
        next: (result) => {
          if (result && result.status) {
            this.form.get('feiraAdocao')?.patchValue(result.feiraAdocao);
          }
        },
      });
  }

  selectTutor() {
    this._dialog
      .open(SelecionarTutorComponent, {
        data: {
          multiplus: false,
        },
        disableClose: true,
        width: '100%',
        minHeight: 'auto',
        maxHeight: '100vh',
      })
      .afterClosed()
      .subscribe({
        next: (result) => {
          if (result && result.status) {
            this.form.get('tutor')?.patchValue(result.tutor);
          }
        },
      });
  }

  submit() {
    if (this.form.valid) {
      const adocao: Adocao = Object.assign({}, this.form.getRawValue());
      const images = this.termoResponsabilidade
        .filter((t: any) => t.file)
        .map((t: any) => t.file);
      adocao.dataHora = DateUtils.addHours(
        adocao.dataHora,
        DateUtils.offsetBrasilia
      );

      if (adocao.id) {
        this._adocaoService.update(adocao, images).subscribe({
          next: (adocao) => {
            this._notificationService.show(
              MessageUtils.ADOCAO.UPDATE_SUCCESS,
              NotificationType.SUCCESS
            );
            this._dialogRef.close({ status: true, adocao: adocao });
          },

          error: (error) => {
            console.error(error);
            this._notificationService.show(
              MessageUtils.ADOCAO.UPDATE_FAIL + MessageUtils.getMessage(error),
              NotificationType.FAIL
            );
          },
        });
      } else {
        this._adocaoService.save(adocao, images).subscribe({
          next: (adocao) => {
            this._notificationService.show(
              MessageUtils.ADOCAO.SAVE_SUCCESS,
              NotificationType.SUCCESS
            );
            this._dialogRef.close({ status: true, adocao: adocao });
          },

          error: (error) => {
            console.error(error);
            this._notificationService.show(
              MessageUtils.ADOCAO.SAVE_FAIL + MessageUtils.getMessage(error),
              NotificationType.FAIL
            );
          },
        });
      }
    }
  }
}
