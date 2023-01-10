import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

import { Tutor } from '../entities/tutor';

@Injectable({
  providedIn: 'root'
})
export class TutoresService {

  private baseURL = environment.apiURL + '/tutores';

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
    return this.http.get<Array<Tutor>>(this.baseURL);
  }

  /**
   * 
   * @param id 
   * @returns 
   */
  findById(id: string) {
    return this.http.get<Tutor>(this.baseURL + '/' + id);
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

    return this.http.post<Tutor>(this.baseURL, formData);
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

    return this.http.put<Tutor>(this.baseURL + '/' + tutor.id, formData);
  }
}