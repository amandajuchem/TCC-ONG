import { Component, OnInit } from '@angular/core';
import { FacadeService } from 'src/app/services/facade.service';

@Component({
  selector: 'app-usuarios',
  templateUrl: './usuarios.component.html',
  styleUrls: ['./usuarios.component.sass']
})
export class UsuariosComponent implements OnInit {
  
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