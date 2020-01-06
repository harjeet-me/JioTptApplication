import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IOwnerOperator } from 'app/shared/model/owner-operator.model';

@Component({
  selector: 'jhi-owner-operator-detail',
  templateUrl: './owner-operator-detail.component.html'
})
export class OwnerOperatorDetailComponent implements OnInit {
  ownerOperator: IOwnerOperator | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ownerOperator }) => {
      this.ownerOperator = ownerOperator;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
