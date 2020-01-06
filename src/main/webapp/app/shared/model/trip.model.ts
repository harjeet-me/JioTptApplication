import { Moment } from 'moment';
import { ILocation } from 'app/shared/model/location.model';
import { IInvoice } from 'app/shared/model/invoice.model';
import { ICustomer } from 'app/shared/model/customer.model';
import { IDriver } from 'app/shared/model/driver.model';
import { IEquipment } from 'app/shared/model/equipment.model';
import { IOwnerOperator } from 'app/shared/model/owner-operator.model';
import { StatusEnum } from 'app/shared/model/enumerations/status-enum.model';
import { HAZMAT } from 'app/shared/model/enumerations/hazmat.model';
import { COVEREDBY } from 'app/shared/model/enumerations/coveredby.model';
import { LoadType } from 'app/shared/model/enumerations/load-type.model';
import { SizeEnum } from 'app/shared/model/enumerations/size-enum.model';

export interface ITrip {
  id?: number;
  tripNumber?: string;
  description?: string;
  shipmentNumber?: string;
  bol?: string;
  pickup?: Moment;
  drop?: Moment;
  pickupLocation?: string;
  dropLocation?: string;
  currentLocation?: string;
  status?: StatusEnum;
  detention?: number;
  chasisInTime?: Moment;
  podContentType?: string;
  pod?: any;
  hazmat?: HAZMAT;
  recievedBy?: string;
  coveredBy?: COVEREDBY;
  loadType?: LoadType;
  containerSize?: SizeEnum;
  numbersOfContainer?: number;
  comments?: string;
  pickupLocation?: ILocation;
  dropLocation?: ILocation;
  invoices?: IInvoice[];
  customer?: ICustomer;
  driver?: IDriver;
  equipment?: IEquipment;
  ownerOperator?: IOwnerOperator;
}

export class Trip implements ITrip {
  constructor(
    public id?: number,
    public tripNumber?: string,
    public description?: string,
    public shipmentNumber?: string,
    public bol?: string,
    public pickup?: Moment,
    public drop?: Moment,
    public pickupLocation?: string,
    public dropLocation?: string,
    public currentLocation?: string,
    public status?: StatusEnum,
    public detention?: number,
    public chasisInTime?: Moment,
    public podContentType?: string,
    public pod?: any,
    public hazmat?: HAZMAT,
    public recievedBy?: string,
    public coveredBy?: COVEREDBY,
    public loadType?: LoadType,
    public containerSize?: SizeEnum,
    public numbersOfContainer?: number,
    public comments?: string,
    public pickupLocation?: ILocation,
    public dropLocation?: ILocation,
    public invoices?: IInvoice[],
    public customer?: ICustomer,
    public driver?: IDriver,
    public equipment?: IEquipment,
    public ownerOperator?: IOwnerOperator
  ) {}
}
