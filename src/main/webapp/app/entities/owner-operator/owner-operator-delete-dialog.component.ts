import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOwnerOperator } from 'app/shared/model/owner-operator.model';
import { OwnerOperatorService } from './owner-operator.service';

@Component({
  templateUrl: './owner-operator-delete-dialog.component.html'
})
export class OwnerOperatorDeleteDialogComponent {
  ownerOperator?: IOwnerOperator;

  constructor(
    protected ownerOperatorService: OwnerOperatorService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ownerOperatorService.delete(id).subscribe(() => {
      this.eventManager.broadcast('ownerOperatorListModification');
      this.activeModal.close();
    });
  }
}
