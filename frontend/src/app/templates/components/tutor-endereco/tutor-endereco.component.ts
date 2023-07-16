import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Endereco } from 'src/app/entities/endereco';
import { Tutor } from 'src/app/entities/tutor';
import { NotificationType } from 'src/app/enums/notification-type';
import { NotificationService } from 'src/app/services/notification.service';
import { TutorService } from 'src/app/services/tutor.service';
import { FormUtils } from 'src/app/utils/form-utils';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-tutor-endereco',
  templateUrl: './tutor-endereco.component.html',
  styleUrls: ['./tutor-endereco.component.sass'],
})
export class TutorEnderecoComponent implements OnInit {
  form!: FormGroup;
  tutor!: Tutor;

  constructor(
    private _formBuilder: FormBuilder,
    private _notificationService: NotificationService,
    private _tutorService: TutorService
  ) {}

  ngOnInit(): void {
    this._tutorService.get().subscribe({
      next: (tutor) => {
        if (tutor) {
          this.tutor = tutor;
          this.buildForm(tutor);
        }
      },
    });
  }

  buildForm(tutor: Tutor) {
    this.form = this._formBuilder.group({
      id: [tutor.endereco?.id, Validators.nullValidator],
      rua: [
        tutor.endereco?.rua,
        [Validators.required, Validators.maxLength(100)],
      ],
      numeroResidencia: [
        tutor.endereco?.numeroResidencia,
        [Validators.required, Validators.maxLength(10)],
      ],
      bairro: [
        tutor.endereco?.bairro,
        [Validators.required, Validators.maxLength(50)],
      ],
      complemento: [tutor.endereco?.complemento, [Validators.maxLength(100)]],
      cidade: [
        tutor.endereco?.cidade,
        [Validators.required, Validators.maxLength(100)],
      ],
      estado: [
        tutor.endereco?.estado,
        [Validators.required, Validators.maxLength(25)],
      ],
      cep: [
        tutor.endereco?.cep,
        [Validators.required, Validators.maxLength(8)],
      ],
    });

    this.form.disable();
  }

  cancel() {
    this.buildForm(this.tutor);
  }

  getErrorMessage(controlName: string) {
    return FormUtils.getErrorMessage(this.form, controlName);
  }

  hasError(controlName: string) {
    return FormUtils.hasError(this.form, controlName);
  }

  submit() {
    if (this.form.valid) {
      const endereco: Endereco = Object.assign({}, this.form.getRawValue());

      this.tutor.endereco = endereco;
      this.tutor.adocoes = [];
      this.tutor.observacoes = [];

      this._tutorService.update(this.tutor, null).subscribe({
        next: (tutor) => {
          this._tutorService.set(tutor);
          this._notificationService.show(
            MessageUtils.TUTOR.UPDATE_SUCCESS,
            NotificationType.SUCCESS
          );
        },

        error: (error) => {
          console.error(error);
          this._notificationService.show(
            MessageUtils.TUTOR.UPDATE_FAIL + MessageUtils.getMessage(error),
            NotificationType.FAIL
          );
        },
      });
    }
  }
}
