import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Animal } from 'src/app/entities/animal';
import { User } from 'src/app/entities/user';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';
import { environment } from 'src/environments/environment';

import { AnimalCadastroComponent } from '../animal-cadastro/animal-cadastro.component';

@Component({
  selector: 'app-animais',
  templateUrl: './animais.component.html',
  styleUrls: ['./animais.component.sass']
})
export class AnimaisComponent implements OnInit {
  
  animais!: Array<Animal>;
  animaisToShow!: Array<Animal>;
  apiURL!: string;
  user!: User;

  constructor(
    private _dialog: MatDialog,
    private _facade: FacadeService
  ) { }

  ngOnInit(): void {
    this.apiURL = environment.apiURL;
    this.user = this._facade.authGetCurrentUser();
    this.findAllAnimais();
  }

  add() {

    this._dialog.open(AnimalCadastroComponent, {
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

    this._facade.animalFindAll().subscribe({

      next: (animais) => {
        this.animais = animais.sort((a, b) => a.nome.toUpperCase() > b.nome.toUpperCase() ? 1 : -1);
        this.animaisToShow = this.animais;
      },

      error: (error) => {
        console.error(error);
        this._facade.notificationShowNotification(MessageUtils.ANIMAIS_GET_FAIL, NotificationType.FAIL); 
      }
    });
  }
}