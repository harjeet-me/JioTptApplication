import { ICustomer } from 'app/shared/model/customer.model';
import { Designation } from 'app/shared/model/enumerations/designation.model';

export interface IContact {
  id?: number;
  firstName?: string;
  lastName?: string;
  contactDesignation?: Designation;
  email?: string;
  phoneNumber?: number;
  remarks?: string;
  preferredTime?: string;
  customer?: ICustomer;
}

export class Contact implements IContact {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public contactDesignation?: Designation,
    public email?: string,
    public phoneNumber?: number,
    public remarks?: string,
    public preferredTime?: string,
    public customer?: ICustomer
  ) {}
}
