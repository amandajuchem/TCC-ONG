import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Animal } from 'src/app/entities/animal';
import { NotificationType } from 'src/app/enums/notification-type';
import { AnimalService } from 'src/app/services/animal.service';
import { ImagemService } from 'src/app/services/imagem.service';
import { NotificationService } from 'src/app/services/notification.service';
import { RedirectService } from 'src/app/services/redirect.service';
import { FormUtils } from 'src/app/utils/form-utils';
import { MessageUtils } from 'src/app/utils/message-utils';
import { environment } from 'src/environments/environment';

import { SelecionarImagemComponent } from '../selecionar-imagem/selecionar-imagem.component';

@Component({
  selector: 'app-animal-informacoes',
  templateUrl: './animal-informacoes.component.html',
  styleUrls: ['./animal-informacoes.component.sass'],
})
export class AnimalInformacoesComponent implements OnInit {
  animal!: Animal;
  apiURL!: string;
  form!: FormGroup;
  foto!: any;

  constructor(
    private _animalService: AnimalService,
    private _dialog: MatDialog,
    private _formBuilder: FormBuilder,
    private _imagemService: ImagemService,
    private _notificationService: NotificationService,
    private _redirectService: RedirectService
  ) {}

  ngOnInit(): void {
    this.apiURL = environment.apiURL;

    this._animalService.get().subscribe({
      next: (animal) => {
        if (animal) {
          this.animal = animal;
          this.buildForm(animal);

          if (animal.foto) {
            this.foto = { id: animal.foto.id, nome: animal.foto.nome };
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

  buildForm(animal: Animal | null) {
    this.form = this._formBuilder.group({
      id: [animal?.id, Validators.nullValidator],
      nome: [animal?.nome, [Validators.required, Validators.maxLength(50)]],
      idade: [animal?.idade, Validators.required],
      sexo: [animal?.sexo, [Validators.required, Validators.maxLength(5)]],
      cor: [animal?.cor, Validators.maxLength(50)],
      raca: [animal?.raca, Validators.maxLength(50)],
      especie: [
        animal?.especie,
        [Validators.required, Validators.maxLength(10)],
      ],
      porte: [animal?.porte, Validators.maxLength(10)],
      situacao: [
        animal?.situacao,
        [Validators.required, Validators.maxLength(10)],
      ],
      foto: [animal?.foto, Validators.nullValidator],
      fichaMedica: [animal?.fichaMedica, Validators.nullValidator],
      adocoes: [[], Validators.nullValidator],
    });
  }

  delete() {}

  getErrorMessage(controlName: string) {
    return FormUtils.getErrorMessage(this.form, controlName);
  }

  hasError(controlName: string) {
    return FormUtils.hasError(this.form, controlName);
  }

  removeFoto() {
    this.foto = null;
    this.form.get('foto')?.patchValue(null);
  }

  redirectToAnimalList() {
    this._redirectService.toAnimalList();
  }

  submit() {
    if (this.form.valid) {
      const animal: Animal = Object.assign({}, this.form.getRawValue());
      const imagem: File = this.foto?.file;

      if (animal.id) {
        this._animalService.update(animal, imagem).subscribe({
          next: (animal) => {
            this.foto ? (this.foto.file = null) : null;
            this._animalService.set(animal);
            this._notificationService.show(
              MessageUtils.ANIMAL.UPDATE_SUCCESS,
              NotificationType.SUCCESS
            );
          },

          error: (error) => {
            console.error(error);
            this._notificationService.show(
              MessageUtils.ANIMAL.UPDATE_FAIL + MessageUtils.getMessage(error),
              NotificationType.FAIL
            );
          },
        });
      } else {
        this._animalService.save(animal, imagem).subscribe({
          next: (animal) => {
            this.foto ? (this.foto.file = null) : null;
            this._redirectService.toAnimal(animal.id);
            this._notificationService.show(
              MessageUtils.ANIMAL.SAVE_SUCCESS,
              NotificationType.SUCCESS
            );
          },

          error: (error) => {
            console.error(error);
            this._notificationService.show(
              MessageUtils.ANIMAL.SAVE_FAIL + MessageUtils.getMessage(error),
              NotificationType.FAIL
            );
          },
        });
      }
    }
  }
}
