import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JioTptApplicationTestModule } from '../../../test.module';
import { OwnerOperatorUpdateComponent } from 'app/entities/owner-operator/owner-operator-update.component';
import { OwnerOperatorService } from 'app/entities/owner-operator/owner-operator.service';
import { OwnerOperator } from 'app/shared/model/owner-operator.model';

describe('Component Tests', () => {
  describe('OwnerOperator Management Update Component', () => {
    let comp: OwnerOperatorUpdateComponent;
    let fixture: ComponentFixture<OwnerOperatorUpdateComponent>;
    let service: OwnerOperatorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JioTptApplicationTestModule],
        declarations: [OwnerOperatorUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OwnerOperatorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OwnerOperatorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OwnerOperatorService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OwnerOperator(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new OwnerOperator();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
