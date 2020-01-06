import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ITrip, Trip } from 'app/shared/model/trip.model';
import { TripService } from './trip.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ILocation } from 'app/shared/model/location.model';
import { LocationService } from 'app/entities/location/location.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';
import { IDriver } from 'app/shared/model/driver.model';
import { DriverService } from 'app/entities/driver/driver.service';
import { IEquipment } from 'app/shared/model/equipment.model';
import { EquipmentService } from 'app/entities/equipment/equipment.service';
import { IOwnerOperator } from 'app/shared/model/owner-operator.model';
import { OwnerOperatorService } from 'app/entities/owner-operator/owner-operator.service';

type SelectableEntity = ILocation | ICustomer | IDriver | IEquipment | IOwnerOperator;

@Component({
  selector: 'jhi-trip-update',
  templateUrl: './trip-update.component.html'
})
export class TripUpdateComponent implements OnInit {
  isSaving = false;

  pickuplocations: ILocation[] = [];

  droplocations: ILocation[] = [];

  customers: ICustomer[] = [];

  drivers: IDriver[] = [];

  equipment: IEquipment[] = [];

  owneroperators: IOwnerOperator[] = [];
  pickupDp: any;
  dropDp: any;

  editForm = this.fb.group({
    id: [],
    tripNumber: [],
    description: [],
    shipmentNumber: [],
    bol: [],
    pickup: [],
    drop: [],
    pickupLocation: [],
    dropLocation: [],
    currentLocation: [],
    status: [],
    detention: [],
    chasisInTime: [],
    pod: [],
    podContentType: [],
    hazmat: [],
    recievedBy: [],
    coveredBy: [],
    loadType: [],
    containerSize: [],
    numbersOfContainer: [],
    comments: [],
    pickupLocation: [],
    dropLocation: [],
    customer: [],
    driver: [],
    equipment: [],
    ownerOperator: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected tripService: TripService,
    protected locationService: LocationService,
    protected customerService: CustomerService,
    protected driverService: DriverService,
    protected equipmentService: EquipmentService,
    protected ownerOperatorService: OwnerOperatorService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trip }) => {
      this.updateForm(trip);

      this.locationService
        .query({ filter: 'trip-is-null' })
        .pipe(
          map((res: HttpResponse<ILocation[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ILocation[]) => {
          if (!trip.pickupLocation || !trip.pickupLocation.id) {
            this.pickuplocations = resBody;
          } else {
            this.locationService
              .find(trip.pickupLocation.id)
              .pipe(
                map((subRes: HttpResponse<ILocation>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ILocation[]) => {
                this.pickuplocations = concatRes;
              });
          }
        });

      this.locationService
        .query({ filter: 'trip-is-null' })
        .pipe(
          map((res: HttpResponse<ILocation[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ILocation[]) => {
          if (!trip.dropLocation || !trip.dropLocation.id) {
            this.droplocations = resBody;
          } else {
            this.locationService
              .find(trip.dropLocation.id)
              .pipe(
                map((subRes: HttpResponse<ILocation>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ILocation[]) => {
                this.droplocations = concatRes;
              });
          }
        });

      this.customerService
        .query()
        .pipe(
          map((res: HttpResponse<ICustomer[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ICustomer[]) => (this.customers = resBody));

      this.driverService
        .query()
        .pipe(
          map((res: HttpResponse<IDriver[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IDriver[]) => (this.drivers = resBody));

      this.equipmentService
        .query()
        .pipe(
          map((res: HttpResponse<IEquipment[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IEquipment[]) => (this.equipment = resBody));

      this.ownerOperatorService
        .query()
        .pipe(
          map((res: HttpResponse<IOwnerOperator[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IOwnerOperator[]) => (this.owneroperators = resBody));
    });
  }

  updateForm(trip: ITrip): void {
    this.editForm.patchValue({
      id: trip.id,
      tripNumber: trip.tripNumber,
      description: trip.description,
      shipmentNumber: trip.shipmentNumber,
      bol: trip.bol,
      pickup: trip.pickup,
      drop: trip.drop,
      pickupLocation: trip.pickupLocation,
      dropLocation: trip.dropLocation,
      currentLocation: trip.currentLocation,
      status: trip.status,
      detention: trip.detention,
      chasisInTime: trip.chasisInTime != null ? trip.chasisInTime.format(DATE_TIME_FORMAT) : null,
      pod: trip.pod,
      podContentType: trip.podContentType,
      hazmat: trip.hazmat,
      recievedBy: trip.recievedBy,
      coveredBy: trip.coveredBy,
      loadType: trip.loadType,
      containerSize: trip.containerSize,
      numbersOfContainer: trip.numbersOfContainer,
      comments: trip.comments,
      pickupLocation: trip.pickupLocation,
      dropLocation: trip.dropLocation,
      customer: trip.customer,
      driver: trip.driver,
      equipment: trip.equipment,
      ownerOperator: trip.ownerOperator
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('jioTptApplicationApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const trip = this.createFromForm();
    if (trip.id !== undefined) {
      this.subscribeToSaveResponse(this.tripService.update(trip));
    } else {
      this.subscribeToSaveResponse(this.tripService.create(trip));
    }
  }

  private createFromForm(): ITrip {
    return {
      ...new Trip(),
      id: this.editForm.get(['id'])!.value,
      tripNumber: this.editForm.get(['tripNumber'])!.value,
      description: this.editForm.get(['description'])!.value,
      shipmentNumber: this.editForm.get(['shipmentNumber'])!.value,
      bol: this.editForm.get(['bol'])!.value,
      pickup: this.editForm.get(['pickup'])!.value,
      drop: this.editForm.get(['drop'])!.value,
      pickupLocation: this.editForm.get(['pickupLocation'])!.value,
      dropLocation: this.editForm.get(['dropLocation'])!.value,
      currentLocation: this.editForm.get(['currentLocation'])!.value,
      status: this.editForm.get(['status'])!.value,
      detention: this.editForm.get(['detention'])!.value,
      chasisInTime:
        this.editForm.get(['chasisInTime'])!.value != null
          ? moment(this.editForm.get(['chasisInTime'])!.value, DATE_TIME_FORMAT)
          : undefined,
      podContentType: this.editForm.get(['podContentType'])!.value,
      pod: this.editForm.get(['pod'])!.value,
      hazmat: this.editForm.get(['hazmat'])!.value,
      recievedBy: this.editForm.get(['recievedBy'])!.value,
      coveredBy: this.editForm.get(['coveredBy'])!.value,
      loadType: this.editForm.get(['loadType'])!.value,
      containerSize: this.editForm.get(['containerSize'])!.value,
      numbersOfContainer: this.editForm.get(['numbersOfContainer'])!.value,
      comments: this.editForm.get(['comments'])!.value,
      pickupLocation: this.editForm.get(['pickupLocation'])!.value,
      dropLocation: this.editForm.get(['dropLocation'])!.value,
      customer: this.editForm.get(['customer'])!.value,
      driver: this.editForm.get(['driver'])!.value,
      equipment: this.editForm.get(['equipment'])!.value,
      ownerOperator: this.editForm.get(['ownerOperator'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrip>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
