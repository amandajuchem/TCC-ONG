import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Setor } from 'src/app/enums/setor';
import { NotificationType } from 'src/app/enums/notification-type';
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
    private facade: FacadeService,
    private formBuilder: FormBuilder,
    private router: Router
  ) { }

  ngOnInit(): void {

    this.hide = true;

    if (this.facade.authIsAuthenticated()) {

      let currentUser = this.facade.authGetCurrentUser();

      switch (currentUser.role) {

        case Setor.ADMINISTRACAO:
          this.router.navigate(['/administracao/painel']);
          break;
      }
    }

    else {
      this.buildForm();
    }
  }

  buildForm() {

    this.form = this.formBuilder.group({
      username: [null, Validators.required],
      password: [null, Validators.required]
    });
  }

  submit() {
    const user = Object.assign({}, this.form.value);

    this.facade.authLogin(user).subscribe({

      next: (authentication) => {
        
        this.facade.authSetCurrentUser({
          token: authentication.access_token,
          role: authentication.role
        })
      },

      complete: () => {
        let currentUser = this.facade.authGetCurrentUser();

        switch (currentUser.role) {

          case Setor.ADMINISTRACAO:
            this.router.navigate(['/administracao/painel']);
            break;
        }
      },

      error: (error) => {
        console.error(error);
        this.facade.notificationsShowNotification(error.error.message, NotificationType.FAIL);
      }
    })
  }
}