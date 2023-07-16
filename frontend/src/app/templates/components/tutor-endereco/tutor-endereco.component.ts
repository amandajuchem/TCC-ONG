import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Endereco } from 'src/app/entities/endereco';
import { Tutor } from 'src/app/entities/tutor';
import { NotificationType } from 'src/app/enums/notification-type';
import { NotificationService } from 'src/app/services/notification.service';
import { TutorService } from 'src/app/services/tutor.service';
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
      rua: [tutor.endereco?.rua, Validators.required],
      numeroResidencia: [tutor.endereco?.numeroResidencia, Validators.required],
      bairro: [tutor.endereco?.bairro, Validators.required],
      complemento: [tutor.endereco?.complemento, Validators.nullValidator],
      cidade: [tutor.endereco?.cidade, Validators.required],
      estado: [tutor.endereco?.estado, Validators.required],
      cep: [tutor.endereco?.cep, Validators.required],
    });

    this.form.disable();
  }

  cancel() {
    this.buildForm(this.tutor);
  }

  submit() {
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
