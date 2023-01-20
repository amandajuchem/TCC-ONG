import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FacadeService } from 'src/app/services/facade.service';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.sass']
})
export class ToolbarComponent implements OnInit {

  currentUser!: any;

  constructor(
    private _facade: FacadeService,
    private _router: Router
  ) { }

  ngOnInit(): void {
    this.currentUser = this._facade.authGetCurrentUser();
  }

  logout() {
    this._facade.authLogout();
    this._router.navigate(['']);
  }
}