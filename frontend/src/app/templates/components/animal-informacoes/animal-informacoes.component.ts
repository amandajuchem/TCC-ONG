import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Animal } from 'src/app/entities/animal';
import { User } from 'src/app/entities/user';
import { NotificationType } from 'src/app/enums/notification-type';
import { AnimalService } from 'src/app/services/animal.service';
import { AuthService } from 'src/app/services/auth.service';
import { ImagemService } from 'src/app/services/imagem.service';
import { NotificationService } from 'src/app/services/notification.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { environment } from 'src/environments/environment';

import { AnimalExcluirComponent } from '../animal-excluir/animal-excluir.component';
import { SelecionarImagemComponent } from '../selecionar-imagem/selecionar-imagem.component';

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
  user!: User;

  constructor(
    private _animalService: AnimalService,
    private _authService: AuthService,
    private _dialog: MatDialog,
    private _formBuilder: FormBuilder,
    private _imagemService: ImagemService,
    private _router: Router,
    private _notificationService: NotificationService
  ) { }

  ngOnInit(): void {
    
    this.apiURL = environment.apiURL;
    this.user = this._authService.getCurrentUser();

    this._animalService.get().subscribe({

      next: (animal) => {
          
        if (animal) {
          this.animal = animal;
          this.buildForm(animal);

          if (animal.foto) {
            this.foto = { id: animal.foto.id, nome: animal.foto.nome, salvo: true};
          } else {
            this.foto = null;
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

        this._imagemService.toBase64(result.images[0])?.then(data => {

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
      situacao: [animal.situacao, Validators.required],
      foto: [animal.foto, Validators.nullValidator],
      fichaMedica: [animal.fichaMedica, Validators.nullValidator],
      adocoes: [animal.adocoes, Validators.nullValidator]
    });

    this.form.disable();
  }

  cancel() {
    
    this.buildForm(this.animal);

    if (this.animal.foto) {
      this.foto = { id: this.animal.foto.id, nome: this.animal.foto.nome, salvo: true};
    } else {
      this.foto = null;
    }
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
    this.foto = null;
    this.form.get('foto')?.patchValue(null);
  }

  submit() {

    let animal: Animal = Object.assign({}, this.form.getRawValue());

    this._animalService.update(animal, this.fotoToSave).subscribe({

      next: (animal) => {
        this.fotoToSave = null;
        this._notificationService.show(MessageUtils.ANIMAL_UPDATE_SUCCESS, NotificationType.SUCCESS);
        this._animalService.set(animal);
      },

      error: (error) => {
        console.error(error);
        this._notificationService.show(MessageUtils.ANIMAL_UPDATE_FAIL + error.error[0].message, NotificationType.FAIL);
      }
    });
  }
}