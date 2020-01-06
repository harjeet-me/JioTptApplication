import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { JioTptApplicationTestModule } from '../../../test.module';
import { InsuranceDetailComponent } from 'app/entities/insurance/insurance-detail.component';
import { Insurance } from 'app/shared/model/insurance.model';

describe('Component Tests', () => {
  describe('Insurance Management Detail Component', () => {
    let comp: InsuranceDetailComponent;
    let fixture: ComponentFixture<InsuranceDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ insurance: new Insurance(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JioTptApplicationTestModule],
        declarations: [InsuranceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InsuranceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InsuranceDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load insurance on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.insurance).toEqual(jasmine.objectContaining({ id: 123 }));
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
