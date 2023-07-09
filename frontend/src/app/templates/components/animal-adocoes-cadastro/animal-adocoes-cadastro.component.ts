import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Adocao } from 'src/app/entities/adocao';
import { NotificationType } from 'src/app/enums/notification-type';
import { AdocaoService } from 'src/app/services/adocao.service';
import { ImagemService } from 'src/app/services/imagem.service';
import { NotificationService } from 'src/app/services/notification.service';
import { DateUtils } from 'src/app/utils/date-utils';
import { FormUtils } from 'src/app/utils/form-utils';
import { MessageUtils } from 'src/app/utils/message-utils';
import { environment } from 'src/environments/environment';

import { SelecionarImagemComponent } from '../selecionar-imagem/selecionar-imagem.component';
import { SelecionarTutorComponent } from '../selecionar-tutor/selecionar-tutor.component';

@Component({
  selector: 'app-animal-adocoes-cadastro',
  templateUrl: './animal-adocoes-cadastro.component.html',
  styleUrls: ['./animal-adocoes-cadastro.component.sass']
})
export class AnimalAdocoesCadastroComponent implements OnInit {
  
  apiURL!: string;
  form!: FormGroup;
  termoResponsabilidade!: Array<any>;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _adocaoService: AdocaoService,
    private _dialog: MatDialog,
    private _dialogRef: MatDialogRef<AnimalAdocoesCadastroComponent>,
    private _formBuilder: FormBuilder,
    private _imagemService: ImagemService,
    private _notificationService: NotificationService
  ) { }

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

            this._imagemService.toBase64(image)?.then((data: any) => {
              this.termoResponsabilidade.push({ id: new Date().getTime(), data: data, file: image });
            })
          });
        }
      }
    });
  }

  buildForm(adocao: Adocao | null) {

    this.form = this._formBuilder.group({
      id: [adocao?.id, Validators.nullValidator],
      dataHora: [adocao?.dataHora, Validators.required],
      local: [adocao?.local, [Validators.required, Validators.maxLength(10)]],
      localAdocao: [adocao?.localAdocao, [Validators.required, Validators.maxLength(10)]],
      valeCastracao: [adocao?.valeCastracao, Validators.required],
      animal: [this.data.animal, Validators.required],
      tutor: [adocao?.tutor, Validators.required],
      termoResponsabilidade: [adocao?.termoResponsabilidade, Validators.nullValidator]
    });
  }

  dateChange() {

    if (this.form.get('dataHora')?.value) {
      
      this.form.get('dataHora')?.patchValue(
        DateUtils.getDateTimeWithoutSecondsAndMilliseconds(this.form.get('dataHora')?.value)
      );
    }
  }

  downloadImagem(imagem: any) {

    const link = document.createElement('a');

    link.href = this.apiURL + '/imagens/search?value=' + imagem.nome;
    link.download = imagem.data;
    link.click();
    link.remove();
  }

  getErrorMessage(controlName: string) {
    return FormUtils.getErrorMessage(this.form, controlName);
  }

  hasError(controlName: string) {
    return FormUtils.hasError(this.form, controlName);
  }

  removeImagem(imagem: any) {
    
    if (this.form.get('termoResponsabilidade')?.value) {
      this.form.get('termoResponsabilidade')?.patchValue(this.form.get('termoResponsabilidade')?.value.filter((t: any) => t.id !== imagem.id));
    }

    this.termoResponsabilidade = this.termoResponsabilidade.filter((t: any) => t.id !== imagem.id);
  }

  selectTutor() {

    this._dialog.open(SelecionarTutorComponent, {
      width: '100%'
    })
    .afterClosed().subscribe({

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
      const images = this.termoResponsabilidade.filter((t: any) => t.file).map((t: any) => t.file);
      adocao.dataHora = DateUtils.addHours(adocao.dataHora, DateUtils.offsetBrasilia);

      if (adocao.id) {

        this._adocaoService.update(adocao, images).subscribe({
          complete: () => {
            this._notificationService.show(MessageUtils.ADOCAO.UPDATE_SUCCESS, NotificationType.SUCCESS);
            this._dialogRef.close({ status: true });
          },
    
          error: (error) => {
            console.error(error);
            this._notificationService.show(MessageUtils.ADOCAO.UPDATE_FAIL + MessageUtils.getMessage(error), NotificationType.FAIL);
          }
        });
      }

      else {

        this._adocaoService.save(adocao, images).subscribe({
          complete: () => {
            this._notificationService.show(MessageUtils.ADOCAO.SAVE_SUCCESS, NotificationType.SUCCESS);
            this._dialogRef.close({ status: true });
          },
    
          error: (error) => {
            console.error(error);
            this._notificationService.show(MessageUtils.ADOCAO.SAVE_FAIL + MessageUtils.getMessage(error), NotificationType.FAIL);
          }
        });
      }
    }
  }
}