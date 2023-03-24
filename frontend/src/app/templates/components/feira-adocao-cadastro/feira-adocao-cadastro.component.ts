import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Animal } from 'src/app/entities/animal';
import { FeiraAdocao } from 'src/app/entities/feira-adocao';
import { Usuario } from 'src/app/entities/usuario';
import { NotificationType } from 'src/app/enums/notification-type';
import { FeiraAdocaoService } from 'src/app/services/feira-adocao.service';
import { NotificationService } from 'src/app/services/notification.service';
import { DateUtils } from 'src/app/utils/date-utils';
import { MessageUtils } from 'src/app/utils/message-utils';

import { SelecionarAnimalComponent } from '../selecionar-animal/selecionar-animal.component';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { SelecionarUsuarioComponent } from '../selecionar-usuario/selecionar-usuario.component';

@Component({
  selector: 'app-feira-adocao-cadastro',
  templateUrl: './feira-adocao-cadastro.component.html',
  styleUrls: ['./feira-adocao-cadastro.component.sass']
})
export class FeiraAdocaoCadastroComponent implements OnInit {

  dataSourceAnimais!: MatTableDataSource<Animal>;
  dataSourceUsuarios!: MatTableDataSource<Usuario>;
  columnsAnimais!: Array<string>;
  columnsUsuarios!: Array<string>;
  form!: FormGroup;

  @ViewChild('animaisPaginator') animaisPaginator!: MatPaginator;
  @ViewChild('usuariosPaginator') usuariosPaginator!: MatPaginator;
  @ViewChild('animaisSort') animaisSort!: MatSort;
  @ViewChild('usuariosSort') usuariosSort!: MatSort;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _dialog: MatDialog,
    private _dialogRef: MatDialogRef<FeiraAdocaoCadastroComponent>,
    private _feiraAdocaoService: FeiraAdocaoService,
    private _formBuilder: FormBuilder,
    private _notificationService: NotificationService
  ) {
    this.dataSourceAnimais = new MatTableDataSource();
    this.dataSourceUsuarios = new MatTableDataSource();
    this.columnsAnimais = ['index', 'nome', 'especie', 'porte', 'idade', 'acao'];
    this.columnsUsuarios = ['index', 'nome', 'setor', 'acao'];
  }

  ngOnInit(): void {

    if (this.data.feiraAdocao) {
      this.buildForm(this.data.feiraAdocao);
      this.dataSourceAnimais.data = this.data.feiraAdocao.animais;
      this.dataSourceUsuarios.data = this.data.feiraAdocao.usuarios;
    } else {
      this.buildForm(null);
    }
  }

  addAnimais() {

    this._dialog.open(SelecionarAnimalComponent, {
      data: {
        multiplus: true
      },
      width: '100%'
    })
    .afterClosed().subscribe({

      next: (result) => {
        
        if (result && result.status) {

          let animais = this.dataSourceAnimais.data;

          result.animais.filter((a: Animal) => !animais.some(animal => animal.id === a.id)).forEach((animal: Animal) => {
            this.dataSourceAnimais.data.push(animal);
          });

          this.dataSourceAnimais._updateChangeSubscription();
        }
      },
    });
  }

  addUsuarios() {
    
    this._dialog.open(SelecionarUsuarioComponent, {
      data: {
        multiplus: true
      },
      width: '100%'
    })
    .afterClosed().subscribe({

      next: (result) => {
        
        if (result && result.status) {

          let usuarios = this.dataSourceUsuarios.data;

          result.usuarios.filter((u: Usuario) => !usuarios.some(usuario => usuario.id === u.id)).forEach((usuario: Usuario) => {
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
      dataHora: [feiraAdocao?.dataHora ? this.getDateWithTimeZone(feiraAdocao.dataHora) : null, Validators.required],
      nome: [feiraAdocao?.nome, Validators.required],
      animais: [feiraAdocao?.animais, Validators.nullValidator],
      usuarios: [feiraAdocao?.usuarios, Validators.nullValidator]
    });
  }

  dateChange() {

    if (this.form.get('dataHora')?.value) {

      this.form.get('dataHora')?.patchValue(
        DateUtils.getDateTimeWithoutSecondsAndMilliseconds(this.form.get('dataHora')?.value)
      );
    }
  }

  getDateWithTimeZone(date: any) {
    return DateUtils.getDateWithTimeZone(date);
  }

  removeAnimal(animal: Animal) {
    this.dataSourceAnimais.data = this.dataSourceAnimais.data.filter(a => a.id !== animal.id);
    this.dataSourceAnimais._updateChangeSubscription();
  }

  removeUsuario(usuario: Usuario) {
    this.dataSourceUsuarios.data = this.dataSourceUsuarios.data.filter(u => u.id !== usuario.id);
    this.dataSourceUsuarios._updateChangeSubscription();
  }

  submit() {

    const feiraAdocao: FeiraAdocao = Object.assign({}, this.form.getRawValue());

    feiraAdocao.animais = this.dataSourceAnimais.data;
    feiraAdocao.usuarios = this.dataSourceUsuarios.data;

    if (feiraAdocao.id) {

      this._feiraAdocaoService.update(feiraAdocao).subscribe({

        complete: () => {
          this._notificationService.show(MessageUtils.FEIRA_ADOCAO_UPDATE_SUCCESS, NotificationType.SUCCESS);
          this._dialogRef.close({ status: true });
        },

        error: (error) => {
          console.error(error);
          this._notificationService.show(MessageUtils.FEIRA_ADOCAO_UPDATE_FAIL + error.error[0].message, NotificationType.FAIL);
        }
      });
    }

    else {

      this._feiraAdocaoService.save(feiraAdocao).subscribe({

        complete: () => {
          this._notificationService.show(MessageUtils.FEIRA_ADOCAO_SAVE_SUCCESS, NotificationType.SUCCESS);
          this._dialogRef.close({ status: true });
        },

        error: (error) => {
          console.error(error);
          this._notificationService.show(MessageUtils.FEIRA_ADOCAO_SAVE_FAIL + error.error[0].message, NotificationType.FAIL);
        }
      });
    }
  }
}