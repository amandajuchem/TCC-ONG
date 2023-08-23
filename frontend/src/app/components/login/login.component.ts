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

  async ngOnInit(): Promise<void> {
    this.hide = true;
    const isAuthenticated = await this._authenticationService.isAuthenticated();
    isAuthenticated ? this._redirectService.toPainel() : this.buildForm();
  }

  buildForm() {
    this.form = this._formBuilder.group({
      username: [null, Validators.required],
      password: [null, Validators.required],
    });
  }

  submit() {
    const user = Object.assign({}, this.form.value);

    this._authenticationService.token(user).subscribe({
      next: (authentication) => {
        this._authenticationService.setAuthentication(authentication);
        this._redirectService.toPainel();
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
