import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ICompanyProfile } from 'app/shared/model/company-profile.model';

type EntityResponseType = HttpResponse<ICompanyProfile>;
type EntityArrayResponseType = HttpResponse<ICompanyProfile[]>;

@Injectable({ providedIn: 'root' })
export class CompanyProfileService {
  public resourceUrl = SERVER_API_URL + 'api/company-profiles';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/company-profiles';

  constructor(protected http: HttpClient) {}

  create(companyProfile: ICompanyProfile): Observable<EntityResponseType> {
    return this.http.post<ICompanyProfile>(this.resourceUrl, companyProfile, { observe: 'response' });
  }

  update(companyProfile: ICompanyProfile): Observable<EntityResponseType> {
    return this.http.put<ICompanyProfile>(this.resourceUrl, companyProfile, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICompanyProfile>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompanyProfile[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompanyProfile[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
