import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IAccounts } from 'app/shared/model/accounts.model';

type EntityResponseType = HttpResponse<IAccounts>;
type EntityArrayResponseType = HttpResponse<IAccounts[]>;

@Injectable({ providedIn: 'root' })
export class AccountsService {
  public resourceUrl = SERVER_API_URL + 'api/accounts';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/accounts';

  constructor(protected http: HttpClient) {}

  create(accounts: IAccounts): Observable<EntityResponseType> {
    return this.http.post<IAccounts>(this.resourceUrl, accounts, { observe: 'response' });
  }

  update(accounts: IAccounts): Observable<EntityResponseType> {
    return this.http.put<IAccounts>(this.resourceUrl, accounts, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAccounts>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAccounts[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAccounts[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
