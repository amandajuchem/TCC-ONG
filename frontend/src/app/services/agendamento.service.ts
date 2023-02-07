import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Agendamento } from '../entities/agendamento';

@Injectable({
  providedIn: 'root'
})
export class AgendamentoService {

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
  findAll() {
    return this._http.get<Array<Agendamento>>(this._baseURL);
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
   * @param agendamento 
   * @returns 
   */
  update(agendamento: Agendamento) {
    return this._http.put<Agendamento>(this._baseURL + '/' + agendamento.id, agendamento);
  }
}