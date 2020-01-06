import { Moment } from 'moment';
import { ICustomer } from 'app/shared/model/customer.model';
import { IAccounts } from 'app/shared/model/accounts.model';
import { TransactionType } from 'app/shared/model/enumerations/transaction-type.model';
import { TxStatus } from 'app/shared/model/enumerations/tx-status.model';
import { CURRENCY } from 'app/shared/model/enumerations/currency.model';

export interface ITransactionsRecord {
  id?: number;
  txType?: TransactionType;
  description?: string;
  txAmmount?: number;
  txRefNo?: string;
  txCreatedBy?: string;
  txCreatedDate?: Moment;
  txCompletedBy?: string;
  txCompletedDate?: Moment;
  status?: TxStatus;
  txDocContentType?: string;
  txDoc?: any;
  currency?: CURRENCY;
  remarks?: string;
  customer?: ICustomer;
  customer?: IAccounts;
}

export class TransactionsRecord implements ITransactionsRecord {
  constructor(
    public id?: number,
    public txType?: TransactionType,
    public description?: string,
    public txAmmount?: number,
    public txRefNo?: string,
    public txCreatedBy?: string,
    public txCreatedDate?: Moment,
    public txCompletedBy?: string,
    public txCompletedDate?: Moment,
    public status?: TxStatus,
    public txDocContentType?: string,
    public txDoc?: any,
    public currency?: CURRENCY,
    public remarks?: string,
    public customer?: ICustomer,
    public customer?: IAccounts
  ) {}
}
