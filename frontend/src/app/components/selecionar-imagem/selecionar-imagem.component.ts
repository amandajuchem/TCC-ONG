import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-selecionar-imagem',
  templateUrl: './selecionar-imagem.component.html',
  styleUrls: ['./selecionar-imagem.component.sass'],
})
export class SelecionarImagemComponent {
  images!: Array<File>;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _matDialogRef: MatDialogRef<SelecionarImagemComponent>
  ) {}

  ngOnInit(): void {}

  onChange(event: any) {
    const selectedFiles = <FileList>event.srcElement.files;

    this.images = new Array();

    for (let i = 0; i < selectedFiles.length; i++) {
      this.images.push(selectedFiles[i]);
    }
  }

  readImage(image: File) {
    const fileReader = new FileReader();

    fileReader.readAsDataURL(image);

    fileReader.onloadend = () => {
      return fileReader.result;
    };
  }

  submit() {
    if (this.images && this.images.length > 0) {
      this._matDialogRef.close({
        status: true,
        message: 'Sucesso',
        images: this.images,
      });
    }
  }
}
