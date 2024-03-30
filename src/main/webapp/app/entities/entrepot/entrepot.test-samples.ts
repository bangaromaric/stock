import dayjs from 'dayjs/esm';

import { IEntrepot, NewEntrepot } from './entrepot.model';

export const sampleWithRequiredData: IEntrepot = {
  id: '0e03be8f-c10e-48f6-8f95-b72d8298e13f',
  libelle: 'en plus de croâ bavarder',
  slug: 'fade diplomate',
  capacite: 4777,
  createdBy: 'vu que considérable de manière à ce que',
};

export const sampleWithPartialData: IEntrepot = {
  id: 'e68aa67b-ea71-4f84-b0e6-1ffb82da2549',
  libelle: 'toujours revoir',
  slug: 'vis-à-vie de',
  capacite: 18253,
  description: '../fake-data/blob/hipster.txt',
  deleteAt: dayjs('2024-03-30T03:11'),
  createdBy: 'risquer chef devant',
};

export const sampleWithFullData: IEntrepot = {
  id: 'cf26f004-842b-4a2c-9f57-3bf44a57207b',
  libelle: 'proche de',
  slug: 'gratis meuh si bien que',
  capacite: 18473,
  description: '../fake-data/blob/hipster.txt',
  deleteAt: dayjs('2024-03-29T16:18'),
  createdBy: 'bof',
  createdDate: dayjs('2024-03-30T14:05'),
  lastModifiedBy: 'fonctionnaire',
  lastModifiedDate: dayjs('2024-03-30T08:35'),
};

export const sampleWithNewData: NewEntrepot = {
  libelle: 'en dedans de personnel chut',
  slug: 'officier',
  capacite: 9480,
  createdBy: 'hystérique de la part de conseil d’administration',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
