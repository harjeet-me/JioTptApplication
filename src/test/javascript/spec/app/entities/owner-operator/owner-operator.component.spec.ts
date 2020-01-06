import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JioTptApplicationTestModule } from '../../../test.module';
import { OwnerOperatorComponent } from 'app/entities/owner-operator/owner-operator.component';
import { OwnerOperatorService } from 'app/entities/owner-operator/owner-operator.service';
import { OwnerOperator } from 'app/shared/model/owner-operator.model';

describe('Component Tests', () => {
  describe('OwnerOperator Management Component', () => {
    let comp: OwnerOperatorComponent;
    let fixture: ComponentFixture<OwnerOperatorComponent>;
    let service: OwnerOperatorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JioTptApplicationTestModule],
        declarations: [OwnerOperatorComponent],
        providers: []
      })
        .overrideTemplate(OwnerOperatorComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OwnerOperatorComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OwnerOperatorService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new OwnerOperator(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.ownerOperators && comp.ownerOperators[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
