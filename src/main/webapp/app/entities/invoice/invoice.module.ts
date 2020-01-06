import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JioTptApplicationSharedModule } from 'app/shared/shared.module';
import { InvoiceComponent } from './invoice.component';
import { InvoiceDetailComponent } from './invoice-detail.component';
import { InvoiceUpdateComponent } from './invoice-update.component';
import { InvoiceDeleteDialogComponent } from './invoice-delete-dialog.component';
import { invoiceRoute } from './invoice.route';

@NgModule({
  imports: [JioTptApplicationSharedModule, RouterModule.forChild(invoiceRoute)],
  declarations: [InvoiceComponent, InvoiceDetailComponent, InvoiceUpdateComponent, InvoiceDeleteDialogComponent],
  entryComponents: [InvoiceDeleteDialogComponent]
})
export class JioTptApplicationInvoiceModule {}
