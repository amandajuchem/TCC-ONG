import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

import { Atendimento } from '../entities/atendimento';

@Injectable({
  providedIn: 'root'
})
export class AtendimentoService {

  private baseURL = environment.apiURL + '/atendimentos';

  constructor(private _http: HttpClient) { }

  /**
   * 
   * @param id 
   * @returns 
   */
  delete(id: string) {
    return this._http.delete(this.baseURL + '/' + id);
  }

  /**
   * 
   * @returns 
   */
  findAll() {
    return this._http.get<Array<Atendimento>>(this.baseURL);
  }

  /**
   * 
   * @param atendimento 
   * @param documentosToSave 
   * @returns 
   */
  save(atendimento: Atendimento, documentosToSave: Array<File> | null) {

    let formData: FormData = new FormData();

    formData.append('atendimento', new Blob([JSON.stringify(atendimento)], { type: 'application/json' }));

    if (documentosToSave) {
      documentosToSave.forEach((imagem: any, index: number) => formData.append('documentosToSave', new Blob([imagem], { type: 'multipart/form-data' }), 'imagem' + index + '.png'));
    }

    return this._http.post<Atendimento>(this.baseURL, formData);
  }

  /**
   * 
   * @param atendimento 
   * @param documentosToSave 
   * @param documentosToDelete 
   * @returns 
   */
  update(atendimento: Atendimento, documentosToSave: Array<File> | null, documentosToDelete: Array<string> | null) {

    let formData: FormData = new FormData();

    formData.append('atendimento', new Blob([JSON.stringify(atendimento)], { type: 'application/json' }));

    if (documentosToSave) {
      documentosToSave.forEach((imagem: any, index: number) => formData.append('documentosToSave', new Blob([imagem], { type: 'multipart/form-data' }), 'imagem' + index + '.png'));
    }

    if (documentosToDelete) {
      formData.append('documentosToDelete', new Blob([JSON.stringify(documentosToDelete)], { type: 'application/json' }));
    }

    return this._http.put<Atendimento>(this.baseURL + '/' + atendimento.id, formData);
  }
}
