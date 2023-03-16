import { Injectable } from '@angular/core';
import { AbstractService } from './abstract-service';
import { Adocao } from '../entities/adocao';
import { HttpClient } from '@angular/common/http';
import { Page } from '../entities/page';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AdocaoService implements AbstractService<Adocao> {

  private _baseURL = environment.apiURL + '/animais';

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
   * @param page 
   * @param size 
   * @param sort 
   * @param direction 
   * @returns 
   */
  findAll(page: number, size: number, sort: string, direction: string, animalId: string) {
    
    return this._http.get<Page<Adocao>>(this._baseURL, {
      
      params: {
        page: page,
        size: size,
        sort: sort,
        direction: direction,
        animalId: animalId
      }
    })
  }

  /**
   * 
   * @param id 
   * @returns 
   */
  findById(id: string) {
    return this._http.get<Adocao>(this._baseURL + '/' + id);
  }

  save(adocao: Adocao) {

    const form = new FormData();

    return this._http.post<Adocao>(this._baseURL, form);
  }

  update(adocao: Adocao) {

    const form = new FormData();

    return this._http.put<Adocao>(this._baseURL + '/' + adocao.id, form);
  }
}