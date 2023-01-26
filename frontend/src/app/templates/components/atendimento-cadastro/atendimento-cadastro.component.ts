import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { Atendimento } from 'src/app/entities/atendimento';
import { FacadeService } from 'src/app/services/facade.service';

import { SelecionarAnimalComponent } from '../selecionar-animal/selecionar-animal.component';
import { SelecionarUsuarioComponent } from '../selecionar-usuario/selecionar-usuario.component';
import { Setor } from 'src/app/enums/setor';

@Component({
  selector: 'app-atendimento-cadastro',
  templateUrl: './atendimento-cadastro.component.html',
  styleUrls: ['./atendimento-cadastro.component.sass']
})
export class AtendimentoCadastroComponent implements OnInit {

  form!: FormGroup;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _dialog: MatDialog,
    private _facade: FacadeService,
    private _formBuilder: FormBuilder
  ) { }

  ngOnInit(): void {

    if (this.data.atendimento) {
      this.buildForm(this.data.atendimento);
    } else {
      this.buildForm(null);
    }
  }

  buildForm(atendimento: Atendimento | null) {

    this.form = this._formBuilder.group({
      id: [atendimento?.id, Validators.nullValidator],
      dataHora: [atendimento?.dataHora, Validators.required],
      dataHoraRetorno: [atendimento?.dataHoraRetorno, Validators.nullValidator],
      motivo: [atendimento?.motivo, Validators.required],
      comorbidades: [atendimento?.animal.fichaMedica.comorbidades, Validators.nullValidator],
      diagnostico: [atendimento?.diagnostico, Validators.required],
      exames: [atendimento?.exames, Validators.nullValidator],
      procedimentos: [atendimento?.procedimentos, Validators.required],
      posologia: [atendimento?.posologia, Validators.nullValidator],
      documentos: [atendimento?.documentos, Validators.nullValidator],
      animal: [atendimento?.animal, Validators.required],
      veterinario: [atendimento?.veterinario, Validators.required]
    });
  }

  selectAnimal() {

    this._dialog.open(SelecionarAnimalComponent, {
      width: '100%'
    })
    .afterClosed().subscribe({
      
      next: (result) => {
          
        if (result && result.status) {
          this.form.get('animal')?.patchValue(result.animal);
          this.form.get('comorbidades')?.patchValue(result.animal.fichaMedica.comorbidades);
        }
      },
    });
  }

  selectVeterinario() {

    this._dialog.open(SelecionarUsuarioComponent, {
      data: {
        setor: Setor.VETERINARIO
      },
      width: '100%'
    })
    .afterClosed().subscribe({
      
      next: (result) => {
          
        if (result && result.status) {
          this.form.get('veterinario')?.patchValue(result.usuario);
        }
      }
    });
  }

  submit() {

    let atendimento: Atendimento = Object.assign({}, this.form.getRawValue());

    if (atendimento.id) {
      
    }

    else {

    }
  }
}
