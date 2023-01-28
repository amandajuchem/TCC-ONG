import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Endereco } from 'src/app/entities/endereco';
import { Tutor } from 'src/app/entities/tutor';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-tutor-endereco',
  templateUrl: './tutor-endereco.component.html',
  styleUrls: ['./tutor-endereco.component.sass']
})
export class TutorEnderecoComponent implements OnInit {

  form!: FormGroup;
  tutor!: Tutor;

  constructor(
    private _facade: FacadeService,
    private _formBuilder: FormBuilder
  ) { }

  ngOnInit(): void {
    
    this._facade.tutorGet().subscribe({

      next: (tutor) => {
          
        if (tutor) {
          this.tutor = tutor;
          this.buildForm(tutor);
        }
      }
    });
  }

  buildForm(tutor: Tutor) {

    this.form = this._formBuilder.group({
      id: [tutor.endereco.id, Validators.nullValidator],
      rua: [tutor.endereco.rua, Validators.required],
      numeroResidencia: [tutor.endereco.numeroResidencia, Validators.required],
      bairro: [tutor.endereco.bairro, Validators.required],
      complemento: [tutor.endereco.complemento, Validators.nullValidator],
      cidade: [tutor.endereco.cidade, Validators.required],
      estado: [tutor.endereco.estado, Validators.required],
      cep: [tutor.endereco.cep, Validators.required]
    });

    this.form.disable();
  }

  cancel() {
    this.buildForm(this.tutor);
  }

  submit() {

    let endereco: Endereco = Object.assign({}, this.form.getRawValue());
    this.tutor.endereco = endereco;

    this._facade.tutorUpdate(this.tutor, null, null).subscribe({

      next: (tutor) => {
        this._facade.notificationShowNotification(MessageUtils.TUTOR_UPDATE_SUCCESS, NotificationType.SUCCESS);
        this._facade.tutorSet(tutor);
      },

      error: (error) => {
        console.error(error);
        this._facade.notificationShowNotification(MessageUtils.TUTOR_UPDATE_FAIL + error.error[0].message, NotificationType.FAIL);
      }
    });
  }
}
