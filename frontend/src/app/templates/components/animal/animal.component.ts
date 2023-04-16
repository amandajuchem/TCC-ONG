import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Animal } from 'src/app/entities/animal';
import { User } from 'src/app/entities/user';
import { NotificationType } from 'src/app/enums/notification-type';
import { AnimalService } from 'src/app/services/animal.service';
import { AuthService } from 'src/app/services/auth.service';
import { NotificationService } from 'src/app/services/notification.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-animal',
  templateUrl: './animal.component.html',
  styleUrls: ['./animal.component.sass']
})
export class AnimalComponent implements OnInit {

  animal!: Animal | null;
  user!: User;

  constructor(
    private _activatedRoute: ActivatedRoute,
    private _animalService: AnimalService,
    private _authService: AuthService,
    private _notificationService: NotificationService
  ) { }

  ngOnInit(): void {

    this.user = this._authService.getCurrentUser();

    this._activatedRoute.params.subscribe({

      next: (params: any) => {
          
        
        if (params && params.id) {
          
          if (params.id.includes('cadastro')) {
            this._animalService.set(null);
          }

          else {

            this._animalService.findById(params.id).subscribe({
            
              next: (animal) => {
                this._animalService.set(animal);
              },
  
              error: (error) => {
                console.error(error);
                this._notificationService.show(MessageUtils.ANIMAL_GET_FAIL, NotificationType.FAIL); 
              }
            });
          }
        }
      }
    });

    this._animalService.get().subscribe({

      next: (animal) => {
        this.animal = animal;
      }
    });
  }
}