import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/entities/user';
import { NotificationType } from 'src/app/enums/notification-type';
import { Setor } from 'src/app/enums/setor';
import { AuthService } from 'src/app/services/auth.service';
import { NotificationService } from 'src/app/services/notification.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})
export class LoginComponent implements OnInit {

  form!: FormGroup;
  hide!: boolean;

  constructor(
    private _authService: AuthService,
    private _formBuilder: FormBuilder,
    private _notificationService: NotificationService,
    private _router: Router
  ) { }

  ngOnInit(): void {

    this.hide = true;

    if (this._authService.isAuthenticated()) {

      let user: User = this._authService.getCurrentUser();

      switch (user.role) {

        case Setor.ADMINISTRACAO:
          this._router.navigate(['/administracao/painel']);
          break;
      }
    }

    else {
      this.buildForm();
    }
  }

  buildForm() {

    this.form = this._formBuilder.group({
      username: [null, Validators.required],
      password: [null, Validators.required]
    });
  }

  submit() {
    
    const user = Object.assign({}, this.form.value);

    this._authService.login(user).subscribe({

      next: (authentication) => {
        
        this._authService.setCurrentUser({
          token: authentication.access_token,
          role: authentication.role
        })
      },

      complete: () => {

        let user: User = this._authService.getCurrentUser();

        switch (user.role) {

          case Setor.ADMINISTRACAO:
            this._router.navigate(['/administracao/painel']);
            break;
        }
      },

      error: (error) => {
        console.error(error);
        this._notificationService.show(error.error.message, NotificationType.FAIL);
      }
    })
  }
}