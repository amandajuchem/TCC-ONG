import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { environment } from 'src/environments/environment';

import { Animal } from '../entities/animal';
import { Page } from '../entities/page';
import { AbstractService } from './abstract-service';
import { FileUtils } from '../utils/file-utils';

@Injectable({
  providedIn: 'root'
})
export class AnimalService implements AbstractService<Animal> {

  private _baseURL = environment.apiURL + '/animais';
  private _subject = new BehaviorSubject<Animal | null>(null);

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

    return this._http.get<Page<Animal>>(this._baseURL, {

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
    return this._http.get<Animal>(this._baseURL + '/' + id);
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
   * @param animal
   * @param foto
   * @returns
   */
  save(animal: Animal, foto: any) {
    const formData = this._handlerAnimal(animal, foto);
    return this._http.post<Animal>(this._baseURL, formData);
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

    return this._http.get<Page<Animal>>(this._baseURL + '/search', {

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
  set(animal: Animal | null) {
    this._subject.next(animal);
  }

  /**
   *
   * @param animal
   * @param foto
   * @returns
   */
  update(animal: Animal, foto: any) {
    const formData = this._handlerAnimal(animal, foto);
    return this._http.put<Animal>(this._baseURL + '/' + animal.id, formData);
  }

  private _handlerAnimal(animal: Animal, foto: any) {
    const formData = new FormData();

    formData.append('animal', new Blob([JSON.stringify(animal)], { type: 'application/json' }));

    if (foto) {
      formData.append('foto', new Blob([foto], { type: 'multipart/form-data' }), 'foto.' + FileUtils.getExtension(foto));
    }

    return formData;
  }
}