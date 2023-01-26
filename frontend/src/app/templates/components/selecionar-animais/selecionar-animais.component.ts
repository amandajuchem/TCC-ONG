import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Animal } from 'src/app/entities/animal';
import { FacadeService } from 'src/app/services/facade.service';

@Component({
  selector: 'app-selecionar-animais',
  templateUrl: './selecionar-animais.component.html',
  styleUrls: ['./selecionar-animais.component.sass']
})
export class SelecionarAnimaisComponent implements OnInit {
  
  animais!: Array<Animal>;
  columns!: Array<String>;
  dataSource!: MatTableDataSource<Animal>;

  @ViewChild(MatSort, { static: false }) set sort(value: MatSort) { if (this.dataSource) this.dataSource.sort = value }

  constructor(
    private _facade: FacadeService,
    private _matDialogRef: MatDialogRef<SelecionarAnimaisComponent>
  ) { }

  ngOnInit(): void {
    this.animais = [];
    this.columns = ['index', 'nome', 'sexo', 'idade'];
    this.dataSource = new MatTableDataSource(); this.animais.length
  }

  filter(value: string) {

  }

  select(animal: Animal) {
    
    if (this.animais.includes(animal)) {
      this.animais = this.animais.filter(a => a.id != animal.id);
    }

    else {
      this.animais.push(animal);
    }
  }

  submit() {
    
  }
}