import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IInvoice, Invoice } from 'app/shared/model/invoice.model';
import { InvoiceService } from './invoice.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';
import { ITrip } from 'app/shared/model/trip.model';
import { TripService } from 'app/entities/trip/trip.service';

type SelectableEntity = ICustomer | ITrip;

@Component({
  selector: 'jhi-invoice-update',
  templateUrl: './invoice-update.component.html'
})
export class InvoiceUpdateComponent implements OnInit {
  isSaving = false;

  customers: ICustomer[] = [];

  trips: ITrip[] = [];
  invoiceDateDp: any;
  invoicePaidDateDp: any;
  invoiceDueDateDp: any;

  editForm = this.fb.group({
    id: [],
    orderNo: [],
    taxRate: [],
    taxType: [],
    currency: [],
    invoiceTaxTotal: [],
    invoiceSubTotal: [],
    invoiceTotal: [],
    invoiceDate: [],
    invoicePaidDate: [],
    payRefNo: [],
    invoiceDueDate: [],
    status: [],
    invoicePdf: [],
    invoicePdfContentType: [],
    remarks: [],
    customer: [],
    trip: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected invoiceService: InvoiceService,
    protected customerService: CustomerService,
    protected tripService: TripService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ invoice }) => {
      this.updateForm(invoice);

      this.customerService
        .query()
        .pipe(
          map((res: HttpResponse<ICustomer[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ICustomer[]) => (this.customers = resBody));

      this.tripService
        .query()
        .pipe(
          map((res: HttpResponse<ITrip[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ITrip[]) => (this.trips = resBody));
    });
  }

  updateForm(invoice: IInvoice): void {
    this.editForm.patchValue({
      id: invoice.id,
      orderNo: invoice.orderNo,
      taxRate: invoice.taxRate,
      taxType: invoice.taxType,
      currency: invoice.currency,
      invoiceTaxTotal: invoice.invoiceTaxTotal,
      invoiceSubTotal: invoice.invoiceSubTotal,
      invoiceTotal: invoice.invoiceTotal,
      invoiceDate: invoice.invoiceDate,
      invoicePaidDate: invoice.invoicePaidDate,
      payRefNo: invoice.payRefNo,
      invoiceDueDate: invoice.invoiceDueDate,
      status: invoice.status,
      invoicePdf: invoice.invoicePdf,
      invoicePdfContentType: invoice.invoicePdfContentType,
      remarks: invoice.remarks,
      customer: invoice.customer,
      trip: invoice.trip
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

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const invoice = this.createFromForm();
    if (invoice.id !== undefined) {
      this.subscribeToSaveResponse(this.invoiceService.update(invoice));
    } else {
      this.subscribeToSaveResponse(this.invoiceService.create(invoice));
    }
  }

  private createFromForm(): IInvoice {
    return {
      ...new Invoice(),
      id: this.editForm.get(['id'])!.value,
      orderNo: this.editForm.get(['orderNo'])!.value,
      taxRate: this.editForm.get(['taxRate'])!.value,
      taxType: this.editForm.get(['taxType'])!.value,
      currency: this.editForm.get(['currency'])!.value,
      invoiceTaxTotal: this.editForm.get(['invoiceTaxTotal'])!.value,
      invoiceSubTotal: this.editForm.get(['invoiceSubTotal'])!.value,
      invoiceTotal: this.editForm.get(['invoiceTotal'])!.value,
      invoiceDate: this.editForm.get(['invoiceDate'])!.value,
      invoicePaidDate: this.editForm.get(['invoicePaidDate'])!.value,
      payRefNo: this.editForm.get(['payRefNo'])!.value,
      invoiceDueDate: this.editForm.get(['invoiceDueDate'])!.value,
      status: this.editForm.get(['status'])!.value,
      invoicePdfContentType: this.editForm.get(['invoicePdfContentType'])!.value,
      invoicePdf: this.editForm.get(['invoicePdf'])!.value,
      remarks: this.editForm.get(['remarks'])!.value,
      customer: this.editForm.get(['customer'])!.value,
      trip: this.editForm.get(['trip'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInvoice>>): void {
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
