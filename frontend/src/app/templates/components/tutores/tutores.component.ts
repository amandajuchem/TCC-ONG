import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Tutor } from 'src/app/entities/tutor';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { environment } from 'src/environments/environment';

import { TutorCadastroComponent } from '../tutor-cadastro/tutor-cadastro.component';
import { User } from 'src/app/entities/user';

@Component({
  selector: 'app-tutores',
  templateUrl: './tutores.component.html',
  styleUrls: ['./tutores.component.sass']
})
export class TutoresComponent implements OnInit {
  
  apiURL!: string;
  tutores!: Array<Tutor>;
  tutoresToShow!: Array<Tutor>;
  user!: User;
  
  constructor(
    private _dialog: MatDialog,
    private _facade: FacadeService
  ) { }

  ngOnInit(): void {
    this.apiURL = environment.apiURL;
    this.user = this._facade.authGetCurrentUser();
    this.findAllTutores();
  }

  add() {

    this._dialog.open(TutorCadastroComponent, {
      data: {
        tutor: null
      },
      width: '100%'
    })
    .afterClosed().subscribe({
      
      next: (result) => {
        
        if (result && result.status) {
          this.findAllTutores();
        }
      }
    });
  }

  filter(value: string) {
    value = value.toUpperCase();
    this.tutoresToShow = this.tutores.filter(t => t.nome.toUpperCase().includes(value) || t.cpf.toUpperCase().includes(value));
  }

  findAllTutores() {

    this._facade.tutorFindAll().subscribe({

      next: (tutores) => {
        this.tutores = tutores.sort((a, b) => a.nome.toUpperCase() > b.nome.toUpperCase() ? 1 : -1);
        this.tutoresToShow = this.tutores;
      },

      error: (error) => {
        console.error(error);
        this._facade.notificationShowNotification(MessageUtils.TUTORES_GET_FAIL, NotificationType.FAIL); 
      }
    });
  }
}