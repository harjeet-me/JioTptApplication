import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ICustomer, Customer } from 'app/shared/model/customer.model';
import { CustomerService } from './customer.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-customer-update',
  templateUrl: './customer-update.component.html'
})
export class CustomerUpdateComponent implements OnInit {
  isSaving = false;
  customerSinceDp: any;

  editForm = this.fb.group({
    id: [],
    company: [],
    firstName: [],
    lastName: [],
    contactDesignation: [],
    email: [],
    phoneNumber: [],
    preffredContactType: [],
    website: [],
    secondaryContactPerson: [],
    secContactNumber: [],
    secContactEmail: [],
    secContactPreContactTime: [],
    fax: [],
    address: [],
    streetAddress: [],
    city: [],
    stateProvince: [],
    country: [],
    postalCode: [],
    dot: [],
    mc: [],
    companyLogo: [],
    companyLogoContentType: [],
    customerSince: [],
    remarks: [],
    contract: [],
    contractContentType: [],
    status: [],
    preffredCurrency: [],
    payterms: [],
    timeZone: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected customerService: CustomerService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customer }) => {
      this.updateForm(customer);
    });
  }

  updateForm(customer: ICustomer): void {
    this.editForm.patchValue({
      id: customer.id,
      company: customer.company,
      firstName: customer.firstName,
      lastName: customer.lastName,
      contactDesignation: customer.contactDesignation,
      email: customer.email,
      phoneNumber: customer.phoneNumber,
      preffredContactType: customer.preffredContactType,
      website: customer.website,
      secondaryContactPerson: customer.secondaryContactPerson,
      secContactNumber: customer.secContactNumber,
      secContactEmail: customer.secContactEmail,
      secContactPreContactTime: customer.secContactPreContactTime,
      fax: customer.fax,
      address: customer.address,
      streetAddress: customer.streetAddress,
      city: customer.city,
      stateProvince: customer.stateProvince,
      country: customer.country,
      postalCode: customer.postalCode,
      dot: customer.dot,
      mc: customer.mc,
      companyLogo: customer.companyLogo,
      companyLogoContentType: customer.companyLogoContentType,
      customerSince: customer.customerSince,
      remarks: customer.remarks,
      contract: customer.contract,
      contractContentType: customer.contractContentType,
      status: customer.status,
      preffredCurrency: customer.preffredCurrency,
      payterms: customer.payterms,
      timeZone: customer.timeZone != null ? customer.timeZone.format(DATE_TIME_FORMAT) : null
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
    const customer = this.createFromForm();
    if (customer.id !== undefined) {
      this.subscribeToSaveResponse(this.customerService.update(customer));
    } else {
      this.subscribeToSaveResponse(this.customerService.create(customer));
    }
  }

  private createFromForm(): ICustomer {
    return {
      ...new Customer(),
      id: this.editForm.get(['id'])!.value,
      company: this.editForm.get(['company'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      contactDesignation: this.editForm.get(['contactDesignation'])!.value,
      email: this.editForm.get(['email'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      preffredContactType: this.editForm.get(['preffredContactType'])!.value,
      website: this.editForm.get(['website'])!.value,
      secondaryContactPerson: this.editForm.get(['secondaryContactPerson'])!.value,
      secContactNumber: this.editForm.get(['secContactNumber'])!.value,
      secContactEmail: this.editForm.get(['secContactEmail'])!.value,
      secContactPreContactTime: this.editForm.get(['secContactPreContactTime'])!.value,
      fax: this.editForm.get(['fax'])!.value,
      address: this.editForm.get(['address'])!.value,
      streetAddress: this.editForm.get(['streetAddress'])!.value,
      city: this.editForm.get(['city'])!.value,
      stateProvince: this.editForm.get(['stateProvince'])!.value,
      country: this.editForm.get(['country'])!.value,
      postalCode: this.editForm.get(['postalCode'])!.value,
      dot: this.editForm.get(['dot'])!.value,
      mc: this.editForm.get(['mc'])!.value,
      companyLogoContentType: this.editForm.get(['companyLogoContentType'])!.value,
      companyLogo: this.editForm.get(['companyLogo'])!.value,
      customerSince: this.editForm.get(['customerSince'])!.value,
      remarks: this.editForm.get(['remarks'])!.value,
      contractContentType: this.editForm.get(['contractContentType'])!.value,
      contract: this.editForm.get(['contract'])!.value,
      status: this.editForm.get(['status'])!.value,
      preffredCurrency: this.editForm.get(['preffredCurrency'])!.value,
      payterms: this.editForm.get(['payterms'])!.value,
      timeZone:
        this.editForm.get(['timeZone'])!.value != null ? moment(this.editForm.get(['timeZone'])!.value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomer>>): void {
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
}
