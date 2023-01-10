import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Tutor } from 'src/app/entities/tutor';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { TutoresCadastroComponent } from '../tutores-cadastro/tutores-cadastro.component';
import { TutoresExcluirComponent } from '../tutores-excluir/tutores-excluir.component';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-tutores',
  templateUrl: './tutores.component.html',
  styleUrls: ['./tutores.component.sass']
})
export class TutoresComponent implements OnInit {
  
  apiURL!: string;
  currentUser: any;
  tutores!: Array<Tutor>;
  tutoresToShow!: Array<Tutor>;
  
  constructor(
    private dialog: MatDialog,
    private facade: FacadeService
  ) { }

  ngOnInit(): void {
    this.apiURL = environment.apiURL;
    this.currentUser = this.facade.authGetCurrentUser();
    this.findAllTutores();
  }

  add() {

    this.dialog.open(TutoresCadastroComponent, {
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

  delete(tutor: Tutor) {

    this.dialog.open(TutoresExcluirComponent, {
      data: {
        tutor: tutor
      },
      width: '100%'
    })
    .afterClosed().subscribe({

      next: (result) => {
        this.findAllTutores();   
      }
    });
  }

  filter(value: string) {
    value = value.toUpperCase();
    this.tutoresToShow = this.tutores.filter(t => t.nome.toUpperCase().includes(value) || t.cpf.toUpperCase().includes(value));
  }

  findAllTutores() {

    this.facade.tutoresFindAll().subscribe({

      next: (tutores) => {
        this.tutores = tutores;
        this.tutoresToShow = this.tutores;
      },

      error: (error) => {
        console.error(error);
        this.facade.notificationsShowNotification(MessageUtils.TUTORES_GET_FAIL, NotificationType.FAIL); 
      }
    });
  }

  update(tutor: Tutor) {

    this.dialog.open(TutoresCadastroComponent, {
      data: {
        tutor: tutor
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
}