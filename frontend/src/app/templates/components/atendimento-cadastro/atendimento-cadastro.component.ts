import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Atendimento } from 'src/app/entities/atendimento';
import { FacadeService } from 'src/app/services/facade.service';

@Component({
  selector: 'app-atendimento-cadastro',
  templateUrl: './atendimento-cadastro.component.html',
  styleUrls: ['./atendimento-cadastro.component.sass']
})
export class AtendimentoCadastroComponent implements OnInit {

  form!: FormGroup;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
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
      comorbidades: [atendimento?.comorbidades, Validators.required],
      diagnostico: [atendimento?.diagnostico, Validators.required],
      exames: [atendimento?.exames, Validators.nullValidator],
      procedimentos: [atendimento?.procedimentos, Validators.required],
      posologia: [atendimento?.posologia, Validators.nullValidator],
      documentos: [atendimento?.documentos, Validators.nullValidator],
      animal: [atendimento?.animal, Validators.required],
      usuario: [atendimento?.usuario, Validators.required]
    });
  }

  selectAnimal() {

  }

  selectVeterinario() {

  }

  submit() {

    let atendimento: Atendimento = Object.assign({}, this.form.getRawValue());

    if (atendimento.id) {
      
    }

    else {

    }
  }
}
