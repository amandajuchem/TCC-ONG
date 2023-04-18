import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Tutor } from 'src/app/entities/tutor';
import { User } from 'src/app/entities/user';
import { NotificationType } from 'src/app/enums/notification-type';
import { AuthService } from 'src/app/services/auth.service';
import { NotificationService } from 'src/app/services/notification.service';
import { TutorService } from 'src/app/services/tutor.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-tutor',
  templateUrl: './tutor.component.html',
  styleUrls: ['./tutor.component.sass']
})
export class TutorComponent implements OnInit {

  user!: User;
  tutor!: Tutor | null;

  constructor(
    private _activatedRoute: ActivatedRoute,
    private _authService: AuthService,
    private _notificationService: NotificationService,
    private _tutorService: TutorService
  ) { }

  ngOnInit(): void {
    
    this.user = this._authService.getCurrentUser();

    this._activatedRoute.params.subscribe({

      next: (params: any) => {
          
        if (params && params.id) {
          
          if (params.id.includes('cadastro')) {
            this._tutorService.set(null);
          }

          else {

            this._tutorService.findById(params.id).subscribe({
            
              next: (tutor) => {
                this._tutorService.set(tutor);
              },
  
              error: (error) => {
                console.error(error);
                this._notificationService.show(MessageUtils.TUTOR_GET_FAIL, NotificationType.FAIL); 
              }
            });
          }
        }
      },
    });

    this._tutorService.get().subscribe({

      next: (tutor) => {
        this.tutor = tutor;
      },
    });
  }
}