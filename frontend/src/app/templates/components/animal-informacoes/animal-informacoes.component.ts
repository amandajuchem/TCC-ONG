import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Animal } from 'src/app/entities/animal';
import { User } from 'src/app/entities/user';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { environment } from 'src/environments/environment';

import { AnimalExcluirComponent } from '../animal-excluir/animal-excluir.component';
import { SelecionarImagemComponent } from '../selecionar-imagem/selecionar-imagem.component';
import { SelecionarTutorComponent } from '../selecionar-tutor/selecionar-tutor.component';

@Component({
  selector: 'app-animal-informacoes',
  templateUrl: './animal-informacoes.component.html',
  styleUrls: ['./animal-informacoes.component.sass']
})
export class AnimalInformacoesComponent implements OnInit {

  animal!: Animal;
  apiURL!: string;
  form!: FormGroup;
  foto!: any;
  fotoToSave!: any;
  fotoToDelete!: any;
  user!: User;

  constructor(
    private _dialog: MatDialog,
    private _facade: FacadeService,
    private _formBuilder: FormBuilder,
    private _router: Router
  ) { }

  ngOnInit(): void {
    
    this.apiURL = environment.apiURL;
    this.user = this._facade.authGetCurrentUser();

    this._facade.animalGet().subscribe({

      next: (animal) => {
          
        if (animal) {
          this.animal = animal;
          this.buildForm(animal);

          if (animal.foto) {
            this.foto = { id: animal.foto.id, nome: animal.foto.nome, salvo: true};
          }
        }
      }
    });
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

  buildForm(animal: Animal) {

    this.form =this._formBuilder.group({
      id: [animal.id, Validators.nullValidator],
      nome: [animal.nome, Validators.required],
      idade: [animal.idade, Validators.required],
      sexo: [animal.sexo, Validators.required],
      cor: [animal.cor, Validators.required],
      raca: [animal.raca, Validators.required],
      especie: [animal.especie, Validators.required],
      porte: [animal.porte, Validators.required],
      castrado: [animal.castrado, Validators.required],
      dataAdocao: [animal.dataAdocao, Validators.nullValidator],
      dataResgate: [animal.dataResgate, Validators.nullValidator],
      local: [animal.local, Validators.nullValidator],
      localAdocao: [animal.localAdocao, Validators.nullValidator],
      situacao: [animal.situacao, Validators.required],
      tutor: [animal.tutor, Validators.nullValidator],
      foto: [animal.foto, Validators.nullValidator]
    });

    this.form.disable();
  }

  delete() {

    this._dialog.open(AnimalExcluirComponent, {
      data: {
        animal: this.animal
      },
      width: '100%'
    })
    .afterClosed().subscribe({

      next: (result) => {
        
        if (result) {
          this._router.navigate([this.user.role.toLowerCase() + '/animais']);
        }
      }
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

    this._dialog.open(SelecionarTutorComponent, {
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

    this._facade.animalUpdate(animal, this.fotoToSave, this.fotoToDelete?.id).subscribe({

      next: (animal) => {
        this._facade.notificationShowNotification(MessageUtils.ANIMAL_UPDATE_SUCCESS, NotificationType.SUCCESS);
        this._facade.animalSet(animal);
      },

      error: (error) => {
        console.error(error);
        this._facade.notificationShowNotification(MessageUtils.ANIMAL_UPDATE_FAIL + error.error[0].message, NotificationType.FAIL);
      }
    });
  }
}