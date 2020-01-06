import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JioTptApplicationTestModule } from '../../../test.module';
import { TransactionsRecordComponent } from 'app/entities/transactions-record/transactions-record.component';
import { TransactionsRecordService } from 'app/entities/transactions-record/transactions-record.service';
import { TransactionsRecord } from 'app/shared/model/transactions-record.model';

describe('Component Tests', () => {
  describe('TransactionsRecord Management Component', () => {
    let comp: TransactionsRecordComponent;
    let fixture: ComponentFixture<TransactionsRecordComponent>;
    let service: TransactionsRecordService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JioTptApplicationTestModule],
        declarations: [TransactionsRecordComponent],
        providers: []
      })
        .overrideTemplate(TransactionsRecordComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransactionsRecordComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransactionsRecordService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TransactionsRecord(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.transactionsRecords && comp.transactionsRecords[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
