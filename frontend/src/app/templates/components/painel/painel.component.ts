import { Component, OnInit } from '@angular/core';
import { FacadeService } from 'src/app/services/facade.service';

@Component({
  selector: 'app-painel',
  templateUrl: './painel.component.html',
  styleUrls: ['./painel.component.sass']
})
export class PainelComponent implements OnInit {
  
  currentUser: any;

  constructor(
    private facade: FacadeService
  ) { }

  ngOnInit(): void {
    this.currentUser = this.facade.authGetCurrentUser();
  }
}