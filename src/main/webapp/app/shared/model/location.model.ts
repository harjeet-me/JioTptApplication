import { CountryEnum } from 'app/shared/model/enumerations/country-enum.model';

export interface ILocation {
  id?: number;
  address?: string;
  streetAddress?: string;
  city?: string;
  stateProvince?: string;
  country?: CountryEnum;
  postalCode?: string;
  latitude?: number;
  longitude?: number;
}

export class Location implements ILocation {
  constructor(
    public id?: number,
    public address?: string,
    public streetAddress?: string,
    public city?: string,
    public stateProvince?: string,
    public country?: CountryEnum,
    public postalCode?: string,
    public latitude?: number,
    public longitude?: number
  ) {}
}
