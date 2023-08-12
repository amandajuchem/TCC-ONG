import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { Agendamento } from 'src/app/entities/agendamento';
import { Atendimento } from 'src/app/entities/atendimento';
import { Exame } from 'src/app/entities/exame';
import { NotificationType } from 'src/app/enums/notification-type';
import { Setor } from 'src/app/enums/setor';
import { AtendimentoService } from 'src/app/services/atendimento.service';
import { ImagemService } from 'src/app/services/imagem.service';
import { NotificationService } from 'src/app/services/notification.service';
import { RedirectService } from 'src/app/services/redirect.service';
import { DateUtils } from 'src/app/utils/date-utils';
import { FormUtils } from 'src/app/utils/form-utils';
import { MessageUtils } from 'src/app/utils/message-utils';
import { environment } from 'src/environments/environment';

import { SelecionarAgendamentoComponent } from '../selecionar-agendamento/selecionar-agendamento.component';
import { SelecionarAnimalComponent } from '../selecionar-animal/selecionar-animal.component';
import { SelecionarExameComponent } from '../selecionar-exame/selecionar-exame.component';
import { SelecionarImagemComponent } from '../selecionar-imagem/selecionar-imagem.component';
import { SelecionarUsuarioComponent } from '../selecionar-usuario/selecionar-usuario.component';

@Component({
  selector: 'app-atendimento-cadastro',
  templateUrl: './atendimento-cadastro.component.html',
  styleUrls: ['./atendimento-cadastro.component.sass'],
})
export class AtendimentoCadastroComponent implements OnInit {
  apiURL!: string;
  atendimento!: Atendimento;
  documentos!: Array<any>;
  form!: FormGroup;

  constructor(
    private _activatedRoute: ActivatedRoute,
    private _atendimentoService: AtendimentoService,
    private _dialog: MatDialog,
    private _formBuilder: FormBuilder,
    private _imagemService: ImagemService,
    private _notificationService: NotificationService,
    private _redirectService: RedirectService
  ) {}

  ngOnInit(): void {
    this.apiURL = environment.apiURL;
    this.documentos = [];

    this._activatedRoute.params.subscribe({
      next: (params: any) => {
        if (params && params.id) {
          if (params.id.includes('cadastro')) {
            this.buildForm(null);
          } else {
            this._atendimentoService.findById(params.id).subscribe({
              next: (atendimento) => {
                this.atendimento = atendimento;
                this.buildForm(atendimento);

                if (atendimento.documentos) {
                  atendimento.documentos.forEach((d: any) => {
                    this.documentos.push({ id: d.id, nome: d.nome });
                  });
                }
              },

              error: (error) => {
                console.error(error);
                this._notificationService.show(
                  MessageUtils.ATENDIMENTO.GET_FAIL +
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

  addImagem() {
    this._dialog
      .open(SelecionarImagemComponent, {
        data: {
          multiple: true,
        },
        width: '100%',
      })
      .afterClosed()
      .subscribe({
        next: (result) => {
          if (result && result.status) {
            result.images.forEach((image: any) => {
              this._imagemService.toBase64(image)?.then((data: any) => {
                this.documentos.push({
                  id: new Date().getTime(),
                  data: data,
                  file: image,
                });
              });
            });
          }
        },
      });
  }

  addExame() {
    this._dialog
      .open(SelecionarExameComponent, {
        width: '100%',
      })
      .afterClosed()
      .subscribe({
        next: (result) => {
          if (result && result.status) {
            let exames: Array<Exame> = this.form.get('exames')?.value;

            if (!exames) {
              exames = [];
            }

            const exists = exames.some((exame) => exame.id === result.exame.id);

            if (!exists) {
              exames.push(result.exame);
              this.form.get('exames')?.patchValue(exames);
            }
          }
        },
      });
  }

  buildForm(atendimento: Atendimento | null) {
    this.form = this._formBuilder.group({
      id: [atendimento?.id, Validators.nullValidator],
      animal: [atendimento?.animal, Validators.required],
      veterinario: [atendimento?.veterinario, Validators.required],
      comorbidades: [
        atendimento?.animal.fichaMedica?.comorbidades,
        Validators.nullValidator,
      ],
      dataHora: [atendimento?.dataHora, Validators.required],
      motivo: [
        atendimento?.motivo,
        [Validators.required, Validators.maxLength(25)],
      ],
      diagnostico: [atendimento?.diagnostico, Validators.required],
      posologia: [atendimento?.posologia, Validators.required],
      exames: [atendimento?.exames, Validators.required],
      documentos: [atendimento?.documentos, Validators.nullValidator],
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

  downloadImagem(imagem: any) {
    const link = document.createElement('a');

    link.href = this.apiURL + '/imagens/search?value=' + imagem.nome;
    link.download = imagem.nome;
    link.click();
    link.remove();
  }

  getErrorMessage(controlName: string) {
    return FormUtils.getErrorMessage(this.form, controlName);
  }

  hasError(controlName: string) {
    return FormUtils.hasError(this.form, controlName);
  }

  importFromAgendamento() {
    this._dialog
      .open(SelecionarAgendamentoComponent, {
        data: {
          multiplus: false,
        },
        width: '100%',
      })
      .afterClosed()
      .subscribe({
        next: (result) => {
          if (result && result.status) {
            const agendamento: Agendamento = result.agendamento;

            this.form.get('animal')?.patchValue(agendamento.animal);
            this.form.get('veterinario')?.patchValue(agendamento.veterinario);
            this.form
              .get('comorbidades')
              ?.patchValue(
                agendamento.animal.fichaMedica
                  ? agendamento.animal.fichaMedica.comorbidades
                  : ''
              );
            this.form.get('dataHora')?.patchValue(agendamento.dataHora);
          }
        },
      });
  }

  redirectToAtendimentoList() {
    this._redirectService.toAtendimentoList();
  }

  removeExame(exame: Exame) {
    let exames: Array<Exame> = this.form.get('exames')?.value;
    exames = exames.filter((e) => e.id != exame.id);
    this.form.get('exames')?.patchValue(exames);
  }

  removeImagem(imagem: any) {
    if (this.form.get('documentos')?.value) {
      this.form
        .get('documentos')
        ?.patchValue(
          this.form
            .get('documentos')
            ?.value.filter((d: any) => d.id !== imagem.id)
        );
    }

    this.documentos = this.documentos.filter((d: any) => d.id !== imagem.id);
  }

  selectAnimal() {
    this._dialog
      .open(SelecionarAnimalComponent, {
        data: {
          multiplus: false,
        },
        width: '100%',
      })
      .afterClosed()
      .subscribe({
        next: (result) => {
          if (result && result.status) {
            this.form.get('animal')?.patchValue(result.animal);
            this.form
              .get('comorbidades')
              ?.patchValue(
                result.animal.fichaMedica
                  ? result.animal.fichaMedica.comorbidades
                  : ''
              );
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
        width: '100%',
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

  submit() {
    if (this.form.valid) {
      const atendimento: Atendimento = Object.assign(
        {},
        this.form.getRawValue()
      );
      const imagens: Array<File> = this.documentos
        .filter((d: any) => d.file)
        .map((d: any) => d.file);
      atendimento.dataHora = DateUtils.addHours(
        atendimento.dataHora,
        DateUtils.offsetBrasilia
      );

      if (atendimento.id) {
        this._atendimentoService.update(atendimento, imagens).subscribe({
          complete: () => {
            this._notificationService.show(
              MessageUtils.ATENDIMENTO.UPDATE_SUCCESS,
              NotificationType.SUCCESS
            );
            this.ngOnInit();
          },

          error: (error) => {
            console.error(error);
            this._notificationService.show(
              MessageUtils.ATENDIMENTO.UPDATE_FAIL +
                MessageUtils.getMessage(error),
              NotificationType.FAIL
            );
          },
        });
      } else {
        this._atendimentoService.save(atendimento, imagens).subscribe({
          next: (atendimento) => {
            this._notificationService.show(
              MessageUtils.ATENDIMENTO.SAVE_SUCCESS,
              NotificationType.SUCCESS
            );
            this.documentos = [];
            this._redirectService.toAtendimento(atendimento.id);
          },

          error: (error) => {
            console.error(error);
            this._notificationService.show(
              MessageUtils.ATENDIMENTO.SAVE_FAIL +
                MessageUtils.getMessage(error),
              NotificationType.FAIL
            );
          },
        });
      }
    }
  }
}
