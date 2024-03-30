import dayjs from 'dayjs/esm';

import { IInventaire, NewInventaire } from './inventaire.model';

export const sampleWithRequiredData: IInventaire = {
  id: '8ef409ff-e4a6-4dbc-91d7-bf4e2bcc0962',
  quantite: 4446,
  inventaireDate: dayjs('2024-03-29T17:06'),
  createdBy: 'antique appuyer',
};

export const sampleWithPartialData: IInventaire = {
  id: '8b08a258-dd09-4c5b-8d96-dc3360f2fd3b',
  quantite: 30257,
  inventaireDate: dayjs('2024-03-29T21:05'),
  deleteAt: dayjs('2024-03-30T10:30'),
  createdBy: 'lors',
  createdDate: dayjs('2024-03-30T11:15'),
};

export const sampleWithFullData: IInventaire = {
  id: '9ced210f-aa66-4e82-864b-96af8ce18c56',
  quantite: 15389,
  inventaireDate: dayjs('2024-03-29T20:04'),
  deleteAt: dayjs('2024-03-30T03:09'),
  createdBy: 'tant que du fait que',
  createdDate: dayjs('2024-03-30T09:38'),
  lastModifiedBy: 'ronron imposer',
  lastModifiedDate: dayjs('2024-03-29T20:22'),
};

export const sampleWithNewData: NewInventaire = {
  quantite: 26234,
  inventaireDate: dayjs('2024-03-30T10:16'),
  createdBy: 'insipide administration calme',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
