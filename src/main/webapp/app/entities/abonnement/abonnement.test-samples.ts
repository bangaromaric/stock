import dayjs from 'dayjs/esm';

import { IAbonnement, NewAbonnement } from './abonnement.model';

export const sampleWithRequiredData: IAbonnement = {
  id: '7f8179b0-6ac9-49ab-b99f-5196d5648a72',
  dateDebut: dayjs('2024-03-30T14:07'),
  dateFin: dayjs('2024-03-30T07:17'),
  statutAbonnement: 'ACTIF',
  prix: 9227.52,
  createdBy: 'camarade tellement grandement',
};

export const sampleWithPartialData: IAbonnement = {
  id: 'daecc01f-843f-44da-ad51-c2e788245046',
  dateDebut: dayjs('2024-03-29T22:35'),
  dateFin: dayjs('2024-03-30T14:30'),
  statutAbonnement: 'ACTIF',
  prix: 21230.48,
  deleteAt: dayjs('2024-03-30T12:01'),
  createdBy: 'cot cot quand mal',
  lastModifiedDate: dayjs('2024-03-30T04:39'),
};

export const sampleWithFullData: IAbonnement = {
  id: 'c65e43af-20c9-491d-8eab-c440f32070e6',
  dateDebut: dayjs('2024-03-30T13:10'),
  dateFin: dayjs('2024-03-30T11:49'),
  statutAbonnement: 'EXPIRE',
  prix: 20113.36,
  deleteAt: dayjs('2024-03-30T10:17'),
  createdBy: 'sans que aussitôt que étant donné que',
  createdDate: dayjs('2024-03-30T07:34'),
  lastModifiedBy: 'puisque',
  lastModifiedDate: dayjs('2024-03-30T07:30'),
};

export const sampleWithNewData: NewAbonnement = {
  dateDebut: dayjs('2024-03-30T05:06'),
  dateFin: dayjs('2024-03-30T07:32'),
  statutAbonnement: 'ANNULE',
  prix: 9743.62,
  createdBy: 'tout',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
