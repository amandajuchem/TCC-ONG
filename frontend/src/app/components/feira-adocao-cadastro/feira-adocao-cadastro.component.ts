import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute } from '@angular/router';
import { Animal } from 'src/app/entities/animal';
import { FeiraAdocao } from 'src/app/entities/feira-adocao';
import { Usuario } from 'src/app/entities/usuario';
import { NotificationType } from 'src/app/enums/notification-type';
import { FeiraAdocaoService } from 'src/app/services/feira-adocao.service';
import { NotificationService } from 'src/app/services/notification.service';
import { RedirectService } from 'src/app/services/redirect.service';
import { DateUtils } from 'src/app/utils/date-utils';
import { FormUtils } from 'src/app/utils/form-utils';
import { MessageUtils } from 'src/app/utils/message-utils';

import { SelecionarAnimalComponent } from '../selecionar-animal/selecionar-animal.component';
import { SelecionarUsuarioComponent } from '../selecionar-usuario/selecionar-usuario.component';
import { Adocao } from 'src/app/entities/adocao';
import { AdocaoService } from 'src/app/services/adocao.service';
import { Situacao } from 'src/app/enums/situacao';

@Component({
  selector: 'app-feira-adocao-cadastro',
  templateUrl: './feira-adocao-cadastro.component.html',
  styleUrls: ['./feira-adocao-cadastro.component.sass'],
})
export class FeiraAdocaoCadastroComponent implements OnInit {
  adocoes!: Array<Adocao>;
  dataSourceAnimais!: MatTableDataSource<Animal>;
  dataSourceUsuarios!: MatTableDataSource<Usuario>;
  columnsAnimais!: Array<string>;
  columnsUsuarios!: Array<string>;
  feiraAdocao!: FeiraAdocao;
  form!: FormGroup;

  @ViewChild('animaisPaginator') animaisPaginator!: MatPaginator;
  @ViewChild('usuariosPaginator') usuariosPaginator!: MatPaginator;
  @ViewChild('animaisSort') animaisSort!: MatSort;
  @ViewChild('usuariosSort') usuariosSort!: MatSort;

  constructor(
    private _adocaoService: AdocaoService,
    private _activatedRoute: ActivatedRoute,
    private _dialog: MatDialog,
    private _feiraAdocaoService: FeiraAdocaoService,
    private _formBuilder: FormBuilder,
    private _notificationService: NotificationService,
    private _redirectService: RedirectService
  ) {
    this.adocoes = [];
    this.dataSourceAnimais = new MatTableDataSource();
    this.dataSourceUsuarios = new MatTableDataSource();
    this.columnsAnimais = ['index', 'nome', 'especie', 'adotado', 'acao'];
    this.columnsUsuarios = ['index', 'nome', 'setor', 'acao'];
  }

  ngOnInit(): void {
    this._activatedRoute.params.subscribe({
      next: (params: any) => {
        if (params && params.id) {
          if (params.id.includes('cadastro')) {
            this.buildForm(null);
          } else {
            this._feiraAdocaoService.findById(params.id).subscribe({
              next: (feiraAdocao) => {
                this.feiraAdocao = feiraAdocao;
                this.dataSourceAnimais.data = feiraAdocao.animais;
                this.dataSourceUsuarios.data = feiraAdocao.usuarios;
                this.buildForm(feiraAdocao);
              },

              error: (error) => {
                console.error(error);
                this._notificationService.show(
                  MessageUtils.FEIRA_ADOCAO.GET_FAIL +
                    MessageUtils.getMessage(error),
                  NotificationType.FAIL
                );
              },
            });

            this.findAllAdocoes(params.id, 0);
          }
        }
      },
    });
  }

  addAnimais() {
    this._dialog
      .open(SelecionarAnimalComponent, {
        data: {
          multiplus: true,
          situacao: Situacao.ESPERANDO
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
            const animais = this.dataSourceAnimais.data;

            result.animais
              // Remove os animais que já foram inseridos anteriormente, evitando assim duplicações
              .filter(
                (a: Animal) => !animais.some((animal) => animal.id === a.id)
              )
              // Adiciona os animais a tabela
              .forEach((animal: Animal) => {
                this.dataSourceAnimais.data.push(animal);
              });
            this.dataSourceAnimais._updateChangeSubscription();
          }
        },
      });
  }

  addUsuarios() {
    this._dialog
      .open(SelecionarUsuarioComponent, {
        data: {
          multiplus: true,
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
            const usuarios = this.dataSourceUsuarios.data;

            result.usuarios
              // Remove os usuários que já foram inseridos anteriormente, evitando assim duplicações
              .filter(
                (u: Usuario) => !usuarios.some((usuario) => usuario.id === u.id)
              )
              // Adiciona os usuários a tabela
              .forEach((usuario: Usuario) => {
                this.dataSourceUsuarios.data.push(usuario);
              });
            this.dataSourceUsuarios._updateChangeSubscription();
          }
        },
      });
  }

  buildForm(feiraAdocao: FeiraAdocao | null) {
    this.form = this._formBuilder.group({
      id: [feiraAdocao?.id, Validators.nullValidator],
      dataHora: [feiraAdocao?.dataHora, Validators.required],
      nome: [
        feiraAdocao?.nome,
        [Validators.required, Validators.maxLength(100)],
      ],
      animais: [feiraAdocao?.animais, Validators.nullValidator],
      usuarios: [feiraAdocao?.usuarios, Validators.nullValidator],
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

  findAllAdocoes(id: string, page: number) {
    const size = 10;
    const sort = 'dataHora';
    const direction = 'desc';

    this._adocaoService.search(id, page, size, sort, direction).subscribe({
      next: (adocoes) => {
        
        this.adocoes.push(...adocoes.content);
        this.adocoes.forEach(adocao => {

          if (!this.dataSourceAnimais.data.some(animal => animal.id === adocao.animal.id)) {
            this.dataSourceAnimais.data.push(adocao.animal);
            this.dataSourceAnimais._updateChangeSubscription();
          }
        });

        if ((adocoes.totalPages - 1) !== page) {
          this.findAllAdocoes(id, page + 1);
        }
      },

      error: (error) => {
        console.error(error);
        this._notificationService.show(
          MessageUtils.ADOCAO.LIST_GET_FAIL + MessageUtils.getMessage(error),
          NotificationType.FAIL
        );
      },
    });
  }

  getErrorMessage(controlName: string) {
    return FormUtils.getErrorMessage(this.form, controlName);
  }

  hasError(controlName: string) {
    return FormUtils.hasError(this.form, controlName);
  }

  isAdotado(animal: Animal) {
    return this.adocoes.some((adocao) => adocao.animal.id === animal.id);
  }

  removeAnimal(animal: Animal) {
    this.dataSourceAnimais.data = this.dataSourceAnimais.data.filter(
      (a) => a.id !== animal.id
    );
    this.dataSourceAnimais._updateChangeSubscription();
  }

  removeUsuario(usuario: Usuario) {
    this.dataSourceUsuarios.data = this.dataSourceUsuarios.data.filter(
      (u) => u.id !== usuario.id
    );
    this.dataSourceUsuarios._updateChangeSubscription();
  }

  redirectToFeirasAdocaoList() {
    this._redirectService.toFeiraAdocaoList();
  }

  submit() {
    if (this.form.valid) {
      const feiraAdocao: FeiraAdocao = Object.assign(
        {},
        this.form.getRawValue()
      );

      feiraAdocao.dataHora = DateUtils.addHours(
        feiraAdocao.dataHora,
        DateUtils.offsetBrasilia
      );
      feiraAdocao.animais = this.dataSourceAnimais.data;
      feiraAdocao.usuarios = this.dataSourceUsuarios.data;

      if (feiraAdocao.id) {
        this._feiraAdocaoService.update(feiraAdocao).subscribe({
          complete: () => {
            this._notificationService.show(
              MessageUtils.FEIRA_ADOCAO.UPDATE_SUCCESS,
              NotificationType.SUCCESS
            );
            this.ngOnInit();
          },

          error: (error) => {
            console.error(error);
            this._notificationService.show(
              MessageUtils.FEIRA_ADOCAO.UPDATE_FAIL +
                MessageUtils.getMessage(error),
              NotificationType.FAIL
            );
          },
        });
      } else {
        this._feiraAdocaoService.save(feiraAdocao).subscribe({
          next: (feiraAdocao) => {
            this._notificationService.show(
              MessageUtils.FEIRA_ADOCAO.SAVE_SUCCESS,
              NotificationType.SUCCESS
            );
            this._redirectService.toFeiraAdocao(feiraAdocao.id);
          },

          error: (error) => {
            console.error(error);
            this._notificationService.show(
              MessageUtils.FEIRA_ADOCAO.SAVE_FAIL +
                MessageUtils.getMessage(error),
              NotificationType.FAIL
            );
          },
        });
      }
    }
  }
}
