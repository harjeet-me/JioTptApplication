import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOwnerOperator } from 'app/shared/model/owner-operator.model';
import { OwnerOperatorService } from './owner-operator.service';
import { OwnerOperatorDeleteDialogComponent } from './owner-operator-delete-dialog.component';

@Component({
  selector: 'jhi-owner-operator',
  templateUrl: './owner-operator.component.html'
})
export class OwnerOperatorComponent implements OnInit, OnDestroy {
  ownerOperators?: IOwnerOperator[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected ownerOperatorService: OwnerOperatorService,
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
      this.ownerOperatorService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IOwnerOperator[]>) => (this.ownerOperators = res.body ? res.body : []));
      return;
    }
    this.ownerOperatorService.query().subscribe((res: HttpResponse<IOwnerOperator[]>) => {
      this.ownerOperators = res.body ? res.body : [];
      this.currentSearch = '';
    });
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInOwnerOperators();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IOwnerOperator): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInOwnerOperators(): void {
    this.eventSubscriber = this.eventManager.subscribe('ownerOperatorListModification', () => this.loadAll());
  }

  delete(ownerOperator: IOwnerOperator): void {
    const modalRef = this.modalService.open(OwnerOperatorDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ownerOperator = ownerOperator;
  }
}
