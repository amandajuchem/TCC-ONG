import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/entities/user';
import { FacadeService } from 'src/app/services/facade.service';

@Component({
  selector: 'app-painel',
  templateUrl: './painel.component.html',
  styleUrls: ['./painel.component.sass']
})
export class PainelComponent implements OnInit {
  
  user!: User;

  constructor(
    private _facade: FacadeService
  ) { }

  ngOnInit(): void {
    this.user = this._facade.authGetCurrentUser();
  }
}