import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Animal } from 'src/app/entities/animal';
import { FichaMedica } from 'src/app/entities/ficha-medica';
import { NotificationType } from 'src/app/enums/notification-type';
import { AnimalService } from 'src/app/services/animal.service';
import { NotificationService } from 'src/app/services/notification.service';
import { RedirectService } from 'src/app/services/redirect.service';
import { FormUtils } from 'src/app/utils/form-utils';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-animal-ficha-medica',
  templateUrl: './animal-ficha-medica.component.html',
  styleUrls: ['./animal-ficha-medica.component.sass'],
})
export class AnimalFichaMedicaComponent implements OnInit {
  animal!: Animal;
  form!: FormGroup;

  constructor(
    private _animalService: AnimalService,
    private _formBuilder: FormBuilder,
    private _notificationService: NotificationService,
    private _redirectService: RedirectService
  ) {}

  ngOnInit(): void {
    this._animalService.get().subscribe({
      next: (animal) => {
        if (animal) {
          this.animal = animal;
          this.buildForm(animal);
        }
      },
    });
  }

  buildForm(animal: Animal) {
    this.form = this._formBuilder.group({
      id: [animal.fichaMedica?.id, Validators.nullValidator],
      comorbidades: [animal.fichaMedica?.comorbidades, Validators.required],
      castrado: [animal.fichaMedica?.castrado, Validators.required],
    });
  }

  getErrorMessage(controlName: string) {
    return FormUtils.getErrorMessage(this.form, controlName);
  }

  hasError(controlName: string) {
    return FormUtils.hasError(this.form, controlName);
  }

  redirectToAnimalList() {
    this._redirectService.toAnimalList();
  }

  submit() {
    if (this.form.valid) {
      const fichaMedica: FichaMedica = Object.assign(
        {},
        this.form.getRawValue()
      );

      this.animal.fichaMedica = fichaMedica;
      this.animal.adocoes = [];

      this._animalService.update(this.animal, null).subscribe({
        next: (animal) => {
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
    }
  }
}
