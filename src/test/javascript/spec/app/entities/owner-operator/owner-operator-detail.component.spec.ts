import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { JioTptApplicationTestModule } from '../../../test.module';
import { OwnerOperatorDetailComponent } from 'app/entities/owner-operator/owner-operator-detail.component';
import { OwnerOperator } from 'app/shared/model/owner-operator.model';

describe('Component Tests', () => {
  describe('OwnerOperator Management Detail Component', () => {
    let comp: OwnerOperatorDetailComponent;
    let fixture: ComponentFixture<OwnerOperatorDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ ownerOperator: new OwnerOperator(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JioTptApplicationTestModule],
        declarations: [OwnerOperatorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OwnerOperatorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OwnerOperatorDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load ownerOperator on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ownerOperator).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
