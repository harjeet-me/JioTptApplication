import { IInsurance } from 'app/shared/model/insurance.model';
import { ITrip } from 'app/shared/model/trip.model';
import { Designation } from 'app/shared/model/enumerations/designation.model';
import { CountryEnum } from 'app/shared/model/enumerations/country-enum.model';
import { ToggleStatus } from 'app/shared/model/enumerations/toggle-status.model';
import { CURRENCY } from 'app/shared/model/enumerations/currency.model';

export interface IOwnerOperator {
  id?: number;
  company?: string;
  firstName?: string;
  lastName?: string;
  contactDesignation?: Designation;
  email?: string;
  phoneNumber?: number;
  address?: string;
  streetAddress?: string;
  city?: string;
  stateProvince?: string;
  country?: CountryEnum;
  postalCode?: string;
  dot?: string;
  mc?: number;
  profileStatus?: ToggleStatus;
  preffredCurrency?: CURRENCY;
  contractDocContentType?: string;
  contractDoc?: any;
  operInsurance?: IInsurance;
  loadOrders?: ITrip[];
}

export class OwnerOperator implements IOwnerOperator {
  constructor(
    public id?: number,
    public company?: string,
    public firstName?: string,
    public lastName?: string,
    public contactDesignation?: Designation,
    public email?: string,
    public phoneNumber?: number,
    public address?: string,
    public streetAddress?: string,
    public city?: string,
    public stateProvince?: string,
    public country?: CountryEnum,
    public postalCode?: string,
    public dot?: string,
    public mc?: number,
    public profileStatus?: ToggleStatus,
    public preffredCurrency?: CURRENCY,
    public contractDocContentType?: string,
    public contractDoc?: any,
    public operInsurance?: IInsurance,
    public loadOrders?: ITrip[]
  ) {}
}
