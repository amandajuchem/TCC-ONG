import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Tutor } from 'src/app/entities/tutor';
import { NotificationType } from 'src/app/enums/notification-type';
import { NotificationService } from 'src/app/services/notification.service';
import { RedirectService } from 'src/app/services/redirect.service';
import { TutorService } from 'src/app/services/tutor.service';
import { FormUtils } from 'src/app/utils/form-utils';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-tutor-informacoes',
  templateUrl: './tutor-informacoes.component.html',
  styleUrls: ['./tutor-informacoes.component.sass'],
})
export class TutorInformacoesComponent implements OnInit {
  form!: FormGroup;
  tutor!: Tutor;

  constructor(
    private _formBuilder: FormBuilder,
    private _notificationService: NotificationService,
    private _redirectService: RedirectService,
    private _tutorService: TutorService
  ) {}

  ngOnInit(): void {
    this._tutorService.get().subscribe({
      next: (tutor) => {
        if (tutor) {
          this.tutor = tutor;
          this.buildForm(tutor);
        } else {
          this.buildForm(null);
        }
      },
    });
  }

  addTelefone() {
    this.telefones.push(
      this._formBuilder.group({
        id: [null, Validators.nullValidator],
        numero: [null, Validators.nullValidator],
      })
    );
  }

  buildForm(tutor: Tutor | null) {
    this.form = this._formBuilder.group({
      id: [tutor?.id, Validators.nullValidator],
      nome: [tutor?.nome, [Validators.required, Validators.maxLength(100)]],
      cpf: [tutor?.cpf, [Validators.required, Validators.maxLength(11)]],
      rg: [tutor?.rg, [Validators.maxLength(13)]],
      situacao: [
        tutor?.situacao,
        [Validators.required, Validators.maxLength(10)],
      ],

      telefones: this._formBuilder.array(
        tutor?.telefones
          ? tutor.telefones.map((telefone) =>
              this._formBuilder.group({
                id: [telefone.id, Validators.nullValidator],
                numero: [
                  telefone.numero,
                  [Validators.required, Validators.maxLength(11)],
                ],
              })
            )
          : []
      ),

      endereco: [tutor?.endereco, Validators.nullValidator],
      adocoes: [[], Validators.nullValidator],
      observacoes: [[], Validators.nullValidator],
    });
  }

  getErrorMessage(controlName: string) {
    return FormUtils.getErrorMessage(this.form, controlName);
  }

  hasError(controlName: string) {
    return FormUtils.hasError(this.form, controlName);
  }

  get telefones() {
    return this.form.controls['telefones'] as FormArray;
  }

  removeTelefone(index: number) {
    this.telefones.removeAt(index);
  }

  redirectToTutorList() {
    this._redirectService.toTutorList();
  }

  submit() {
    if (this.form.valid) {
      const tutor: Tutor = Object.assign({}, this.form.getRawValue());

      if (tutor.id) {
        this._tutorService.update(tutor).subscribe({
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
      } else {
        this._tutorService.save(tutor).subscribe({
          next: (tutor) => {
            this._notificationService.show(
              MessageUtils.TUTOR.SAVE_SUCCESS,
              NotificationType.SUCCESS
            );
            this._redirectService.toTutor(tutor.id);
          },

          error: (error) => {
            console.error(error);
            this._notificationService.show(
              MessageUtils.TUTOR.SAVE_FAIL + MessageUtils.getMessage(error),
              NotificationType.FAIL
            );
          },
        });
      }
    }
  }
}
