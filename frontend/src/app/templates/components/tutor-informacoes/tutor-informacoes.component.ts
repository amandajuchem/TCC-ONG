import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Tutor } from 'src/app/entities/tutor';
import { User } from 'src/app/entities/user';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { environment } from 'src/environments/environment';

import { SelecionarImagemComponent } from '../selecionar-imagem/selecionar-imagem.component';
import { TutorExcluirComponent } from '../tutor-excluir/tutor-excluir.component';

@Component({
  selector: 'app-tutor-informacoes',
  templateUrl: './tutor-informacoes.component.html',
  styleUrls: ['./tutor-informacoes.component.sass']
})
export class TutorInformacoesComponent implements OnInit {

  apiURL!: string;
  form!: FormGroup;
  foto!: any;
  fotoToSave!: any;
  fotoToDelete!: any;
  tutor!: Tutor;
  user!: User;

  constructor(
    private _dialog: MatDialog,
    private _facade: FacadeService,
    private _formBuilder: FormBuilder,
    private _router: Router
  ) { }

  ngOnInit(): void {
    
    this.apiURL = environment.apiURL;
    this.user = this._facade.authGetCurrentUser();

    this._facade.tutorGet().subscribe({

      next: (tutor) => {
          
        if (tutor) {
          this.tutor = tutor;
          this.buildForm(tutor);

          if (tutor.foto) {
            this.foto = { id: tutor.foto.id, nome: tutor.foto.nome, salvo: true};
          }
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

        this._facade.imagemToBase64(result.images[0])?.then(data => {

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

  buildForm(tutor: Tutor) {

    this.form = this._formBuilder.group({
      id: [tutor.id, Validators.nullValidator],
      nome: [tutor.nome, Validators.required],
      cpf: [tutor.cpf, Validators.required],
      rg: [tutor.rg, Validators.nullValidator],
      telefone: [tutor.telefone, Validators.required],
      situacao: [tutor.situacao, Validators.required],
      foto: [tutor.foto, Validators.nullValidator],
      endereco: [tutor.endereco, Validators.nullValidator]
    });

    this.form.disable();
  }

  cancel() {

    this.buildForm(this.tutor);

    if (this.tutor.foto) {
      this.foto = { id: this.tutor.foto.id, nome: this.tutor.foto.nome, salvo: true};
    } else {
      this.foto = null;
    }
  }

  delete() {

    this._dialog.open(TutorExcluirComponent, {
      data: {
        tutor: this.tutor
      },
      width: '100%'
    })
    .afterClosed().subscribe({

      next: (result) => {
        
        if (result) {
          this._router.navigate([this.user.role.toLowerCase() + '/tutores']);
        }
      }
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

    this._facade.tutorUpdate(tutor, this.fotoToSave, this.fotoToDelete?.id).subscribe({

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