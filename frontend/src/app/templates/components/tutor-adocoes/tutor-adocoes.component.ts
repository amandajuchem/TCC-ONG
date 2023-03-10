import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Adocao } from 'src/app/entities/adocao';
import { Tutor } from 'src/app/entities/tutor';
import { TutorService } from 'src/app/services/tutor.service';

@Component({
  selector: 'app-tutor-adocoes',
  templateUrl: './tutor-adocoes.component.html',
  styleUrls: ['./tutor-adocoes.component.sass']
})
export class TutorAdocoesComponent implements AfterViewInit {

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
}