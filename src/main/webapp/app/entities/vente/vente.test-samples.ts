import dayjs from 'dayjs/esm';

import { IVente, NewVente } from './vente.model';

export const sampleWithRequiredData: IVente = {
  id: '1719985a-b5a4-48fd-9eb8-2d74eece9b7c',
  quantite: 31584,
  montant: 20573.73,
  venteDate: dayjs('2024-03-29T20:52'),
  createdBy: 'énorme',
};

export const sampleWithPartialData: IVente = {
  id: '9e54f4c8-e23d-4258-a38a-238d0d0dcf62',
  quantite: 4813,
  montant: 3467.49,
  venteDate: dayjs('2024-03-29T19:49'),
  deleteAt: dayjs('2024-03-30T13:13'),
  createdBy: 'ah siffler renseigner',
  createdDate: dayjs('2024-03-30T11:15'),
};

export const sampleWithFullData: IVente = {
  id: '50664ee3-b85c-469c-a808-704030172207',
  quantite: 6586,
  montant: 3785.69,
  venteDate: dayjs('2024-03-29T18:45'),
  deleteAt: dayjs('2024-03-30T14:39'),
  createdBy: 'nonobstant',
  createdDate: dayjs('2024-03-30T06:48'),
  lastModifiedBy: 'pendant que toc-toc',
  lastModifiedDate: dayjs('2024-03-30T13:24'),
};

export const sampleWithNewData: NewVente = {
  quantite: 16184,
  montant: 2109.92,
  venteDate: dayjs('2024-03-30T00:50'),
  createdBy: 'sitôt que',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);