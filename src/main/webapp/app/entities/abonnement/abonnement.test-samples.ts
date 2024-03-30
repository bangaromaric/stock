import dayjs from 'dayjs/esm';

import { IAbonnement, NewAbonnement } from './abonnement.model';

export const sampleWithRequiredData: IAbonnement = {
  id: '7f8179b0-6ac9-49ab-b99f-5196d5648a72',
  dateDebut: dayjs('2024-03-30T13:26'),
  dateFin: dayjs('2024-03-30T06:36'),
  statutAbonnement: 'ACTIF',
  prix: 9227.52,
  createdBy: 'camarade tellement grandement',
};

export const sampleWithPartialData: IAbonnement = {
  id: 'daecc01f-843f-44da-ad51-c2e788245046',
  dateDebut: dayjs('2024-03-29T21:54'),
  dateFin: dayjs('2024-03-30T13:50'),
  statutAbonnement: 'ACTIF',
  prix: 21230.48,
  deleteAt: dayjs('2024-03-30T11:20'),
  createdBy: 'cot cot quand mal',
  lastModifiedDate: dayjs('2024-03-30T03:58'),
};

export const sampleWithFullData: IAbonnement = {
  id: 'c65e43af-20c9-491d-8eab-c440f32070e6',
  dateDebut: dayjs('2024-03-30T12:29'),
  dateFin: dayjs('2024-03-30T11:08'),
  statutAbonnement: 'EXPIRE',
  prix: 20113.36,
  deleteAt: dayjs('2024-03-30T09:36'),
  createdBy: 'sans que aussitôt que étant donné que',
  createdDate: dayjs('2024-03-30T06:53'),
  lastModifiedBy: 'puisque',
  lastModifiedDate: dayjs('2024-03-30T06:50'),
};

export const sampleWithNewData: NewAbonnement = {
  dateDebut: dayjs('2024-03-30T04:25'),
  dateFin: dayjs('2024-03-30T06:51'),
  statutAbonnement: 'ANNULE',
  prix: 9743.62,
  createdBy: 'tout',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
