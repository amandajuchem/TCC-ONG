import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Animal } from 'src/app/entities/animal';
import { NotificationType } from 'src/app/enums/notification-type';
import { AnimalService } from 'src/app/services/animal.service';
import { ImagemService } from 'src/app/services/imagem.service';
import { NotificationService } from 'src/app/services/notification.service';
import { MessageUtils } from 'src/app/utils/message-utils';

import { SelecionarImagemComponent } from '../selecionar-imagem/selecionar-imagem.component';
import { SelecionarTutorComponent } from '../selecionar-tutor/selecionar-tutor.component';

@Component({
  selector: 'app-animal-cadastro',
  templateUrl: './animal-cadastro.component.html',
  styleUrls: ['./animal-cadastro.component.sass']
})
export class AnimalCadastroComponent implements OnInit {
  
  form!: FormGroup;
  foto!: any;

  constructor(
    private _animalService: AnimalService,
    private _dialog: MatDialog,
    private _dialogRef: MatDialogRef<AnimalCadastroComponent>,
    private _formBuilder: FormBuilder,
    private _imagemService: ImagemService,
    private _notificationService: NotificationService
  ) { }

  ngOnInit(): void {
    this.buildForm();
  }

  addFoto() {

    this._dialog.open(SelecionarImagemComponent, {
      data: {
        multiple: false
      },
      width: '100%'
    })
    .afterClosed().subscribe(result => {

      if (result && result.status) {

        this._imagemService.toBase64(result.images[0])?.then(data => {
          this.foto = { id: new Date().getTime(), data: data, file: result.images[0] };;
        });
      }
    });
  }

  buildForm() {

    this.form = this._formBuilder.group({
      id: [null, Validators.nullValidator],
      nome: [null, Validators.required],
      idade: [null, Validators.required],
      sexo: [null, Validators.required],
      cor: [null, Validators.nullValidator],
      raca: [null, Validators.nullValidator],
      especie: [null, Validators.required],
      porte: [null, Validators.nullValidator],
      situacao: [null, Validators.required],
      foto: [null, Validators.nullValidator],
      
      fichaMedica: this._formBuilder.group({
        comorbidades: [null, Validators.required],
        castrado: [null, Validators.required]
      })
    });
  }

  removeFoto() {
    this.foto = null;
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

    const animal: Animal = Object.assign({}, this.form.getRawValue());
    const imagem: File = this.foto?.file;

    this._animalService.save(animal, imagem).subscribe({

      complete: () => {
        this._notificationService.show(MessageUtils.ANIMAL_SAVE_SUCCESS, NotificationType.SUCCESS);
        this._dialogRef.close({ status: true });
      },

      error: (error) => {
        console.error(error);
        this._notificationService.show(MessageUtils.ANIMAL_SAVE_FAIL + error.error[0].message, NotificationType.FAIL);
      }
    });
  }
}