import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

import { Exame } from '../entities/exame';

@Injectable({
  providedIn: 'root'
})
export class ExameService {

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
   * @returns 
   */
  findAll() {
    return this._http.get<Array<Exame>>(this._baseURL);
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
   * @param exame 
   * @returns 
   */
  update(exame: Exame) {
    return this._http.put<Exame>(this._baseURL + '/' + exame.id, exame);
  }
}