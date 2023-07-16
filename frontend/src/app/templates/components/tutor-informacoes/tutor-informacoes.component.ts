import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Authentication } from 'src/app/entities/authentication';
import { Tutor } from 'src/app/entities/tutor';
import { NotificationType } from 'src/app/enums/notification-type';
import { AuthService } from 'src/app/services/auth.service';
import { ImagemService } from 'src/app/services/imagem.service';
import { NotificationService } from 'src/app/services/notification.service';
import { TutorService } from 'src/app/services/tutor.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { environment } from 'src/environments/environment';

import { SelecionarImagemComponent } from '../selecionar-imagem/selecionar-imagem.component';
import { TutorExcluirComponent } from '../tutor-excluir/tutor-excluir.component';

@Component({
  selector: 'app-tutor-informacoes',
  templateUrl: './tutor-informacoes.component.html',
  styleUrls: ['./tutor-informacoes.component.sass'],
})
export class TutorInformacoesComponent implements OnInit {
  apiURL!: string;
  authentication!: Authentication;
  form!: FormGroup;
  foto!: any;
  tutor!: Tutor;

  constructor(
    private _authService: AuthService,
    private _dialog: MatDialog,
    private _formBuilder: FormBuilder,
    private _imagemService: ImagemService,
    private _notificationService: NotificationService,
    private _router: Router,
    private _tutorService: TutorService
  ) {}

  ngOnInit(): void {
    this.apiURL = environment.apiURL;
    this.authentication = this._authService.getAuthentication();

    this._tutorService.get().subscribe({
      next: (tutor) => {
        if (tutor) {
          this.tutor = tutor;
          this.buildForm(tutor);

          if (tutor.foto) {
            this.foto = { id: tutor.foto.id, nome: tutor.foto.nome };
          } else {
            this.foto = null;
          }
        } else {
          this.buildForm(null);
        }
      },
    });
  }

  addFoto() {
    this._dialog
      .open(SelecionarImagemComponent, {
        data: {
          multiple: false,
        },
        width: '100%',
      })
      .afterClosed()
      .subscribe((result) => {
        if (result && result.status) {
          this._imagemService.toBase64(result.images[0])?.then((data) => {
            this.foto = {
              id: new Date().getTime(),
              data: data,
              file: result.images[0],
            };
          });
        }
      });
  }

  addTelefone() {
    this.telefones.push(
      this._formBuilder.group({
        id: [null, Validators.nullValidator],
        numero: [null, Validators.nullValidator],
      })
    );
  }

  buildForm(tutor: Tutor | null) {
    this.form = this._formBuilder.group({
      id: [tutor?.id, Validators.nullValidator],
      nome: [tutor?.nome, Validators.required],
      cpf: [tutor?.cpf, Validators.required],
      rg: [tutor?.rg, Validators.nullValidator],
      situacao: [tutor?.situacao, Validators.required],
      foto: [tutor?.foto, Validators.nullValidator],

      telefones: this._formBuilder.array(
        tutor?.telefones
          ? tutor.telefones.map((telefone) =>
              this._formBuilder.group({
                id: [telefone.id, Validators.nullValidator],
                numero: [telefone.numero, Validators.required],
              })
            )
          : []
      ),

      endereco: [tutor?.endereco, Validators.nullValidator],
      adocoes: [[], Validators.nullValidator],
      observacoes: [[], Validators.nullValidator],
    });

    tutor ? this.form.disable() : this.form.enable();
  }

  cancel() {
    this.foto = this.tutor?.foto
      ? { id: this.tutor?.foto.id, nome: this.tutor?.foto.nome }
      : null;
    this.tutor
      ? this.buildForm(this.tutor)
      : this._router.navigate([
          '/' + this.authentication.role.toLowerCase() + '/tutores',
        ]);
  }

  delete() {
    this._dialog
      .open(TutorExcluirComponent, {
        data: {
          tutor: this.tutor,
        },
        width: '100%',
      })
      .afterClosed()
      .subscribe({
        next: (result) => {
          if (result) {
            this._router.navigate([
              this.authentication.role.toLowerCase() + '/tutores',
            ]);
          }
        },
      });
  }

  get telefones() {
    return this.form.controls['telefones'] as FormArray;
  }

  removeFoto() {
    this.foto = null;
    this.form.get('foto')?.patchValue(null);
  }

  removeTelefone(index: number) {
    this.telefones.removeAt(index);
  }

  submit() {
    const tutor: Tutor = Object.assign({}, this.form.getRawValue());
    const imagem: File = this.foto?.file;

    if (tutor.id) {
      this._tutorService.update(tutor, imagem).subscribe({
        next: (tutor) => {
          this.foto ? (this.foto.file = null) : null;
          this._tutorService.set(tutor);
          this._notificationService.show(
            MessageUtils.TUTOR.UPDATE_SUCCESS,
            NotificationType.SUCCESS
          );
        },

        error: (error) => {
          console.error(error);
          this._notificationService.show(
            MessageUtils.TUTOR.UPDATE_FAIL + MessageUtils.getMessage(error),
            NotificationType.FAIL
          );
        },
      });
    } else {
      this._tutorService.save(tutor, imagem).subscribe({
        next: (tutor) => {
          this.foto ? (this.foto.file = null) : null;
          this._router.navigate([
            '/' +
              this.authentication.role.toLowerCase() +
              '/tutores/' +
              tutor.id,
          ]);
          this._notificationService.show(
            MessageUtils.TUTOR.SAVE_SUCCESS,
            NotificationType.SUCCESS
          );
        },

        error: (error) => {
          console.error(error);
          this._notificationService.show(
            MessageUtils.TUTOR.SAVE_FAIL + MessageUtils.getMessage(error),
            NotificationType.FAIL
          );
        },
      });
    }
  }
}
