import { Moment } from 'moment';
import { ITrip } from 'app/shared/model/trip.model';
import { IInvoice } from 'app/shared/model/invoice.model';
import { IContact } from 'app/shared/model/contact.model';
import { ITransactionsRecord } from 'app/shared/model/transactions-record.model';
import { IAccounts } from 'app/shared/model/accounts.model';
import { Designation } from 'app/shared/model/enumerations/designation.model';
import { PreffredContactType } from 'app/shared/model/enumerations/preffred-contact-type.model';
import { CountryEnum } from 'app/shared/model/enumerations/country-enum.model';
import { ToggleStatus } from 'app/shared/model/enumerations/toggle-status.model';
import { CURRENCY } from 'app/shared/model/enumerations/currency.model';

export interface ICustomer {
  id?: number;
  company?: string;
  firstName?: string;
  lastName?: string;
  contactDesignation?: Designation;
  email?: string;
  phoneNumber?: number;
  preffredContactType?: PreffredContactType;
  website?: string;
  secondaryContactPerson?: string;
  secContactNumber?: number;
  secContactEmail?: string;
  secContactPreContactTime?: string;
  fax?: number;
  address?: string;
  streetAddress?: string;
  city?: string;
  stateProvince?: string;
  country?: CountryEnum;
  postalCode?: string;
  dot?: string;
  mc?: number;
  companyLogoContentType?: string;
  companyLogo?: any;
  customerSince?: Moment;
  remarks?: string;
  contractContentType?: string;
  contract?: any;
  status?: ToggleStatus;
  preffredCurrency?: CURRENCY;
  payterms?: string;
  timeZone?: Moment;
  loadOrders?: ITrip[];
  invoices?: IInvoice[];
  morecontacts?: IContact[];
  transactionsRecords?: ITransactionsRecord[];
  accounts?: IAccounts;
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public company?: string,
    public firstName?: string,
    public lastName?: string,
    public contactDesignation?: Designation,
    public email?: string,
    public phoneNumber?: number,
    public preffredContactType?: PreffredContactType,
    public website?: string,
    public secondaryContactPerson?: string,
    public secContactNumber?: number,
    public secContactEmail?: string,
    public secContactPreContactTime?: string,
    public fax?: number,
    public address?: string,
    public streetAddress?: string,
    public city?: string,
    public stateProvince?: string,
    public country?: CountryEnum,
    public postalCode?: string,
    public dot?: string,
    public mc?: number,
    public companyLogoContentType?: string,
    public companyLogo?: any,
    public customerSince?: Moment,
    public remarks?: string,
    public contractContentType?: string,
    public contract?: any,
    public status?: ToggleStatus,
    public preffredCurrency?: CURRENCY,
    public payterms?: string,
    public timeZone?: Moment,
    public loadOrders?: ITrip[],
    public invoices?: IInvoice[],
    public morecontacts?: IContact[],
    public transactionsRecords?: ITransactionsRecord[],
    public accounts?: IAccounts
  ) {}
}
