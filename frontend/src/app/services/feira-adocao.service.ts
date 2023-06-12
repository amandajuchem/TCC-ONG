import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { FeiraAdocao } from '../entities/feira-adocao';
import { Page } from '../entities/page';
import { AbstractService } from './abstract-service';

@Injectable({
  providedIn: 'root'
})
export class FeiraAdocaoService implements AbstractService<FeiraAdocao> {

  private _baseURL = environment.apiURL + '/feiras-adocao';

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

    return this._http.get<Page<FeiraAdocao>>(this._baseURL, {
      
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
    return this._http.get<FeiraAdocao>(this._baseURL + '/' + id);
  }

  /**
   * 
   * @param feiraAdocao 
   * @returns 
   */
  save(feiraAdocao: FeiraAdocao) {
    return this._http.post<FeiraAdocao>(this._baseURL, feiraAdocao);
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

    return this._http.get<Page<FeiraAdocao>>(this._baseURL + '/search', {
      
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
   * @param feiraAdocao 
   * @returns 
   */
  update(feiraAdocao: FeiraAdocao) {
    return this._http.put<FeiraAdocao>(this._baseURL + '/' + feiraAdocao.id, feiraAdocao);
  }
}