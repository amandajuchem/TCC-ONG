import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { environment } from 'src/environments/environment';

import { Usuario } from '../entities/usuario';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  private baseURL = environment.apiURL + '/usuarios';
  private subject = new BehaviorSubject<Usuario | null>(null);

  constructor(private http: HttpClient) { }

  /**
   * 
   * @returns 
   */
  findAll() {
    return this.http.get<Array<Usuario>>(this.baseURL);
  }

  /**
   * 
   * @param id 
   * @returns 
   */
  findById(id: string) {
    return this.http.get<Usuario>(this.baseURL + '/' + id);
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
   * @param usuario 
   * @returns 
   */
  save(usuario: Usuario, novaFoto: any) {

    let formData = new FormData();

    formData.append('usuario', new Blob([JSON.stringify(usuario)], { type: 'application/json' }));

    if (novaFoto) {
      formData.append('novaFoto', new Blob([novaFoto], { type: 'multipart/form-data' }), 'novaFoto.png');
    }

    return this.http.post<Usuario>(this.baseURL, formData);
  }
  
  /**
   * 
   * @param usuario 
   */
  set(usuario: Usuario) {
    this.subject.next(usuario);
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

    return this.http.put<Usuario>(this.baseURL + '/' + usuario.id, formData);
  }
}