import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Usuario } from 'src/app/entities/usuario';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { environment } from 'src/environments/environment';
import { SelecionarImagemComponent } from '../selecionar-imagem/selecionar-imagem.component';

@Component({
  selector: 'app-usuarios-cadastro',
  templateUrl: './usuarios-cadastro.component.html',
  styleUrls: ['./usuarios-cadastro.component.sass']
})
export class UsuariosCadastroComponent implements OnInit {
  
  apiURL!: string;
  form!: FormGroup;
  foto!: any;
  fotoToSave!: any;
  fotoToDelete!: any;
  hide!: boolean;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private dialog: MatDialog,
    private dialogRef: MatDialogRef<UsuariosCadastroComponent>,
    private facade: FacadeService,
    private formBuilder: FormBuilder
  ) { }

  ngOnInit(): void {
    
    this.apiURL = environment.apiURL;
    this.hide = true;

    if (this.data.usuario) {

      this.buildForm(this.data.usuario);
    
      if (this.data.usuario.foto) {
        this.foto = { id: this.data.usuario.foto.id, nome: this.data.usuario.foto.nome, salvo: true};
      }

    } else {
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

  buildForm(usuario: Usuario | null) {

    this.form = this.formBuilder.group({
      id: [usuario?.id, Validators.nullValidator],
      nome: [usuario?.nome, Validators.required],
      cpf: [usuario?.cpf, Validators.required],
      senha: [usuario?.senha, Validators.required],
      setor: [usuario?.setor, Validators.required],
      status: [usuario?.status, Validators.required],
      foto: [usuario?.foto, Validators.nullValidator]
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

    let usuario: Usuario = Object.assign({}, this.form.value);

    if (usuario.id) {
      
      this.facade.usuariosUpdate(usuario, this.fotoToSave, this.fotoToDelete ? this.fotoToDelete.id : null).subscribe({

        complete: () => {
          this.facade.notificationsShowNotification(MessageUtils.USUARIO_UPDATE_SUCCESS, NotificationType.SUCCESS);
          this.dialogRef.close({status: true});
        },
  
        error: (error) => {
          console.error(error);
          this.facade.notificationsShowNotification(MessageUtils.USUARIO_UPDATE_FAIL + error.error[0].message, NotificationType.FAIL);   
        
        }
      });
    }

    else {

      this.facade.usuariosSave(usuario, this.fotoToSave, this.fotoToDelete ? this.fotoToDelete.id : null).subscribe({

        complete: () => {
          this.facade.notificationsShowNotification(MessageUtils.USUARIO_SAVE_SUCCESS, NotificationType.SUCCESS);
          this.dialogRef.close({status: true});
        },
  
        error: (error) => {
          console.error(error);
          this.facade.notificationsShowNotification(MessageUtils.USUARIO_SAVE_FAIL + error.error[0].message, NotificationType.FAIL);
        }
      });
    }
  }
}