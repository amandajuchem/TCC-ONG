import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Tutor } from 'src/app/entities/tutor';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-tutor',
  templateUrl: './tutor.component.html',
  styleUrls: ['./tutor.component.sass']
})
export class TutorComponent implements OnInit {

  currentUser!: any;
  tutor!: Tutor;

  constructor(
    private _activatedRoute: ActivatedRoute,
    private _facade: FacadeService
  ) { }

  ngOnInit(): void {
    
    this.currentUser = this._facade.authGetCurrentUser();

    this._activatedRoute.params.subscribe({

      next: (x: any) => {
          
        if (x && x.id) {
          
          this._facade.tutorFindById(x.id).subscribe({
            
            next: (tutor) => {
              this._facade.tutorSet(tutor);
            },

            error: (error) => {
              console.error(error);
              this._facade.notificationShowNotification(MessageUtils.TUTOR_GET_FAIL, NotificationType.FAIL); 
            }
          });
        }
      },
    });

    this._facade.tutorGet().subscribe({

      next: (tutor) => {
        
        if (tutor) {
          this.tutor = tutor;
        }
      },
    });
  }
}