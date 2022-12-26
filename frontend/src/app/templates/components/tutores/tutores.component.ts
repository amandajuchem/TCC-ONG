import { Component, OnInit } from '@angular/core';
import { FacadeService } from 'src/app/services/facade.service';

@Component({
  selector: 'app-tutores',
  templateUrl: './tutores.component.html',
  styleUrls: ['./tutores.component.sass']
})
export class TutoresComponent implements OnInit {
  
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
