import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { environment } from 'src/environments/environment';

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
   * @param novaFoto 
   * @returns 
   */
  save(animal: Animal, novaFoto: any) {
    
    let formData = new FormData();

    formData.append('animal', new Blob([JSON.stringify(animal)], { type: 'application/json' }));

    if (novaFoto) {
      formData.append('novaFoto', new Blob([novaFoto], { type: 'multipart/form-data' }), 'novaFoto.png');
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
   * @param novaFoto 
   * @param antigaFoto 
   * @returns 
   */
  update(animal: Animal, novaFoto: any, antigaFoto: any) {
    
    let formData = new FormData();

    formData.append('animal', new Blob([JSON.stringify(animal)], { type: 'application/json' }));

    if (novaFoto) {
      formData.append('novaFoto', new Blob([novaFoto], { type: 'multipart/form-data' }), 'novaFoto.png');
    }

    if (antigaFoto) {
      formData.append('antigaFoto', new Blob([antigaFoto], { type: 'text/plain' }));
    }

    return this._http.put<Animal>(this.baseURL + '/' + animal.id, formData);
  }
}