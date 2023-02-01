import { Component } from '@angular/core';
import { User } from 'src/app/entities/user';
import { FacadeService } from 'src/app/services/facade.service';

@Component({
  selector: 'app-agendamentos',
  templateUrl: './agendamentos.component.html',
  styleUrls: ['./agendamentos.component.sass']
})
export class AgendamentosComponent {

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