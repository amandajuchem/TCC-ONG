import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { environment } from 'src/environments/environment';

import { Page } from '../entities/page';
import { Tutor } from '../entities/tutor';
import { AbstractService } from './abstract-service';

@Injectable({
  providedIn: 'root'
})
export class TutorService implements AbstractService<Tutor> {

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
   * @param page 
   * @param size 
   * @param sort 
   * @param direction 
   * @returns 
   */
  findAll(page: number, size: number, sort: string, direction: string) {
    
    return this._http.get<Page<Tutor>>(this._baseURL, {

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
    return this._http.get<Tutor>(this._baseURL + '/' + id);
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
   * @param foto 
   * @returns 
   */
  save(tutor: Tutor) {
    return this._http.post<Tutor>(this._baseURL, tutor);
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

    return this._http.get<Page<Tutor>>(this._baseURL + '/search', {

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
   * @param tutor 
   */
  set(tutor: Tutor | null) {
    this._subject.next(tutor);
  }

  /**
   * 
   * @param tutor 
   * @param foto 
   * @returns 
   */
  update(tutor: Tutor) {
    return this._http.put<Tutor>(this._baseURL + '/' + tutor.id, tutor);
  }
}