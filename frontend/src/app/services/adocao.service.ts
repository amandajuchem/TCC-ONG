import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

import { Adocao } from '../entities/adocao';
import { Page } from '../entities/page';
import { AbstractService } from './abstract-service';
import { FileUtils } from '../utils/file-utils';

@Injectable({
  providedIn: 'root',
})
export class AdocaoService implements AbstractService<Adocao> {
  private _baseURL = environment.apiURL + '/adocoes';

  constructor(private _http: HttpClient) {}

  /**
   *
   * @param id
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
    return this._http.get<Page<Adocao>>(this._baseURL, {
      params: {
        page: page,
        size: size,
        sort: sort,
        direction: direction,
      },
    });
  }

  /**
   *
   * @param id
   * @returns
   */
  findById(id: string) {
    return this._http.get<Adocao>(this._baseURL + '/' + id);
  }

  /**
   *
   * @param adocao
   * @param termoResponsabilidade
   * @returns
   */
  save(adocao: Adocao, termoResponsabilidade: Array<File> | null) {
    const formData = this._handlerAdocao(adocao, termoResponsabilidade);
    return this._http.post<Adocao>(this._baseURL, formData);
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
  search(
    value: string,
    page: number,
    size: number,
    sort: string,
    direction: string
  ): Observable<Page<Adocao>> {
    return this._http.get<Page<Adocao>>(this._baseURL + '/search', {
      params: {
        value: value,
        page: page,
        size: size,
        sort: sort,
        direction: direction,
      },
    });
  }

  /**
   *
   * @param adocao
   * @param termoResponsabilidade
   * @returns
   */
  update(adocao: Adocao, termoResponsabilidade: Array<File> | null) {
    const formData = this._handlerAdocao(adocao, termoResponsabilidade);
    return this._http.put<Adocao>(this._baseURL + '/' + adocao.id, formData);
  }

  private _handlerAdocao(
    adocao: Adocao,
    termoResponsabilidade: Array<File> | null
  ) {
    const formData = new FormData();

    formData.append(
      'adocao',
      new Blob([JSON.stringify(adocao)], { type: 'application/json' })
    );

    if (termoResponsabilidade) {
      termoResponsabilidade.forEach((arquivo: any, index: number) =>
        formData.append(
          'termoResponsabilidade',
          new Blob([arquivo], { type: 'multipart/form-data' }),
          'arquivo' + index + '.' + FileUtils.getExtension(arquivo)
        )
      );
    }

    return formData;
  }
}
