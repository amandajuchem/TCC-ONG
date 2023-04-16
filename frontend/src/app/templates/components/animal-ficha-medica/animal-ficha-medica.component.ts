import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Animal } from 'src/app/entities/animal';
import { FichaMedica } from 'src/app/entities/ficha-medica';
import { NotificationType } from 'src/app/enums/notification-type';
import { AnimalService } from 'src/app/services/animal.service';
import { NotificationService } from 'src/app/services/notification.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-animal-ficha-medica',
  templateUrl: './animal-ficha-medica.component.html',
  styleUrls: ['./animal-ficha-medica.component.sass']
})
export class AnimalFichaMedicaComponent implements OnInit {

  animal!: Animal;
  form!: FormGroup;

  constructor(
    private _animalService: AnimalService,
    private _formBuilder: FormBuilder,
    private _notificationService: NotificationService
  ) { }

  ngOnInit(): void {
    
    this._animalService.get().subscribe({

      next: (animal) => {
          
        if (animal) {
          this.animal = animal;
          this.buildForm(animal);
        }
      }
    });
  }

  buildForm(animal: Animal) {

    this.form = this._formBuilder.group({
      id: [animal.fichaMedica?.id, Validators.nullValidator],
      comorbidades: [animal.fichaMedica?.comorbidades, Validators.required],
      castrado: [animal.fichaMedica?.castrado, Validators.required]
    });

    this.form.disable();
  }

  cancel() {
    this.buildForm(this.animal);
  }

  submit() {

    const fichaMedica: FichaMedica = Object.assign({}, this.form.getRawValue());

    this.animal.fichaMedica = fichaMedica;
    this.animal.adocoes = [];

    this._animalService.update(this.animal, null).subscribe({

      next: (animal) => {
        this._animalService.set(animal);
        this._notificationService.show(MessageUtils.ANIMAL_UPDATE_SUCCESS, NotificationType.SUCCESS);
      },

      error: (error) => {
        console.error(error);
        this._notificationService.show(MessageUtils.ANIMAL_UPDATE_FAIL + error.error[0].message, NotificationType.FAIL);
      }
    });
  }
}