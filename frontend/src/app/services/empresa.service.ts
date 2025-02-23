import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

import { Empresa } from '../entities/empresa';
import { Page } from '../entities/page';
import { AbstractService } from './abstract-service';

@Injectable({
  providedIn: 'root'
})
export class EmpresaService implements AbstractService<Empresa> {

  private _baseURL = environment.apiURL + '/empresas';
  private _subject = new BehaviorSubject<Empresa | null>(null);

  constructor(private _http: HttpClient) { }
  
  /**
   * 
   * @param id 
   */
  delete(id: string): Observable<any> {
    throw new Error('Method not implemented.');
  }
  
  /**
   * 
   * @param page 
   * @param size 
   * @param sort 
   * @param direction 
   * @returns 
   */
  findAll(page: number, size: number, sort: string, direction: string): Observable<Page<Empresa>> {
    
    return this._http.get<Page<Empresa>>(this._baseURL, {
      
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
  findById(id: string): Observable<Empresa> {
    return this._http.get<Empresa>(this._baseURL + '/' + id);
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
   * @param empresa 
   * @returns 
   */
  save(empresa: Empresa): Observable<Empresa> {
    return this._http.post<Empresa>(this._baseURL, empresa);
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
  search(value: string, page: number, size: number, sort: string, direction: string): Observable<Page<Empresa>> {
    
    return this._http.get<Page<Empresa>>(this._baseURL + '/search', {
      
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
   * @param empresa 
   */
  set(empresa: Empresa | null) {
    this._subject.next(empresa);
  }
  
  /**
   * 
   * @param empresa 
   * @returns 
   */
  update(empresa: Empresa): Observable<Empresa> {
    return this._http.put<Empresa>(this._baseURL + '/' + empresa.id, empresa);
  }
}