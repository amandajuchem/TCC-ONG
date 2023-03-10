import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Tutor } from 'src/app/entities/tutor';
import { NotificationType } from 'src/app/enums/notification-type';
import { ImagemService } from 'src/app/services/imagem.service';
import { NotificationService } from 'src/app/services/notification.service';
import { TutorService } from 'src/app/services/tutor.service';
import { MessageUtils } from 'src/app/utils/message-utils';

import { SelecionarImagemComponent } from '../selecionar-imagem/selecionar-imagem.component';

@Component({
  selector: 'app-tutor-cadastro',
  templateUrl: './tutor-cadastro.component.html',
  styleUrls: ['./tutor-cadastro.component.sass']
})
export class TutorCadastroComponent implements OnInit {

  form!: FormGroup;
  foto!: any;
  fotoToSave!: any;

  constructor(
    private _formBuilder: FormBuilder,
    private _dialog: MatDialog,
    private _dialogRef: MatDialogRef<TutorCadastroComponent>,
    private _imagemService: ImagemService,
    private _notificationService: NotificationService,
    private _tutorService: TutorService
  ) { }

  ngOnInit(): void {
    this.buildForm();
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

          let imagem = { id: new Date().getTime(), data: data };

          this.foto = imagem;
          this.fotoToSave = result.images[0];
        });
      }
    });
  }

  addTelefone() {

    this.telefones.push(this._formBuilder.group({
      id: [null, Validators.nullValidator],
      numero: [null, Validators.nullValidator]
    }));
  }

  buildForm() {

    this.form = this._formBuilder.group({
      id: [null, Validators.nullValidator],
      nome: [null, Validators.required],
      cpf: [null, Validators.required],
      rg: [null, Validators.nullValidator],
      situacao: [null, Validators.required],

      telefones: this._formBuilder.array([]),

      endereco: this._formBuilder.group({
        id: [null, Validators.nullValidator],
        rua: [null, Validators.required],
        numeroResidencia: [null, Validators.required],
        bairro: [null, Validators.required],
        complemento: [null, Validators.nullValidator],
        cidade: [null, Validators.required],
        estado: [null, Validators.required],
        cep: [null, Validators.required]
      })
    });
  }

  get telefones() {
    return this.form.controls['telefones'] as FormArray;
  }

  removeFoto() {
    this.foto = null;
    this.form.get('foto')?.patchValue(null);
  }

  removeTelefone(index: number) {
    this.telefones.removeAt(index);
  }

  submit() {

    let tutor: Tutor = Object.assign({}, this.form.getRawValue());

    this._tutorService.save(tutor, this.fotoToSave).subscribe({

      complete: () => {
        this._notificationService.show(MessageUtils.TUTOR_SAVE_SUCCESS, NotificationType.SUCCESS);
        this._dialogRef.close({status: true});
      },

      error: (error) => {
        console.error(error);
        this._notificationService.show(MessageUtils.TUTOR_SAVE_FAIL + error.error[0].message, NotificationType.FAIL);
      }
    });
  } 
}