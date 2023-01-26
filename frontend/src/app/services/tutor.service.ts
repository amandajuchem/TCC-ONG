import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { environment } from 'src/environments/environment';

import { Tutor } from '../entities/tutor';

@Injectable({
  providedIn: 'root'
})
export class TutorService {

  private _baseURL = environment.apiURL + '/tutores';
  private _subject = new BehaviorSubject<Tutor | null>(null);

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
    return this._http.get<Array<Tutor>>(this._baseURL);
  }

  /**
   * 
   * @param id 
   * @returns 
   */
  findById(id: string) {
    return this._http.get<Tutor>(this._baseURL + '/' + id);
  }

  /**
   * 
   * @param nome 
   * @returns 
   */
  findByNomeContains(nome: string) {
    
    return this._http.get<Array<Tutor>>(this._baseURL + '/search', {
      params: {
        nome: nome
      }
    });
  }

  /**
   * 
   * @returns 
   */
  get() {
    return this._subject.asObservable();
  }

  /**
   * 
   * @param tutor 
   * @param novaFoto 
   * @returns 
   */
  save(tutor: Tutor, novaFoto: any) {

    let formData = new FormData();

    formData.append('tutor', new Blob([JSON.stringify(tutor)], { type: 'application/json' }));

    if (novaFoto) {
      formData.append('novaFoto', new Blob([novaFoto], { type: 'multipart/form-data' }), 'novaFoto.png');
    }

    return this._http.post<Tutor>(this._baseURL, formData);
  }

  /**
   * 
   * @param tutor 
   */
  set(tutor: Tutor) {
    this._subject.next(tutor);
  }

  /**
   * 
   * @param tutor 
   * @param novaFoto 
   * @param antigaFoto 
   * @returns 
   */
  update(tutor: Tutor, novaFoto: any, antigaFoto: any) {
    
    let formData = new FormData();

    formData.append('tutor', new Blob([JSON.stringify(tutor)], { type: 'application/json' }));

    if (novaFoto) {
      formData.append('novaFoto', new Blob([novaFoto], { type: 'multipart/form-data' }), 'novaFoto.png');
    }

    if (antigaFoto) {
      formData.append('antigaFoto', new Blob([antigaFoto], { type: 'text/plain' }));
    }

    return this._http.put<Tutor>(this._baseURL + '/' + tutor.id, formData);
  }
}