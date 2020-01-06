import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IOwnerOperator, OwnerOperator } from 'app/shared/model/owner-operator.model';
import { OwnerOperatorService } from './owner-operator.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IInsurance } from 'app/shared/model/insurance.model';
import { InsuranceService } from 'app/entities/insurance/insurance.service';

@Component({
  selector: 'jhi-owner-operator-update',
  templateUrl: './owner-operator-update.component.html'
})
export class OwnerOperatorUpdateComponent implements OnInit {
  isSaving = false;

  operinsurances: IInsurance[] = [];

  editForm = this.fb.group({
    id: [],
    company: [],
    firstName: [],
    lastName: [],
    contactDesignation: [],
    email: [],
    phoneNumber: [],
    address: [],
    streetAddress: [],
    city: [],
    stateProvince: [],
    country: [],
    postalCode: [],
    dot: [],
    mc: [],
    profileStatus: [],
    preffredCurrency: [],
    contractDoc: [],
    contractDocContentType: [],
    operInsurance: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected ownerOperatorService: OwnerOperatorService,
    protected insuranceService: InsuranceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ownerOperator }) => {
      this.updateForm(ownerOperator);

      this.insuranceService
        .query({ filter: 'owneroperator-is-null' })
        .pipe(
          map((res: HttpResponse<IInsurance[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IInsurance[]) => {
          if (!ownerOperator.operInsurance || !ownerOperator.operInsurance.id) {
            this.operinsurances = resBody;
          } else {
            this.insuranceService
              .find(ownerOperator.operInsurance.id)
              .pipe(
                map((subRes: HttpResponse<IInsurance>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IInsurance[]) => {
                this.operinsurances = concatRes;
              });
          }
        });
    });
  }

  updateForm(ownerOperator: IOwnerOperator): void {
    this.editForm.patchValue({
      id: ownerOperator.id,
      company: ownerOperator.company,
      firstName: ownerOperator.firstName,
      lastName: ownerOperator.lastName,
      contactDesignation: ownerOperator.contactDesignation,
      email: ownerOperator.email,
      phoneNumber: ownerOperator.phoneNumber,
      address: ownerOperator.address,
      streetAddress: ownerOperator.streetAddress,
      city: ownerOperator.city,
      stateProvince: ownerOperator.stateProvince,
      country: ownerOperator.country,
      postalCode: ownerOperator.postalCode,
      dot: ownerOperator.dot,
      mc: ownerOperator.mc,
      profileStatus: ownerOperator.profileStatus,
      preffredCurrency: ownerOperator.preffredCurrency,
      contractDoc: ownerOperator.contractDoc,
      contractDocContentType: ownerOperator.contractDocContentType,
      operInsurance: ownerOperator.operInsurance
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
    const ownerOperator = this.createFromForm();
    if (ownerOperator.id !== undefined) {
      this.subscribeToSaveResponse(this.ownerOperatorService.update(ownerOperator));
    } else {
      this.subscribeToSaveResponse(this.ownerOperatorService.create(ownerOperator));
    }
  }

  private createFromForm(): IOwnerOperator {
    return {
      ...new OwnerOperator(),
      id: this.editForm.get(['id'])!.value,
      company: this.editForm.get(['company'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      contactDesignation: this.editForm.get(['contactDesignation'])!.value,
      email: this.editForm.get(['email'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      address: this.editForm.get(['address'])!.value,
      streetAddress: this.editForm.get(['streetAddress'])!.value,
      city: this.editForm.get(['city'])!.value,
      stateProvince: this.editForm.get(['stateProvince'])!.value,
      country: this.editForm.get(['country'])!.value,
      postalCode: this.editForm.get(['postalCode'])!.value,
      dot: this.editForm.get(['dot'])!.value,
      mc: this.editForm.get(['mc'])!.value,
      profileStatus: this.editForm.get(['profileStatus'])!.value,
      preffredCurrency: this.editForm.get(['preffredCurrency'])!.value,
      contractDocContentType: this.editForm.get(['contractDocContentType'])!.value,
      contractDoc: this.editForm.get(['contractDoc'])!.value,
      operInsurance: this.editForm.get(['operInsurance'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOwnerOperator>>): void {
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

  trackById(index: number, item: IInsurance): any {
    return item.id;
  }
}
