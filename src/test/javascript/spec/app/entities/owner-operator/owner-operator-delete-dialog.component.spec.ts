import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JioTptApplicationTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { OwnerOperatorDeleteDialogComponent } from 'app/entities/owner-operator/owner-operator-delete-dialog.component';
import { OwnerOperatorService } from 'app/entities/owner-operator/owner-operator.service';

describe('Component Tests', () => {
  describe('OwnerOperator Management Delete Component', () => {
    let comp: OwnerOperatorDeleteDialogComponent;
    let fixture: ComponentFixture<OwnerOperatorDeleteDialogComponent>;
    let service: OwnerOperatorService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JioTptApplicationTestModule],
        declarations: [OwnerOperatorDeleteDialogComponent]
      })
        .overrideTemplate(OwnerOperatorDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OwnerOperatorDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OwnerOperatorService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.clear();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
