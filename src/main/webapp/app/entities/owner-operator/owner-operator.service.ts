import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IOwnerOperator } from 'app/shared/model/owner-operator.model';

type EntityResponseType = HttpResponse<IOwnerOperator>;
type EntityArrayResponseType = HttpResponse<IOwnerOperator[]>;

@Injectable({ providedIn: 'root' })
export class OwnerOperatorService {
  public resourceUrl = SERVER_API_URL + 'api/owner-operators';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/owner-operators';

  constructor(protected http: HttpClient) {}

  create(ownerOperator: IOwnerOperator): Observable<EntityResponseType> {
    return this.http.post<IOwnerOperator>(this.resourceUrl, ownerOperator, { observe: 'response' });
  }

  update(ownerOperator: IOwnerOperator): Observable<EntityResponseType> {
    return this.http.put<IOwnerOperator>(this.resourceUrl, ownerOperator, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOwnerOperator>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOwnerOperator[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOwnerOperator[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
