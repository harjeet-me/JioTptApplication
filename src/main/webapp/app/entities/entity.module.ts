import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'company-profile',
        loadChildren: () => import('./company-profile/company-profile.module').then(m => m.JioTptApplicationCompanyProfileModule)
      },
      {
        path: 'customer',
        loadChildren: () => import('./customer/customer.module').then(m => m.JioTptApplicationCustomerModule)
      },
      {
        path: 'trip',
        loadChildren: () => import('./trip/trip.module').then(m => m.JioTptApplicationTripModule)
      },
      {
        path: 'invoice',
        loadChildren: () => import('./invoice/invoice.module').then(m => m.JioTptApplicationInvoiceModule)
      },
      {
        path: 'invoice-item',
        loadChildren: () => import('./invoice-item/invoice-item.module').then(m => m.JioTptApplicationInvoiceItemModule)
      },
      {
        path: 'accounts',
        loadChildren: () => import('./accounts/accounts.module').then(m => m.JioTptApplicationAccountsModule)
      },
      {
        path: 'transactions-record',
        loadChildren: () =>
          import('./transactions-record/transactions-record.module').then(m => m.JioTptApplicationTransactionsRecordModule)
      },
      {
        path: 'equipment',
        loadChildren: () => import('./equipment/equipment.module').then(m => m.JioTptApplicationEquipmentModule)
      },
      {
        path: 'insurance',
        loadChildren: () => import('./insurance/insurance.module').then(m => m.JioTptApplicationInsuranceModule)
      },
      {
        path: 'contact',
        loadChildren: () => import('./contact/contact.module').then(m => m.JioTptApplicationContactModule)
      },
      {
        path: 'driver',
        loadChildren: () => import('./driver/driver.module').then(m => m.JioTptApplicationDriverModule)
      },
      {
        path: 'owner-operator',
        loadChildren: () => import('./owner-operator/owner-operator.module').then(m => m.JioTptApplicationOwnerOperatorModule)
      },
      {
        path: 'location',
        loadChildren: () => import('./location/location.module').then(m => m.JioTptApplicationLocationModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class JioTptApplicationEntityModule {}
