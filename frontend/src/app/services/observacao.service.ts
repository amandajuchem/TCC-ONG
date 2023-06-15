import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

import { Observacao } from '../entities/observacao';
import { Page } from '../entities/page';
import { AbstractService } from './abstract-service';

@Injectable({
  providedIn: 'root'
})
export class ObservacaoService implements AbstractService<Observacao> {

  private _baseURL = environment.apiURL + '/observacoes';

  constructor(private _http: HttpClient) { }

  /**
   * 
   * @param id 
   */
  delete(id: string) {
    return this._http.delete(this._baseURL + '/' + id);
  }

  /**
   * 
   * @param tutorId 
   * @param page 
   * @param size 
   * @param sort 
   * @param direction 
   * @returns 
   */
  findAll(page: number, size: number, sort: string, direction: string) {

    return this._http.get<Page<Observacao>>(this._baseURL, {
      
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
    return this._http.get<Observacao>(this._baseURL + '/' + id);
  }

  /**
   * 
   * @param observacao 
   * @returns 
   */
  save(observacao: Observacao) {
    return this._http.post<Observacao>(this._baseURL, observacao);
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
  search(value: string, page: number, size: number, sort: string, direction: string): Observable<Page<Observacao>> {
      
    return this._http.get<Page<Observacao>>(this._baseURL + '/search', {
      
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
   * @param observacao 
   * @returns 
   */
  update(observacao: Observacao) {
    return this._http.put<Observacao>(this._baseURL + '/' + observacao.id, observacao);
  }
}