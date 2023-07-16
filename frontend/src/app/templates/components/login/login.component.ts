import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NotificationType } from 'src/app/enums/notification-type';
import { AuthService } from 'src/app/services/auth.service';
import { NotificationService } from 'src/app/services/notification.service';
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
    private _authService: AuthService,
    private _formBuilder: FormBuilder,
    private _notificationService: NotificationService,
    private _router: Router
  ) {}

  ngOnInit(): void {
    this.hide = true;

    if (this._authService.isAuthenticated()) {
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
    const authentication = this._authService.getAuthentication();
    this._router.navigate([
      '/' + authentication.role.toLowerCase() + '/painel',
    ]);
  }

  submit() {
    const user = Object.assign({}, this.form.value);

    this._authService.login(user).subscribe({
      next: (authentication) => {
        this._authService.setAuthentication(authentication);
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
