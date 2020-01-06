import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITransactionsRecord } from 'app/shared/model/transactions-record.model';
import { TransactionsRecordService } from './transactions-record.service';
import { TransactionsRecordDeleteDialogComponent } from './transactions-record-delete-dialog.component';

@Component({
  selector: 'jhi-transactions-record',
  templateUrl: './transactions-record.component.html'
})
export class TransactionsRecordComponent implements OnInit, OnDestroy {
  transactionsRecords?: ITransactionsRecord[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected transactionsRecordService: TransactionsRecordService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.transactionsRecordService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ITransactionsRecord[]>) => (this.transactionsRecords = res.body ? res.body : []));
      return;
    }
    this.transactionsRecordService.query().subscribe((res: HttpResponse<ITransactionsRecord[]>) => {
      this.transactionsRecords = res.body ? res.body : [];
      this.currentSearch = '';
    });
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTransactionsRecords();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITransactionsRecord): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInTransactionsRecords(): void {
    this.eventSubscriber = this.eventManager.subscribe('transactionsRecordListModification', () => this.loadAll());
  }

  delete(transactionsRecord: ITransactionsRecord): void {
    const modalRef = this.modalService.open(TransactionsRecordDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.transactionsRecord = transactionsRecord;
  }
}
