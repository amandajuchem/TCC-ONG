import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Adocao } from 'src/app/entities/adocao';
import { Observacao } from 'src/app/entities/observacao';
import { Tutor } from 'src/app/entities/tutor';
import { TutorService } from 'src/app/services/tutor.service';

@Component({
  selector: 'app-tutor-observacoes',
  templateUrl: './tutor-observacoes.component.html',
  styleUrls: ['./tutor-observacoes.component.sass']
})
export class TutorObservacoesComponent implements AfterViewInit {

  columns!: Array<string>;
  dataSource!: MatTableDataSource<Adocao>;
  isLoadingResults!: boolean;
  tutor!: Tutor;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private _dialog: MatDialog,
    private _tutorService: TutorService
  ) { }

  ngAfterViewInit(): void {
  
  }

  add() {

  }

  delete(observacao: Observacao) {

  }

  findAllObservacoes() {

  }

  update(observacao: Observacao) {

    
  }
}