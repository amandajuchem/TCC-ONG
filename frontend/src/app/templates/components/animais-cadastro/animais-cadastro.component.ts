import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Animal } from 'src/app/entities/animal';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { environment } from 'src/environments/environment';

import { SelecionarImagemComponent } from '../selecionar-imagem/selecionar-imagem.component';
import { SelecionarTutorComponent } from '../selecionar-tutor/selecionar-tutor.component';

@Component({
  selector: 'app-animais-cadastro',
  templateUrl: './animais-cadastro.component.html',
  styleUrls: ['./animais-cadastro.component.sass']
})
export class AnimaisCadastroComponent implements OnInit {
  
  apiURL!: string;
  form!: FormGroup;
  foto!: any;
  fotoToSave!: any;
  fotoToDelete!: any;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private dialog: MatDialog,
    private dialogRef: MatDialogRef<AnimaisCadastroComponent>,
    private facade: FacadeService,
    private formBuilder: FormBuilder
  ) { }

  ngOnInit(): void {
    
    this.apiURL = environment.apiURL;

    if (this.data.animal) {
      
      this.buildForm(this.data.animal);

      if (this.data.animal.foto) {
        this.foto = { id: this.data.animal.foto.id, nome: this.data.animal.foto.nome, salvo: true};
      }

    } else {
      this.buildForm(null);
    }
  }

  addFoto() {

    this.dialog.open(SelecionarImagemComponent, {
      data: {
        multiple: false
      },
      width: '100%'
    })
    .afterClosed().subscribe(result => {

      if (result && result.status) {

        this.facade.imagemToBase64(result.images[0])?.then(data => {

          if (this.foto && this.foto.salvo) {
            this.fotoToDelete = this.foto;
          }

          let imagem = { id: new Date().getTime(), data: data, nome: null, salvo: false };

          this.foto = imagem;
          this.fotoToSave = result.images[0];
        });
      }
    });
  }

  buildForm(animal: Animal | null) {

    this.form = this.formBuilder.group({
      id: [animal?.id, Validators.nullValidator],
      nome: [animal?.nome, Validators.required],
      idade: [animal?.idade, Validators.required],
      sexo: [animal?.sexo, Validators.required],
      cor: [animal?.cor, Validators.required],
      raca: [animal?.raca, Validators.required],
      especie: [animal?.especie, Validators.required],
      porte: [animal?.porte, Validators.required],
      castrado: [animal?.castrado, Validators.required],
      dataAdocao: [animal?.dataAdocao, Validators.nullValidator],
      dataResgate: [animal?.dataResgate, Validators.nullValidator],
      local: [animal?.local, Validators.nullValidator],
      localAdocao: [animal?.localAdocao, Validators.nullValidator],
      situacao: [animal?.situacao, Validators.required],
      tutor: [animal?.tutor, Validators.nullValidator],
      foto: [animal?.foto, Validators.nullValidator]
    });
  }

  removeFoto() {

    if (this.foto && this.foto.salvo) {
      this.fotoToDelete = this.foto;
    }

    this.foto = null;
    this.form.get('foto')?.patchValue(null);
  }

  selectTutor() {

    this.dialog.open(SelecionarTutorComponent, {
      width: '100%'
    })
    .afterClosed().subscribe({
      
      next: (result) => {
          
        if (result) {
          this.form.get('tutor')?.patchValue(result.tutor);
        }
      },
    });
  }

  submit() {

    let animal: Animal = Object.assign({}, this.form.getRawValue());

    if (animal.id) {
      
      this.facade.animalUpdate(animal, this.fotoToSave, this.fotoToDelete ? this.fotoToDelete.id : null).subscribe({

        complete: () => {
          this.facade.notificationShowNotification(MessageUtils.ANIMAIS_UPDATE_SUCCESS, NotificationType.SUCCESS);
          this.dialogRef.close({status: true});
        },
  
        error: (error) => {
          console.error(error);
          this.facade.notificationShowNotification(MessageUtils.ANIMAIS_UPDATE_FAIL + error.error[0].message, NotificationType.FAIL);
        }
      });
    }

    else {

      this.facade.animalSave(animal, this.fotoToSave).subscribe({

        complete: () => {
          this.facade.notificationShowNotification(MessageUtils.ANIMAIS_SAVE_SUCCESS, NotificationType.SUCCESS);
          this.dialogRef.close({status: true});
        },
  
        error: (error) => {
          console.error(error);
          this.facade.notificationShowNotification(MessageUtils.ANIMAIS_SAVE_FAIL + error.error[0].message, NotificationType.FAIL);
        }
      });
    }
  }
}