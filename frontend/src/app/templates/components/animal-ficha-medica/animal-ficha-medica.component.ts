import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Animal } from 'src/app/entities/animal';
import { FichaMedica } from 'src/app/entities/ficha-medica';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
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
    private _facade: FacadeService,
    private _formBuilder: FormBuilder
  ) { }

  ngOnInit(): void {
    
    this._facade.animalGet().subscribe({

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
      id: [animal.fichaMedica.id, Validators.nullValidator],
      comorbidades: [animal.fichaMedica.comorbidades, Validators.required],
      castrado: [animal.fichaMedica.castrado, Validators.required]
    });

    this.form.disable();
  }

  cancel() {
    this.buildForm(this.animal);
  }

  submit() {

    let fichaMedica: FichaMedica = Object.assign({}, this.form.getRawValue());
    this.animal.fichaMedica = fichaMedica;

    this._facade.animalUpdate(this.animal, null, null).subscribe({

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
