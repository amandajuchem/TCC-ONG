import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Usuario } from '../entities/usuario';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UsuariosService {

  constructor(
    private http: HttpClient
  ) { }

  /**
   * 
   * @returns 
   */
  findAll() {
    return this.http.get<Array<Usuario>>(environment.apiURL + '/usuarios');
  }

  /**
   * 
   * @param usuario 
   * @returns 
   */
  save(usuario: Usuario, novaFoto: any, antigaFoto: any) {

    let formData = new FormData();

    formData.append('usuario', new Blob([JSON.stringify(usuario)], { type: 'application/json' }));

    if (novaFoto) {
      formData.append('novaFoto', new Blob([novaFoto], { type: 'multipart/form-data' }), 'novaFoto.png');
    }

    if (antigaFoto) {
      formData.append('antigaFoto', new Blob([antigaFoto], { type: 'text/plain' }));
    }

    return this.http.post<Usuario>(environment.apiURL + '/usuarios', formData);
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

    return this.http.put<Usuario>(environment.apiURL + '/usuarios/' + usuario.id, formData);
  }
}