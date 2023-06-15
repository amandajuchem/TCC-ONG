import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

import { Agendamento } from '../entities/agendamento';
import { Page } from '../entities/page';
import { AbstractService } from './abstract-service';

@Injectable({
  providedIn: 'root'
})
export class AgendamentoService implements AbstractService<Agendamento> {

  private _baseURL = environment.apiURL + '/agendamentos';

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
   * @returns 
   */
  findAll(page: number, size: number, sort: string, direction: string) {

    return this._http.get<Page<Agendamento>>(this._baseURL, {
      
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
    return this._http.get<Agendamento>(this._baseURL + '/' + id);
  }

  /**
   * 
   * @param agendamento 
   * @returns 
   */
  save(agendamento: Agendamento) {
    return this._http.post<Agendamento>(this._baseURL, agendamento);
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

    return this._http.get<Page<Agendamento>>(this._baseURL + '/search', {
      
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
   * @param agendamento 
   * @returns 
   */
  update(agendamento: Agendamento) {
    return this._http.put<Agendamento>(this._baseURL + '/' + agendamento.id, agendamento);
  }
}