import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ITransactionsRecord, TransactionsRecord } from 'app/shared/model/transactions-record.model';
import { TransactionsRecordService } from './transactions-record.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';
import { IAccounts } from 'app/shared/model/accounts.model';
import { AccountsService } from 'app/entities/accounts/accounts.service';

type SelectableEntity = ICustomer | IAccounts;

@Component({
  selector: 'jhi-transactions-record-update',
  templateUrl: './transactions-record-update.component.html'
})
export class TransactionsRecordUpdateComponent implements OnInit {
  isSaving = false;

  customers: ICustomer[] = [];

  accounts: IAccounts[] = [];
  txCreatedDateDp: any;
  txCompletedDateDp: any;

  editForm = this.fb.group({
    id: [],
    txType: [],
    description: [],
    txAmmount: [],
    txRefNo: [],
    txCreatedBy: [],
    txCreatedDate: [],
    txCompletedBy: [],
    txCompletedDate: [],
    status: [],
    txDoc: [],
    txDocContentType: [],
    currency: [],
    remarks: [],
    customer: [],
    customer: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected transactionsRecordService: TransactionsRecordService,
    protected customerService: CustomerService,
    protected accountsService: AccountsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transactionsRecord }) => {
      this.updateForm(transactionsRecord);

      this.customerService
        .query()
        .pipe(
          map((res: HttpResponse<ICustomer[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ICustomer[]) => (this.customers = resBody));

      this.accountsService
        .query()
        .pipe(
          map((res: HttpResponse<IAccounts[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IAccounts[]) => (this.accounts = resBody));
    });
  }

  updateForm(transactionsRecord: ITransactionsRecord): void {
    this.editForm.patchValue({
      id: transactionsRecord.id,
      txType: transactionsRecord.txType,
      description: transactionsRecord.description,
      txAmmount: transactionsRecord.txAmmount,
      txRefNo: transactionsRecord.txRefNo,
      txCreatedBy: transactionsRecord.txCreatedBy,
      txCreatedDate: transactionsRecord.txCreatedDate,
      txCompletedBy: transactionsRecord.txCompletedBy,
      txCompletedDate: transactionsRecord.txCompletedDate,
      status: transactionsRecord.status,
      txDoc: transactionsRecord.txDoc,
      txDocContentType: transactionsRecord.txDocContentType,
      currency: transactionsRecord.currency,
      remarks: transactionsRecord.remarks,
      customer: transactionsRecord.customer,
      customer: transactionsRecord.customer
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
    const transactionsRecord = this.createFromForm();
    if (transactionsRecord.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionsRecordService.update(transactionsRecord));
    } else {
      this.subscribeToSaveResponse(this.transactionsRecordService.create(transactionsRecord));
    }
  }

  private createFromForm(): ITransactionsRecord {
    return {
      ...new TransactionsRecord(),
      id: this.editForm.get(['id'])!.value,
      txType: this.editForm.get(['txType'])!.value,
      description: this.editForm.get(['description'])!.value,
      txAmmount: this.editForm.get(['txAmmount'])!.value,
      txRefNo: this.editForm.get(['txRefNo'])!.value,
      txCreatedBy: this.editForm.get(['txCreatedBy'])!.value,
      txCreatedDate: this.editForm.get(['txCreatedDate'])!.value,
      txCompletedBy: this.editForm.get(['txCompletedBy'])!.value,
      txCompletedDate: this.editForm.get(['txCompletedDate'])!.value,
      status: this.editForm.get(['status'])!.value,
      txDocContentType: this.editForm.get(['txDocContentType'])!.value,
      txDoc: this.editForm.get(['txDoc'])!.value,
      currency: this.editForm.get(['currency'])!.value,
      remarks: this.editForm.get(['remarks'])!.value,
      customer: this.editForm.get(['customer'])!.value,
      customer: this.editForm.get(['customer'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransactionsRecord>>): void {
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
