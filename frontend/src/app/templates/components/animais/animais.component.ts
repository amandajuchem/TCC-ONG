import { Component, OnInit } from '@angular/core';
import { FacadeService } from 'src/app/services/facade.service';

@Component({
  selector: 'app-animais',
  templateUrl: './animais.component.html',
  styleUrls: ['./animais.component.sass']
})
export class AnimaisComponent implements OnInit {
  
  currentUser: any;

  constructor(
    private facade: FacadeService
  ) { }

  ngOnInit(): void {
    this.currentUser = this.facade.authGetCurrentUser();
  }

  add() {

  }

  filter(value: string) {
    
  }
}