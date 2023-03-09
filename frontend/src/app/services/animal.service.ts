import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { environment } from 'src/environments/environment';

import { Adocao } from '../entities/adocao';
import { Animal } from '../entities/animal';
import { Page } from '../entities/page';

@Injectable({
  providedIn: 'root'
})
export class AnimalService {

  private baseURL = environment.apiURL + '/animais';
  private subject = new BehaviorSubject<Animal | null>(null);

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
   * @param page
   * @param size
   * @param sort
   * @param direction
   * @returns
   */
  findAll(page: number, size: number, sort: string, direction: string) {

    return this._http.get<Page>(this.baseURL, {
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
    return this._http.get<Animal>(this.baseURL + '/' + id);
  }

  /**
   *
   * @returns
   */
  get() {
    return this.subject.asObservable();
  }

  /**
   *
   * @param animal
   * @param foto
   * @returns
   */
  save(animal: Animal, foto: any) {

    let formData = new FormData();

    formData.append('animal', new Blob([JSON.stringify(animal)], { type: 'application/json' }));

    if (foto) {
      formData.append('foto', new Blob([foto], { type: 'multipart/form-data' }), 'foto.png');
    }

    return this._http.post<Animal>(this.baseURL, formData);
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

    return this._http.get<Page>(this.baseURL + '/search', {
      params: {
        value: value,
        page: page,
        size: size,
        sort: sort,
        direction: direction
      }
    })
  }

  /**
   *
   * @param animal
   */
  set(animal: Animal) {
    this.subject.next(animal);
  }

  /**
   *
   * @param animal
   * @param foto
   * @returns
   */
  update(animal: Animal, foto: any) {

    let formData = new FormData();

    formData.append('animal', new Blob([JSON.stringify(animal)], { type: 'application/json' }));

    if (foto) {
      formData.append('foto', new Blob([foto], { type: 'multipart/form-data' }), 'foto.png');
    }

    return this._http.put<Animal>(this.baseURL + '/' + animal.id, formData);
  }

  ////////////////////////////////////////////////// ADOÇÃO //////////////////////////////////////////////////

  adocaoDelete(id: string, idAdocao: string) {
    return this._http.delete(this.baseURL + '/' + id + '/adocoes/' + idAdocao);
  }

  adocaoSave(animal: Animal, adocao: Adocao) {
    return this._http.post<Adocao>(this.baseURL + '/' + animal.id + '/adocoes', adocao);
  }

  adocaoUpdate(animal: Animal, adocao: Adocao) {
    return this._http.put<Adocao>(this.baseURL + '/' + animal.id + '/adocoes/' + adocao.id, adocao);
  }
}