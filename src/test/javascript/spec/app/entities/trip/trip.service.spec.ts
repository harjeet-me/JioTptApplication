import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TripService } from 'app/entities/trip/trip.service';
import { ITrip, Trip } from 'app/shared/model/trip.model';
import { StatusEnum } from 'app/shared/model/enumerations/status-enum.model';
import { HAZMAT } from 'app/shared/model/enumerations/hazmat.model';
import { COVEREDBY } from 'app/shared/model/enumerations/coveredby.model';
import { LoadType } from 'app/shared/model/enumerations/load-type.model';
import { SizeEnum } from 'app/shared/model/enumerations/size-enum.model';

describe('Service Tests', () => {
  describe('Trip Service', () => {
    let injector: TestBed;
    let service: TripService;
    let httpMock: HttpTestingController;
    let elemDefault: ITrip;
    let expectedResult: ITrip | ITrip[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TripService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Trip(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        StatusEnum.PICKEDUP,
        0,
        currentDate,
        'image/png',
        'AAAAAAA',
        HAZMAT.YES,
        'AAAAAAA',
        COVEREDBY.CompanyDriver,
        LoadType.REEFER,
        SizeEnum.C53,
        0,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            pickup: currentDate.format(DATE_FORMAT),
            drop: currentDate.format(DATE_FORMAT),
            chasisInTime: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a Trip', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            pickup: currentDate.format(DATE_FORMAT),
            drop: currentDate.format(DATE_FORMAT),
            chasisInTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            pickup: currentDate,
            drop: currentDate,
            chasisInTime: currentDate
          },
          returnedFromService
        );
        service
          .create(new Trip())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Trip', () => {
        const returnedFromService = Object.assign(
          {
            tripNumber: 'BBBBBB',
            description: 'BBBBBB',
            shipmentNumber: 'BBBBBB',
            bol: 'BBBBBB',
            pickup: currentDate.format(DATE_FORMAT),
            drop: currentDate.format(DATE_FORMAT),
            pickupLocation: 'BBBBBB',
            dropLocation: 'BBBBBB',
            currentLocation: 'BBBBBB',
            status: 'BBBBBB',
            detention: 1,
            chasisInTime: currentDate.format(DATE_TIME_FORMAT),
            pod: 'BBBBBB',
            hazmat: 'BBBBBB',
            recievedBy: 'BBBBBB',
            coveredBy: 'BBBBBB',
            loadType: 'BBBBBB',
            containerSize: 'BBBBBB',
            numbersOfContainer: 1,
            comments: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            pickup: currentDate,
            drop: currentDate,
            chasisInTime: currentDate
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

      it('should return a list of Trip', () => {
        const returnedFromService = Object.assign(
          {
            tripNumber: 'BBBBBB',
            description: 'BBBBBB',
            shipmentNumber: 'BBBBBB',
            bol: 'BBBBBB',
            pickup: currentDate.format(DATE_FORMAT),
            drop: currentDate.format(DATE_FORMAT),
            pickupLocation: 'BBBBBB',
            dropLocation: 'BBBBBB',
            currentLocation: 'BBBBBB',
            status: 'BBBBBB',
            detention: 1,
            chasisInTime: currentDate.format(DATE_TIME_FORMAT),
            pod: 'BBBBBB',
            hazmat: 'BBBBBB',
            recievedBy: 'BBBBBB',
            coveredBy: 'BBBBBB',
            loadType: 'BBBBBB',
            containerSize: 'BBBBBB',
            numbersOfContainer: 1,
            comments: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            pickup: currentDate,
            drop: currentDate,
            chasisInTime: currentDate
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

      it('should delete a Trip', () => {
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
