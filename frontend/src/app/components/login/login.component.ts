import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NotificationType } from 'src/app/enums/notification-type';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { NotificationService } from 'src/app/services/notification.service';
import { RedirectService } from 'src/app/services/redirect.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass'],
})
export class LoginComponent implements OnInit {
  form!: FormGroup;
  hide!: boolean;

  constructor(
    private _authenticationService: AuthenticationService,
    private _formBuilder: FormBuilder,
    private _notificationService: NotificationService,
    private _redirectService: RedirectService
  ) {}

  ngOnInit(): void {
    this.hide = true;

    if (this._authenticationService.isAuthenticated()) {
      this.redirect();
    } else {
      this.buildForm();
    }
  }

  buildForm() {
    this.form = this._formBuilder.group({
      username: [null, Validators.required],
      password: [null, Validators.required],
    });
  }

  redirect() {
    this._redirectService.toPainel();
  }

  submit() {
    const user = Object.assign({}, this.form.value);

    this._authenticationService.login(user).subscribe({
      next: (authentication) => {
        this._authenticationService.setAuthentication(authentication);
        this.redirect();
      },

      error: (error) => {
        console.error(error);
        this._notificationService.show(
          MessageUtils.getMessage(error),
          NotificationType.FAIL
        );
      },
    });
  }
}
