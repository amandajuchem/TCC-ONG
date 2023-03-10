import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/entities/user';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-painel',
  templateUrl: './painel.component.html',
  styleUrls: ['./painel.component.sass']
})
export class PainelComponent implements OnInit {
  
  user!: User;

  constructor(
    private _authService: AuthService
  ) { }

  ngOnInit(): void {
    this.user = this._authService.getCurrentUser();
  }
}