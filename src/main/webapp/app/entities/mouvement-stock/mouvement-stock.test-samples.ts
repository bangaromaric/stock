import dayjs from 'dayjs/esm';

import { IMouvementStock, NewMouvementStock } from './mouvement-stock.model';

export const sampleWithRequiredData: IMouvementStock = {
  id: '7ba0ee36-f14e-4f52-9023-72773090b9e7',
  quantite: 2065,
  typeMouvement: 'SORTIE',
  transactionDate: dayjs('2024-03-29T16:41'),
  createdBy: 'parce que',
};

export const sampleWithPartialData: IMouvementStock = {
  id: 'd91741b3-12aa-4b20-804a-461b5f3bcc4d',
  quantite: 6743,
  typeMouvement: 'ENTREE',
  transactionDate: dayjs('2024-03-30T08:51'),
  deleteAt: dayjs('2024-03-30T06:25'),
  createdBy: 'souple',
  lastModifiedBy: 'relier certainement rester',
  lastModifiedDate: dayjs('2024-03-30T06:00'),
};

export const sampleWithFullData: IMouvementStock = {
  id: 'c04a3648-d771-47a5-87b7-c363282fa62c',
  quantite: 6397,
  typeMouvement: 'ENTREE',
  transactionDate: dayjs('2024-03-30T09:30'),
  deleteAt: dayjs('2024-03-29T19:28'),
  createdBy: 'éteindre conseil d’administration reprendre',
  createdDate: dayjs('2024-03-29T18:08'),
  lastModifiedBy: 'smack bè innombrable',
  lastModifiedDate: dayjs('2024-03-30T01:52'),
};

export const sampleWithNewData: NewMouvementStock = {
  quantite: 20255,
  typeMouvement: 'ENTREE',
  transactionDate: dayjs('2024-03-29T19:39'),
  createdBy: 'auprès de pourpre au-dehors',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
