import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Animal } from 'src/app/entities/animal';
import { User } from 'src/app/entities/user';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-animal',
  templateUrl: './animal.component.html',
  styleUrls: ['./animal.component.sass']
})
export class AnimalComponent implements OnInit {

  animal!: Animal;
  user!: User;

  constructor(
    private _activatedRoute: ActivatedRoute,
    private _facade: FacadeService
  ) { }

  ngOnInit(): void {

    this.user = this._facade.authGetCurrentUser();

    this._activatedRoute.params.subscribe({

      next: (x: any) => {
          
        if (x && x.id) {
          
          this._facade.animalFindById(x.id).subscribe({
            
            next: (animal) => {
              this._facade.animalSet(animal);
            },

            error: (error) => {
              console.error(error);
              this._facade.notificationShowNotification(MessageUtils.ANIMAL_GET_FAIL, NotificationType.FAIL); 
            }
          });
        }
      },
    });

    this._facade.animalGet().subscribe({

      next: (animal) => {
        
        if (animal) {
          this.animal = animal;
        }
      },
    });
  }
}