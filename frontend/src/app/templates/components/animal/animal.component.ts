import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Animal } from 'src/app/entities/animal';
import { Authentication } from 'src/app/entities/authentication';
import { NotificationType } from 'src/app/enums/notification-type';
import { AnimalService } from 'src/app/services/animal.service';
import { AuthService } from 'src/app/services/auth.service';
import { NotificationService } from 'src/app/services/notification.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-animal',
  templateUrl: './animal.component.html',
  styleUrls: ['./animal.component.sass'],
})
export class AnimalComponent implements OnInit {
  animal!: Animal | null;
  authentication!: Authentication;
  isLoading!: boolean;

  constructor(
    private _activatedRoute: ActivatedRoute,
    private _animalService: AnimalService,
    private _authService: AuthService,
    private _notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.authentication = this._authService.getAuthentication();
    this.isLoading = true;

    this._activatedRoute.params.subscribe({
      next: (params: any) => {
        if (params && params.id) {
          if (params.id.includes('cadastro')) {
            this._animalService.set(null);
            this.isLoading = false;
          } else {
            this._animalService.findById(params.id).subscribe({
              next: (animal) => {
                this._animalService.set(animal);
                this.isLoading = false;
              },

              error: (error) => {
                console.error(error);
                this.isLoading = false;
                this._notificationService.show(
                  MessageUtils.ANIMAL.GET_FAIL + MessageUtils.getMessage(error),
                  NotificationType.FAIL
                );
              },
            });
          }
        }
      },
    });

    this._animalService.get().subscribe({
      next: (animal) => {
        this.animal = animal;
      },
    });
  }
}
