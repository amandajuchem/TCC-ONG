import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { environment } from 'src/environments/environment';

import { Page } from '../entities/page';
import { Usuario } from '../entities/usuario';
import { AbstractService } from './abstract-service';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService implements AbstractService<Usuario> {

  private _baseURL = environment.apiURL + '/usuarios';
  private _subject = new BehaviorSubject<Usuario | null>(null);

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
    
    return this._http.get<Page<Usuario>>(this._baseURL, {
      
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
    return this._http.get<Usuario>(this._baseURL + '/' + id);
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
   * @param usuario 
   * @param foto 
   * @returns 
   */
  save(usuario: Usuario, foto: any) {

    let formData = new FormData();

    formData.append('usuario', new Blob([JSON.stringify(usuario)], { type: 'application/json' }));

    if (foto) {
      formData.append('foto', new Blob([foto], { type: 'multipart/form-data' }), 'foto.png');
    }

    return this._http.post<Usuario>(this._baseURL, formData);
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

    return this._http.get<Page<Usuario>>(this._baseURL + '/search', {
      
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
   * @param usuario 
   */
  set(usuario: Usuario) {
    this._subject.next(usuario);
  }

  /**
   * 
   * @param usuario 
   * @param foto 
   * @returns 
   */
  update(usuario: Usuario, foto: any) {

    let formData = new FormData();

    formData.append('usuario', new Blob([JSON.stringify(usuario)], { type: 'application/json' }));

    if (foto) {
      formData.append('foto', new Blob([foto], { type: 'multipart/form-data' }), 'foto.png');
    }

    return this._http.put<Usuario>(this._baseURL + '/' + usuario.id, formData);
  }
}