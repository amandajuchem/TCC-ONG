import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-selecionar-arquivo',
  templateUrl: './selecionar-arquivo.component.html',
  styleUrls: ['./selecionar-arquivo.component.sass']
})
export class SelecionarArquivoComponent {
  arquivo!: File;
  arquivos!: Array<File>;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _matDialogRef: MatDialogRef<SelecionarArquivoComponent>
  ) {}

  onChange(event: any) {
    const selectedFiles = <FileList>event.srcElement.files;

    if (this.data.multiple) {
      
      this.arquivos = [];

      for (let i = 0; i < selectedFiles.length; i++) {
        this.arquivos.push(selectedFiles[i]);
      }
    }
    
    else {
      this.arquivo = selectedFiles[0];
    }
  }

  submit() {

    if (this.arquivo || this.arquivos.length > 0) {
      this._matDialogRef.close({
        status: true,
        message: 'Sucesso',
        arquivo: this.arquivo,
        arquivos: this.arquivos
      });
    }
  }
}
