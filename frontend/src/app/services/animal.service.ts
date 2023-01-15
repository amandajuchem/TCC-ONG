import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

import { Animal } from '../entities/animal';

@Injectable({
  providedIn: 'root'
})
export class AnimalService {

  private baseURL = environment.apiURL + '/animais';

  constructor(private http: HttpClient) { }

  /**
   * 
   * @param id 
   * @returns 
   */
  delete(id: string) {
    return this.http.delete(this.baseURL + '/' + id);
  }

  /**
   * 
   * @returns 
   */
  findAll() {
    return this.http.get<Array<Animal>>(this.baseURL);
  }

  /**
   * 
   * @param id 
   * @returns 
   */
  findById(id: string) {
    return this.http.get<Animal>(this.baseURL + '/' + id);
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

    return this.http.post<Animal>(this.baseURL, formData);
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

    return this.http.put<Animal>(this.baseURL + '/' + animal.id, formData);
  }
}