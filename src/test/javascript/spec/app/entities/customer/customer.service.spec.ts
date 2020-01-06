import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { CustomerService } from 'app/entities/customer/customer.service';
import { ICustomer, Customer } from 'app/shared/model/customer.model';
import { Designation } from 'app/shared/model/enumerations/designation.model';
import { PreffredContactType } from 'app/shared/model/enumerations/preffred-contact-type.model';
import { CountryEnum } from 'app/shared/model/enumerations/country-enum.model';
import { ToggleStatus } from 'app/shared/model/enumerations/toggle-status.model';
import { CURRENCY } from 'app/shared/model/enumerations/currency.model';

describe('Service Tests', () => {
  describe('Customer Service', () => {
    let injector: TestBed;
    let service: CustomerService;
    let httpMock: HttpTestingController;
    let elemDefault: ICustomer;
    let expectedResult: ICustomer | ICustomer[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CustomerService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Customer(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        Designation.MANAGER,
        'AAAAAAA',
        0,
        PreffredContactType.PHONE,
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        CountryEnum.USA,
        'AAAAAAA',
        'AAAAAAA',
        0,
        'image/png',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        ToggleStatus.ACTIVE,
        CURRENCY.USD,
        'AAAAAAA',
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            customerSince: currentDate.format(DATE_FORMAT),
            timeZone: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Customer', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            customerSince: currentDate.format(DATE_FORMAT),
            timeZone: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            customerSince: currentDate,
            timeZone: currentDate
          },
          returnedFromService
        );
        service
          .create(new Customer())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Customer', () => {
        const returnedFromService = Object.assign(
          {
            company: 'BBBBBB',
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            contactDesignation: 'BBBBBB',
            email: 'BBBBBB',
            phoneNumber: 1,
            preffredContactType: 'BBBBBB',
            website: 'BBBBBB',
            secondaryContactPerson: 'BBBBBB',
            secContactNumber: 1,
            secContactEmail: 'BBBBBB',
            secContactPreContactTime: 'BBBBBB',
            fax: 1,
            address: 'BBBBBB',
            streetAddress: 'BBBBBB',
            city: 'BBBBBB',
            stateProvince: 'BBBBBB',
            country: 'BBBBBB',
            postalCode: 'BBBBBB',
            dot: 'BBBBBB',
            mc: 1,
            companyLogo: 'BBBBBB',
            customerSince: currentDate.format(DATE_FORMAT),
            remarks: 'BBBBBB',
            contract: 'BBBBBB',
            status: 'BBBBBB',
            preffredCurrency: 'BBBBBB',
            payterms: 'BBBBBB',
            timeZone: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            customerSince: currentDate,
            timeZone: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Customer', () => {
        const returnedFromService = Object.assign(
          {
            company: 'BBBBBB',
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            contactDesignation: 'BBBBBB',
            email: 'BBBBBB',
            phoneNumber: 1,
            preffredContactType: 'BBBBBB',
            website: 'BBBBBB',
            secondaryContactPerson: 'BBBBBB',
            secContactNumber: 1,
            secContactEmail: 'BBBBBB',
            secContactPreContactTime: 'BBBBBB',
            fax: 1,
            address: 'BBBBBB',
            streetAddress: 'BBBBBB',
            city: 'BBBBBB',
            stateProvince: 'BBBBBB',
            country: 'BBBBBB',
            postalCode: 'BBBBBB',
            dot: 'BBBBBB',
            mc: 1,
            companyLogo: 'BBBBBB',
            customerSince: currentDate.format(DATE_FORMAT),
            remarks: 'BBBBBB',
            contract: 'BBBBBB',
            status: 'BBBBBB',
            preffredCurrency: 'BBBBBB',
            payterms: 'BBBBBB',
            timeZone: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            customerSince: currentDate,
            timeZone: currentDate
          },
          returnedFromService
        );
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

      it('should delete a Customer', () => {
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
