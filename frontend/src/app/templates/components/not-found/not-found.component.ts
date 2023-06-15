import { Component, OnInit } from '@angular/core';
import { Authentication } from 'src/app/entities/authentication';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-not-found',
  templateUrl: './not-found.component.html',
  styleUrls: ['./not-found.component.sass']
})
export class NotFoundComponent implements OnInit {

  authentication!: Authentication;

  constructor(
    private _authService: AuthService 
  ) { }

  ngOnInit(): void {
    this.authentication = this._authService.getAuthentication();
  }
}