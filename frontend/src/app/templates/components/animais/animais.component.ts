import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Animal } from 'src/app/entities/animal';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { environment } from 'src/environments/environment';

import { AnimaisCadastroComponent } from '../animais-cadastro/animais-cadastro.component';

@Component({
  selector: 'app-animais',
  templateUrl: './animais.component.html',
  styleUrls: ['./animais.component.sass']
})
export class AnimaisComponent implements OnInit {
  

  animais!: Array<Animal>;
  animaisToShow!: Array<Animal>;
  apiURL!: string;
  currentUser!: any;

  constructor(
    private dialog: MatDialog,
    private facade: FacadeService
  ) { }

  ngOnInit(): void {
    this.apiURL = environment.apiURL;
    this.currentUser = this.facade.authGetCurrentUser();
    this.findAllAnimais();
  }

  add() {

    this.dialog.open(AnimaisCadastroComponent, {
      data: {
        animal: null
      },
      width: '100%'
    })
    .afterClosed().subscribe({
      
      next: (result) => {
        
        if (result && result.status) {
          this.findAllAnimais();
        }
      }
    });
  }

  filter(value: string) {
    value = value.toUpperCase();
    this.animaisToShow = this.animais.filter(a => a.nome.toUpperCase().includes(value));
  }

  findAllAnimais() {

    this.facade.animaisFindAll().subscribe({

      next: (animais) => {
        this.animais = animais;
        this.animaisToShow = this.animais;
      },

      error: (error) => {
        console.error(error);
        this.facade.notificationsShowNotification(MessageUtils.ANIMAIS_GET_FAIL, NotificationType.FAIL); 
      }
    });
  }

  update(animal: Animal) {

    this.dialog.open(AnimaisCadastroComponent, {
      data: {
        animal: animal
      },
      width: '100%'
    })
    .afterClosed().subscribe({
      
      next: (result) => {
        
        if (result && result.status) {
          this.findAllAnimais();
        }
      }
    });
  }
}