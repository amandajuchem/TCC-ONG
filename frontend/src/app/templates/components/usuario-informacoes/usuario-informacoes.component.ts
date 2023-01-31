import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Usuario } from 'src/app/entities/usuario';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { SelecionarImagemComponent } from '../selecionar-imagem/selecionar-imagem.component';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-usuario-informacoes',
  templateUrl: './usuario-informacoes.component.html',
  styleUrls: ['./usuario-informacoes.component.sass']
})
export class UsuarioInformacoesComponent implements OnInit {

  apiURL!: string;
  form!: FormGroup;
  foto!: any;
  fotoToSave!: any;
  fotoToDelete!: any;
  hide!: boolean;
  user!: any;

  constructor(
    private _dialog: MatDialog,
    private _facade: FacadeService,
    private _formBuilder: FormBuilder
  ) { }

  ngOnInit(): void {
    
    this.apiURL = environment.apiURL;
    this.hide = true;
    this.user = this._facade.authGetCurrentUser();

    this._facade.usuarioGet().subscribe({

      next: (usuario) => {
          
        if (usuario) {
          this.buildForm(usuario);

          if (usuario.foto) {
            this.foto = { id: usuario.foto.id, nome: usuario.foto.nome, salvo: true};
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

  buildForm(usuario: Usuario) {

    this.form = this._formBuilder.group({
      id: [usuario.id, Validators.nullValidator],
      nome: [usuario.nome, Validators.required],
      cpf: [usuario.cpf, Validators.required],
      senha: [usuario.senha, Validators.required],
      setor: [{value: usuario.setor, disabled: usuario.cpf == this.user.username}, Validators.required],
      status: [{value: usuario.status, disabled: usuario.cpf == this.user.username}, Validators.required],
      foto: [usuario.foto, Validators.nullValidator]
    });

    this.form.disable();
  }

  removeFoto() {

    if (this.foto && this.foto.salvo) {
      this.fotoToDelete = this.foto;
    }

    this.foto = null;
    this.form.get('foto')?.patchValue(null);
  }

  submit() {

    let usuario: Usuario = Object.assign({}, this.form.getRawValue());

    this._facade.usuarioUpdate(usuario, this.fotoToSave, this.fotoToDelete?.id).subscribe({

      next: (usuario) => {
        this.fotoToSave = null;
        this.fotoToDelete = null;
        this._facade.notificationShowNotification(MessageUtils.USUARIO_UPDATE_SUCCESS, NotificationType.SUCCESS);
        this._facade.usuarioSet(usuario);
      },

      error: (error) => {
        console.error(error);
        this._facade.notificationShowNotification(MessageUtils.USUARIO_UPDATE_FAIL + error.error[0].message, NotificationType.FAIL);
      }
    });
  }
}