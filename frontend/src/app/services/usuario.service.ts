import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { environment } from 'src/environments/environment';

import { Page } from '../entities/page';
import { Usuario } from '../entities/usuario';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  private _baseURL = environment.apiURL + '/usuarios';
  private _subject = new BehaviorSubject<Usuario | null>(null);

  constructor(private _http: HttpClient) { }

  /**
   * 
   * @param page 
   * @param size 
   * @param sort 
   * @param direction 
   * @returns 
   */
  findAll(page: number, size: number, sort: string, direction: string) {
    
    return this._http.get<Page>(this._baseURL, {
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
   * @returns 
   */
  save(usuario: Usuario, novaFoto: any) {

    let formData = new FormData();

    formData.append('usuario', new Blob([JSON.stringify(usuario)], { type: 'application/json' }));

    if (novaFoto) {
      formData.append('novaFoto', new Blob([novaFoto], { type: 'multipart/form-data' }), 'novaFoto.png');
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

    return this._http.get<Page>(this._baseURL + '/search', {
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
   * @returns 
   */
  update(usuario: Usuario, novaFoto: any, antigaFoto: any) {

    let formData = new FormData();

    formData.append('usuario', new Blob([JSON.stringify(usuario)], { type: 'application/json' }));

    if (novaFoto) {
      formData.append('novaFoto', new Blob([novaFoto], { type: 'multipart/form-data' }), 'novaFoto.png');
    }

    if (antigaFoto) {
      formData.append('antigaFoto', new Blob([antigaFoto], { type: 'text/plain' }));
    }

    return this._http.put<Usuario>(this._baseURL + '/' + usuario.id, formData);
  }
}