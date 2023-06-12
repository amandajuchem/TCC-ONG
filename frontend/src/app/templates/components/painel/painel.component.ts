import { Component, OnInit } from '@angular/core';
import { Authentication } from 'src/app/entities/authentication';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-painel',
  templateUrl: './painel.component.html',
  styleUrls: ['./painel.component.sass']
})
export class PainelComponent implements OnInit {
  
  authentication!: Authentication;

  constructor(
    private _authService: AuthService
  ) { }

  ngOnInit(): void {
    this.authentication = this._authService.getAuthentication();
  }
}