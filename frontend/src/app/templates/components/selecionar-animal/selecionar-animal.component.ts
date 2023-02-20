import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Animal } from 'src/app/entities/animal';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-selecionar-animal',
  templateUrl: './selecionar-animal.component.html',
  styleUrls: ['./selecionar-animal.component.sass']
})
export class SelecionarAnimalComponent implements OnInit {
  
  animal!: Animal;
  columns!: Array<String>;
  dataSource!: MatTableDataSource<Animal>;

  @ViewChild(MatSort, { static: false }) set sort(value: MatSort) { if (this.dataSource) this.dataSource.sort = value }

  constructor(
    private _facade: FacadeService, 
    private _matDialogRef: MatDialogRef<SelecionarAnimalComponent>
  ) { }

  ngOnInit(): void {
    this.columns = ['index', 'nome', 'sexo', 'idade'];
    this.dataSource = new MatTableDataSource();
  }

  search(nome: string) {

    // this._facade.animalFindByNomeContains(nome).subscribe({

    //   next: (animais) => {
    //     this.dataSource.data = animais;
    //   },

    //   error: (error) => {
    //     console.error(error);
    //     this._facade.notificationShowNotification(MessageUtils.ANIMAIS_GET_FAIL, NotificationType.FAIL); 
    //   }
    // });
  }

  select(animal: Animal) {
    this.animal = animal;
  }

  submit() {
    this._matDialogRef.close({ status: true, animal: this.animal });
  }
}