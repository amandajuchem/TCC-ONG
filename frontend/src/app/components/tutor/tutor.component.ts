import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Tutor } from 'src/app/entities/tutor';
import { NotificationType } from 'src/app/enums/notification-type';
import { NotificationService } from 'src/app/services/notification.service';
import { TutorService } from 'src/app/services/tutor.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-tutor',
  templateUrl: './tutor.component.html',
  styleUrls: ['./tutor.component.sass'],
})
export class TutorComponent implements OnInit {
  isLoading!: boolean;
  tutor!: Tutor | null;

  constructor(
    private _activatedRoute: ActivatedRoute,
    private _notificationService: NotificationService,
    private _tutorService: TutorService
  ) {}

  ngOnInit(): void {
    this.isLoading = true;

    this._activatedRoute.params.subscribe({
      next: (params: any) => {
        if (params && params.id) {
          if (params.id.includes('cadastro')) {
            this._tutorService.set(null);
            this.isLoading = false;
          } else {
            this._tutorService.findById(params.id).subscribe({
              next: (tutor) => {
                this._tutorService.set(tutor);
                this.isLoading = false;
              },

              error: (error) => {
                console.error(error);
                this.isLoading = false;
                this._notificationService.show(
                  MessageUtils.TUTOR.GET_FAIL + MessageUtils.getMessage(error),
                  NotificationType.FAIL
                );
              },
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
