import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Animal } from 'src/app/entities/animal';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { environment } from 'src/environments/environment';

import { SelecionarImagemComponent } from '../selecionar-imagem/selecionar-imagem.component';
import { SelecionarTutorComponent } from '../selecionar-tutor/selecionar-tutor.component';

@Component({
  selector: 'app-animal-cadastro',
  templateUrl: './animal-cadastro.component.html',
  styleUrls: ['./animal-cadastro.component.sass']
})
export class AnimalCadastroComponent implements OnInit {
  
  apiURL!: string;
  form!: FormGroup;
  foto!: any;
  fotoToSave!: any;

  constructor(
    private _dialog: MatDialog,
    private _dialogRef: MatDialogRef<AnimalCadastroComponent>,
    private _facade: FacadeService,
    private _formBuilder: FormBuilder
  ) { }

  ngOnInit(): void {
    
    this.apiURL = environment.apiURL;
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

        this._facade.imagemToBase64(result.images[0])?.then(data => {

          let imagem = { id: new Date().getTime(), data: data };

          this.foto = imagem;
          this.fotoToSave = result.images[0];
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
      cor: [null, Validators.required],
      raca: [null, Validators.required],
      especie: [null, Validators.required],
      porte: [null, Validators.required],
      castrado: [null, Validators.required],
      dataAdocao: [null, Validators.nullValidator],
      dataResgate: [null, Validators.nullValidator],
      local: [null, Validators.nullValidator],
      localAdocao: [null, Validators.nullValidator],
      situacao: [null, Validators.required],
      tutor: [null, Validators.nullValidator],
      foto: [null, Validators.nullValidator]
    });
  }

  removeFoto() {

    this.foto = null;
    this.form.get('foto')?.patchValue(null);
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

    let animal: Animal = Object.assign({}, this.form.getRawValue());

    this._facade.animalSave(animal, this.fotoToSave).subscribe({

      complete: () => {
        this._facade.notificationShowNotification(MessageUtils.ANIMAL_SAVE_SUCCESS, NotificationType.SUCCESS);
        this._dialogRef.close({status: true});
      },

      error: (error) => {
        console.error(error);
        this._facade.notificationShowNotification(MessageUtils.ANIMAL_SAVE_FAIL + error.error[0].message, NotificationType.FAIL);
      }
    });
  }
}