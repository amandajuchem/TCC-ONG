import { Observable } from 'rxjs';

import { Page } from '../entities/page';

export interface AbstractService<T> {

  /**
   *
   * @param id
   */
  delete(id: string): Observable<any>;

  /**
   *
   * @param page
   * @param size
   * @param sort
   * @param direction
   */
  findAll(
    page: number,
    size: number,
    sort: string,
    direction: string
  ): Observable<Page<T>>;

  /**
   *
   * @param id
   */
  findById(id: string): Observable<T>;

  /**
   *
   * @param object
   */
  save(object: T, param?: any): Observable<T>;

  /**
   *
   * @param page
   * @param size
   * @param sort
   * @param direction
   */
  search(
    value: string,
    page: number,
    size: number,
    sort: string,
    direction: string
  ): Observable<Page<T>>;

  /**
   *
   * @param object
   */
  update(object: T, param?: any): Observable<T>;
}