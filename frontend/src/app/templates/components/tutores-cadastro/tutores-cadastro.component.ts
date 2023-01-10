import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Tutor } from 'src/app/entities/tutor';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { environment } from 'src/environments/environment';

import { SelecionarImagemComponent } from '../selecionar-imagem/selecionar-imagem.component';

@Component({
  selector: 'app-tutores-cadastro',
  templateUrl: './tutores-cadastro.component.html',
  styleUrls: ['./tutores-cadastro.component.sass']
})
export class TutoresCadastroComponent implements OnInit {

  apiURL!: string;
  form!: FormGroup;
  foto!: any;
  fotoToSave!: any;
  fotoToDelete!: any;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private dialog: MatDialog,
    private dialogRef: MatDialogRef<TutoresCadastroComponent>,
    private facade: FacadeService,
    private formBuilder: FormBuilder
  ) { }

  ngOnInit(): void {
    
    this.apiURL = environment.apiURL;

    if (this.data.tutor) {
      
      this.buildForm(this.data.tutor);
      
      if (this.data.tutor.foto) {
        this.foto = { id: this.data.tutor.foto.id, nome: this.data.tutor.foto.nome, salvo: true};
      }
    }

    else {
      this.buildForm(null);
    }
  }
  
  addFoto() {

    this.dialog.open(SelecionarImagemComponent, {
      data: {
        multiple: false
      },
      width: '100%'
    })
    .afterClosed().subscribe(result => {

      if (result && result.status) {

        this.facade.imagensToBase64(result.images[0])?.then(data => {

          if (this.foto && this.foto.salvo) {
            this.fotoToDelete = this.foto;
          }

          let imagem = { id: new Date().getTime(), data: data, nome: null, salvo: false };

          this.foto = imagem;
          this.fotoToSave = result.images[0];
        });
      }
    });
  }

  buildForm(tutor: Tutor | null) {

    this.form = this.formBuilder.group({
      id: [tutor?.id, Validators.nullValidator],
      nome: [tutor?.nome, Validators.required],
      cpf: [tutor?.cpf, Validators.required],
      rg: [tutor?.rg, Validators.nullValidator],
      telefone: [tutor?.telefone, Validators.required],
      situacao: [tutor?.situacao, Validators.required],

      endereco: this.formBuilder.group({
        id: [tutor?.endereco.id, Validators.nullValidator],
        rua: [tutor?.endereco.rua, Validators.required],
        numeroResidencia: [tutor?.endereco.numeroResidencia, Validators.required],
        bairro: [tutor?.endereco.bairro, Validators.required],
        complemento: [tutor?.endereco.complemento, Validators.nullValidator],
        cidade: [tutor?.endereco.cidade, Validators.required],
        estado: [tutor?.endereco.estado, Validators.required],
        cep: [tutor?.endereco.cep, Validators.required]
      })
    });
  }

  removeFoto() {

    if (this.foto && this.foto.salvo) {
      this.fotoToDelete = this.foto;
    }

    this.foto = null;
    this.form.get('foto')?.patchValue(null);
  }

  submit() {

    let tutor: Tutor = Object.assign({}, this.form.getRawValue());

    if (tutor.id) {
      
      this.facade.tutoresUpdate(tutor, this.fotoToSave, this.fotoToDelete ? this.fotoToDelete.id : null).subscribe({

        complete: () => {
          this.facade.notificationsShowNotification(MessageUtils.TUTORES_UPDATE_SUCCESS, NotificationType.SUCCESS);
          this.dialogRef.close({status: true});
        },
  
        error: (error) => {
          console.error(error);
          this.facade.notificationsShowNotification(MessageUtils.TUTORES_UPDATE_FAIL + error.error[0].message, NotificationType.FAIL);
        }
      });
    }

    else {

      this.facade.tutoresSave(tutor, this.fotoToSave).subscribe({

        complete: () => {
          this.facade.notificationsShowNotification(MessageUtils.TUTORES_SAVE_SUCCESS, NotificationType.SUCCESS);
          this.dialogRef.close({status: true});
        },
  
        error: (error) => {
          console.error(error);
          this.facade.notificationsShowNotification(MessageUtils.TUTORES_SAVE_FAIL + error.error[0].message, NotificationType.FAIL);
        }
      });
    }
  } 
}