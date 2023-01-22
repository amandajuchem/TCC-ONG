import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/entities/user';
import { FacadeService } from 'src/app/services/facade.service';

@Component({
  selector: 'app-atendimentos',
  templateUrl: './atendimentos.component.html',
  styleUrls: ['./atendimentos.component.sass']
})
export class AtendimentosComponent implements OnInit {
  
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