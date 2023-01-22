import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/entities/user';
import { NotificationType } from 'src/app/enums/notification-type';
import { Setor } from 'src/app/enums/setor';
import { FacadeService } from 'src/app/services/facade.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})
export class LoginComponent implements OnInit {

  form!: FormGroup;
  hide!: boolean;

  constructor(
    private _facade: FacadeService,
    private _formBuilder: FormBuilder,
    private _router: Router
  ) { }

  ngOnInit(): void {

    this.hide = true;

    if (this._facade.authIsAuthenticated()) {

      let user: User = this._facade.authGetCurrentUser();

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

    this._facade.authLogin(user).subscribe({

      next: (authentication) => {
        
        this._facade.authSetCurrentUser({
          token: authentication.access_token,
          role: authentication.role
        })
      },

      complete: () => {

        let user: User = this._facade.authGetCurrentUser();

        switch (user.role) {

          case Setor.ADMINISTRACAO:
            this._router.navigate(['/administracao/painel']);
            break;
        }
      },

      error: (error) => {
        console.error(error);
        this._facade.notificationShowNotification(error.error.message, NotificationType.FAIL);
      }
    })
  }
}