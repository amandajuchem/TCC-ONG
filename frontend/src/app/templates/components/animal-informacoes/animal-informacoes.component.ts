import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Animal } from 'src/app/entities/animal';
import { Authentication } from 'src/app/entities/authentication';
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
  authentication!: Authentication;
  apiURL!: string;
  form!: FormGroup;
  foto!: any;

  constructor(
    private _animalService: AnimalService,
    private _authService: AuthService,
    private _dialog: MatDialog,
    private _formBuilder: FormBuilder,
    private _imagemService: ImagemService,
    private _notificationService: NotificationService,
    private _router: Router
  ) { }

  ngOnInit(): void {
    
    this.apiURL = environment.apiURL;
    this.authentication = this._authService.getAuthentication();

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
        }

        else {
          this.buildForm(null);
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
          this.foto = { id: new Date().getTime(), data: data, file: result.images[0] };
        });
      }
    });
  }

  buildForm(animal: Animal | null) {

    this.form = this._formBuilder.group({
      id: [animal?.id, Validators.nullValidator],
      nome: [animal?.nome, Validators.required],
      idade: [animal?.idade, Validators.required],
      sexo: [animal?.sexo, Validators.required],
      cor: [animal?.cor, Validators.nullValidator],
      raca: [animal?.raca, Validators.nullValidator],
      especie: [animal?.especie, Validators.required],
      porte: [animal?.porte, Validators.nullValidator],
      situacao: [animal?.situacao, Validators.required],
      foto: [animal?.foto, Validators.nullValidator],
      fichaMedica: [animal?.fichaMedica, Validators.nullValidator],
      adocoes: [[], Validators.nullValidator]
    });

    animal ? this.form.disable() : this.form.enable();
  }

  cancel() {
    this.foto = this.animal?.foto ? { id: this.animal?.foto.id, nome: this.animal?.foto.nome } : null;
    this.animal ? this.buildForm(this.animal) : this._router.navigate(['/' + this.authentication.role.toLowerCase() + '/animais']);
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
          this._router.navigate([this.authentication.role.toLowerCase() + '/animais']);
        }
      }
    });
  }

  removeFoto() {
    this.foto = null;
    this.form.get('foto')?.patchValue(null);
  }

  submit() {

    const animal: Animal = Object.assign({}, this.form.getRawValue());
    const imagem: File = this.foto?.file;

    if (animal.id) {

      this._animalService.update(animal, imagem).subscribe({

        next: (animal) => {
          this.foto ? this.foto.file = null : null;
          this._animalService.set(animal);
          this._notificationService.show(MessageUtils.ANIMAL_UPDATE_SUCCESS, NotificationType.SUCCESS);
        },
  
        error: (error) => {
          console.error(error);
          this._notificationService.show(MessageUtils.ANIMAL_UPDATE_FAIL + error.error[0].message, NotificationType.FAIL);
        }
      });
    }

    else {

      this._animalService.save(animal, imagem).subscribe({

        next: (animal) => {
          this.foto ? this.foto.file = null : null;
          this._router.navigate(['/' + this.authentication.role.toLowerCase() + '/animais/' + animal.id]);
          this._notificationService.show(MessageUtils.ANIMAL_SAVE_SUCCESS, NotificationType.SUCCESS);
        },
  
        error: (error) => {
          console.error(error);
          this._notificationService.show(MessageUtils.ANIMAL_SAVE_FAIL + error.error[0].message, NotificationType.FAIL);
        }
      });
    }
  }
}