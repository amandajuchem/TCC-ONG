import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

import { Exame } from '../entities/exame';
import { Page } from '../entities/page';
import { AbstractService } from './abstract-service';

@Injectable({
  providedIn: 'root'
})
export class ExameService implements AbstractService<Exame> {

  private _baseURL = environment.apiURL + '/exames';

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

    return this._http.get<Page<Exame>>(this._baseURL, {
      
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
    return this._http.get<Exame>(this._baseURL + '/' + id);
  }

  /**
   * 
   * @param exame 
   * @returns 
   */
  save(exame: Exame) {
    return this._http.post<Exame>(this._baseURL, exame);
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

    return this._http.get<Page<Exame>>(this._baseURL + '/search', {
      
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
   * @param exame 
   * @returns 
   */
  update(exame: Exame) {
    return this._http.put<Exame>(this._baseURL + '/' + exame.id, exame);
  }
}