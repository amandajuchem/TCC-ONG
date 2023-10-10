import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

import { Atendimento } from '../entities/atendimento';
import { Page } from '../entities/page';
import { FileUtils } from '../utils/file-utils';
import { AbstractService } from './abstract-service';

@Injectable({
  providedIn: 'root'
})
export class AtendimentoService implements AbstractService<Atendimento> {

  private _baseURL = environment.apiURL + '/atendimentos';

  constructor(private _http: HttpClient) { }

  /**
   * 
   * @param id 
   * @returns 
   */
  delete(id: string) {
    return this._http.delete(this._baseURL + '/' + id);
  }

  /**
   * 
   * @param page 
   * @param size 
   * @param sort 
   * @param direction 
   * @returns 
   */
  findAll(page: number, size: number, sort: string, direction: string) {

    return this._http.get<Page<Atendimento>>(this._baseURL, {

      params: {
        page: page,
        size: size, 
        sort: sort,
        direction: direction
      }
    });
  }

  /**
   * 
   * @param id 
   * @returns 
   */
  findById(id: string) {
    return this._http.get<Atendimento>(this._baseURL + '/' + id);
  }

  /**
   * 
   * @param atendimento 
   * @param documentos 
   * @returns 
   */
  save(atendimento: Atendimento, examesRealizados: Array<any>) {
    const formData = this._hadlerAtendimento(atendimento, examesRealizados);
    console.log(formData);
    return this._http.post<Atendimento>(this._baseURL, formData);
  }

  /**
   * 
   * @param value 
   * @param page 
   * @param size 
   * @param sort 
   * @param direction 
   * @returns 
   */
  search(value: string, page: number, size: number, sort: string, direction: string) {

    return this._http.get<Page<Atendimento>>(this._baseURL + '/search', {
      params: {
        value: value,
        page: page,
        size: size, 
        sort: sort,
        direction: direction
      }
    });
  }

  /**
   * 
   * @param atendimento 
   * @param documentos 
   * @returns 
   */
  update(atendimento: Atendimento, examesRealizados: Array<any>) {
    const formData = this._hadlerAtendimento(atendimento, examesRealizados);
    return this._http.put<Atendimento>(this._baseURL + '/' + atendimento.id, formData);
  }

  private _hadlerAtendimento(atendimento: Atendimento, examesRealizados: Array<any>) {

    const formData: FormData = new FormData();

    formData.append('atendimento', new Blob([JSON.stringify(atendimento)], { type: 'application/json' }));

    examesRealizados.forEach(exameRealizado => {

      if (exameRealizado.file) {
        formData.append('examesRealizados', new Blob([exameRealizado.file], { type: 'multipart/form-data' }), exameRealizado.exame.nome + '.' + FileUtils.getExtension(exameRealizado.file));
      }
    });

    return formData;  
  }
}