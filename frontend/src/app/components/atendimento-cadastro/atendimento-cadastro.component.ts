import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { Agendamento } from 'src/app/entities/agendamento';
import { Atendimento } from 'src/app/entities/atendimento';
import { Exame } from 'src/app/entities/exame';
import { ExameRealizado } from 'src/app/entities/exame-realizado';
import { NotificationType } from 'src/app/enums/notification-type';
import { Setor } from 'src/app/enums/setor';
import { AtendimentoService } from 'src/app/services/atendimento.service';
import { ImagemService } from 'src/app/services/imagem.service';
import { NotificationService } from 'src/app/services/notification.service';
import { RedirectService } from 'src/app/services/redirect.service';
import { DateUtils } from 'src/app/utils/date-utils';
import { FormUtils } from 'src/app/utils/form-utils';
import { MessageUtils } from 'src/app/utils/message-utils';
import { OperatorUtils } from 'src/app/utils/operator-utils';
import { environment } from 'src/environments/environment';

import { SelecionarAgendamentoComponent } from '../selecionar-agendamento/selecionar-agendamento.component';
import { SelecionarAnimalComponent } from '../selecionar-animal/selecionar-animal.component';
import { SelecionarArquivoComponent } from '../selecionar-arquivo/selecionar-arquivo.component';
import { SelecionarExameComponent } from '../selecionar-exame/selecionar-exame.component';
import { SelecionarUsuarioComponent } from '../selecionar-usuario/selecionar-usuario.component';
import { FileUtils } from 'src/app/utils/file-utils';

@Component({
  selector: 'app-atendimento-cadastro',
  templateUrl: './atendimento-cadastro.component.html',
  styleUrls: ['./atendimento-cadastro.component.sass'],
})
export class AtendimentoCadastroComponent implements OnInit {
  apiURL!: string;
  atendimento!: Atendimento;
  examesRealizados!: Array<any>;
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
    this.examesRealizados = [];

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

                atendimento.examesRealizados.forEach((exameRealizado) => {
                  this.examesRealizados.push(exameRealizado);
                });
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

  async addExame() {
    const exame = await this.selectExame();
    await OperatorUtils.delay(500);

    const exists = this.examesRealizados.some(
      (e: any) => e.exame.id === exame.id
    );

    if (!exists) {
      const { file } = await this.selectArquivo();

      if (file) {
        const exameRealizado = { exame, file };
        this.examesRealizados.push(exameRealizado);
      }
    } else {
      this._notificationService.show(
        'Exame já cadastrado!',
        NotificationType.FAIL
      );
    }
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
      diagnostico: [atendimento?.diagnostico, Validators.nullValidator],
      posologia: [atendimento?.posologia, Validators.nullValidator],
      examesRealizados: [
        atendimento?.examesRealizados,
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

  downloadArquivo(exameRealizado: ExameRealizado) {
    const link = document.createElement('a');

    link.href = this.apiURL + '/arquivos/search?value=' + exameRealizado.arquivo.nome;
    link.download = exameRealizado.arquivo.nome;
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
        disableClose: true,
        width: '100%',
        minHeight: 'auto',
        maxHeight: '100vh',
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
              ?.patchValue(agendamento.animal.fichaMedica?.comorbidades ?? '');
            this.form.get('dataHora')?.patchValue(agendamento.dataHora);
          }
        },
      });
  }

  isArquivo(arquivo: any): string {
    
    console.log(arquivo);

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

  redirectToAtendimentoList() {
    this._redirectService.toAtendimentoList();
  }

  removeExameRealizado(exameRealizado: any) {
    if (exameRealizado.id) {
      const examesRealizados: Array<ExameRealizado> =
        this.form.get('examesRealizados')?.value;
      this.form
        .get('examesRealizados')
        ?.patchValue(
          examesRealizados.filter((e) => e.id !== exameRealizado.id)
        );
    }

    this.examesRealizados = this.examesRealizados.filter(
      (e) => e.exame.id !== exameRealizado.exame.id
    );
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
        maxHeight: '100vh',
      })
      .afterClosed()
      .subscribe({
        next: (result) => {
          if (result && result.status) {
            this.form.get('animal')?.patchValue(result.animal);
            this.form
              .get('comorbidades')
              ?.patchValue(result.animal.fichaMedica?.comorbidades ?? '');
          }
        },
      });
  }

  selectExame() {
    return new Promise<Exame>((resolve, reject) => {
      this._dialog
        .open(SelecionarExameComponent, {
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
              resolve(result.exame);
            }
          },
        });
    });
  }

  selectArquivo() {
    return new Promise<any>((resolve, reject) => {
      this._dialog
        .open(SelecionarArquivoComponent, {
          data: {
            multiple: false,
          },
          disableClose: true,
          width: '100%',
          minHeight: 'auto',
          maxHeight: '100vh',
        })
        .afterClosed()
        .subscribe({
          next: async (result) => {
            if (result && result.status) {
              const arquivo = {file: result.arquivo};
              resolve(arquivo);
            }
          },
        });
    });
  }

  selectVeterinario() {
    this._dialog
      .open(SelecionarUsuarioComponent, {
        data: {
          multiplus: false,
          setor: Setor.VETERINARIO,
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

      atendimento.dataHora = DateUtils.addHours(
        atendimento.dataHora,
        DateUtils.offsetBrasilia
      );

      atendimento.examesRealizados = atendimento.examesRealizados ?? [];

      this.examesRealizados.forEach((exameRealizado) => {
        if (!exameRealizado.id) {
          atendimento.examesRealizados.push({
            exame: exameRealizado.exame,
            imagem: null,
          } as any);
        }
      });

      if (atendimento.id) {
        this._atendimentoService
          .update(atendimento, this.examesRealizados)
          .subscribe({
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
        this._atendimentoService
          .save(atendimento, this.examesRealizados)
          .subscribe({
            next: (atendimento) => {
              this._notificationService.show(
                MessageUtils.ATENDIMENTO.SAVE_SUCCESS,
                NotificationType.SUCCESS
              );
              this.examesRealizados = [];
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
