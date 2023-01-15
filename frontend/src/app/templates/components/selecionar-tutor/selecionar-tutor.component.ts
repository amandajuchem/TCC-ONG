import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Tutor } from 'src/app/entities/tutor';
import { NotificationType } from 'src/app/enums/notification-type';
import { FacadeService } from 'src/app/services/facade.service';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-selecionar-tutor',
  templateUrl: './selecionar-tutor.component.html',
  styleUrls: ['./selecionar-tutor.component.sass']
})
export class SelecionarTutorComponent implements OnInit {

  columns!: Array<String>;
  dataSource!: MatTableDataSource<Tutor>;
  tutor!: Tutor;

  @ViewChild(MatSort, { static: false }) set sort(value: MatSort) { if (this.dataSource) this.dataSource.sort = value }
  constructor(
    private _facade: FacadeService, 
    private _matDialogRef: MatDialogRef<SelecionarTutorComponent>
  ) { }

  ngOnInit(): void {
    this.columns = ['index', 'nome', 'cpf'];
    this.dataSource = new MatTableDataSource();
  }

  search(nome: string) {

    this._facade.tutorFindByNomeContains(nome).subscribe({

      next: (tutores) => {
        this.dataSource.data = tutores;
      },

      error: (error) => {
        console.error(error);
        this._facade.notificationShowNotification(MessageUtils.ANIMAIS_GET_FAIL, NotificationType.FAIL); 
      }
    });
  }

  select(tutor: Tutor) {
    this.tutor = tutor;
  }

  submit() {
    this._matDialogRef.close({ status: true, tutor: this.tutor });
  }
}