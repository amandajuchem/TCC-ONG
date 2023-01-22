import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/entities/user';
import { FacadeService } from 'src/app/services/facade.service';

@Component({
  selector: 'app-adocoes',
  templateUrl: './adocoes.component.html',
  styleUrls: ['./adocoes.component.sass']
})
export class AdocoesComponent implements OnInit {

  user!: User;

  constructor(
    private _facade: FacadeService
  ) { }

  ngOnInit(): void {
    this.user = this._facade.authGetCurrentUser();
  }

  add() {

  }

  filter(value: string) {

  }
}