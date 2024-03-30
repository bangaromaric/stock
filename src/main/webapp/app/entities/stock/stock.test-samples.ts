import dayjs from 'dayjs/esm';

import { IStock, NewStock } from './stock.model';

export const sampleWithRequiredData: IStock = {
  id: '1703924c-cc08-4562-a84c-ab8b7ce1d07a',
  quantite: 670,
  createdBy: 'aussitôt que de façon à ce que',
};

export const sampleWithPartialData: IStock = {
  id: '58e280cc-f393-4467-b462-20d5bffb7435',
  quantite: 6724,
  createdBy: 'croâ derechef',
};

export const sampleWithFullData: IStock = {
  id: '0708e949-c066-4e71-a80f-5d32f2687c31',
  quantite: 17945,
  deleteAt: dayjs('2024-03-30T00:28'),
  createdBy: 'précisément quand',
  createdDate: dayjs('2024-03-30T10:13'),
  lastModifiedBy: 'dominer loin de simple',
  lastModifiedDate: dayjs('2024-03-30T04:17'),
};

export const sampleWithNewData: NewStock = {
  quantite: 27054,
  createdBy: 'bzzz volontiers',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
