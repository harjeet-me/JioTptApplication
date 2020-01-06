import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { CompanyProfileService } from 'app/entities/company-profile/company-profile.service';
import { ICompanyProfile, CompanyProfile } from 'app/shared/model/company-profile.model';
import { CountryEnum } from 'app/shared/model/enumerations/country-enum.model';
import { ToggleStatus } from 'app/shared/model/enumerations/toggle-status.model';
import { CURRENCY } from 'app/shared/model/enumerations/currency.model';

describe('Service Tests', () => {
  describe('CompanyProfile Service', () => {
    let injector: TestBed;
    let service: CompanyProfileService;
    let httpMock: HttpTestingController;
    let elemDefault: ICompanyProfile;
    let expectedResult: ICompanyProfile | ICompanyProfile[] | boolean | null;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CompanyProfileService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new CompanyProfile(
        0,
        false,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        CountryEnum.USA,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        0,
        'image/png',
        'AAAAAAA',
        ToggleStatus.ACTIVE,
        CURRENCY.USD
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CompanyProfile', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new CompanyProfile())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CompanyProfile', () => {
        const returnedFromService = Object.assign(
          {
            active: true,
            company: 'BBBBBB',
            address: 'BBBBBB',
            streetAddress: 'BBBBBB',
            city: 'BBBBBB',
            stateProvince: 'BBBBBB',
            country: 'BBBBBB',
            postalCode: 'BBBBBB',
            email: 'BBBBBB',
            website: 'BBBBBB',
            phoneNumber: 1,
            dot: 'BBBBBB',
            mc: 1,
            companyLogo: 'BBBBBB',
            profileStatus: 'BBBBBB',
            preffredCurrency: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CompanyProfile', () => {
        const returnedFromService = Object.assign(
          {
            active: true,
            company: 'BBBBBB',
            address: 'BBBBBB',
            streetAddress: 'BBBBBB',
            city: 'BBBBBB',
            stateProvince: 'BBBBBB',
            country: 'BBBBBB',
            postalCode: 'BBBBBB',
            email: 'BBBBBB',
            website: 'BBBBBB',
            phoneNumber: 1,
            dot: 'BBBBBB',
            mc: 1,
            companyLogo: 'BBBBBB',
            profileStatus: 'BBBBBB',
            preffredCurrency: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query()
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a CompanyProfile', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
