import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { User } from 'src/app/entities/user';
import { FacadeService } from 'src/app/services/facade.service';
import { AtendimentoCadastroComponent } from '../atendimento-cadastro/atendimento-cadastro.component';

@Component({
  selector: 'app-atendimentos',
  templateUrl: './atendimentos.component.html',
  styleUrls: ['./atendimentos.component.sass']
})
export class AtendimentosComponent implements OnInit {
  
  user!: User;

  constructor(
    private _dialog: MatDialog,
    private _facade: FacadeService
  ) { }

  ngOnInit(): void {
    this.user = this._facade.authGetCurrentUser();
  }

  add() {

    this._dialog.open(AtendimentoCadastroComponent, {
      data: {
        atendimento: null
      },
      width: '100%'
    })
    .afterClosed().subscribe({

      next: (result) => {
          
        if (result && result.status) {
          this.findAllAtendimentos();
        }
      }
    });
  }

  filter(value: string) {
    
  }

  findAllAtendimentos() {

  }
}