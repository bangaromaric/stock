import dayjs from 'dayjs/esm';

import { IProduit, NewProduit } from './produit.model';

export const sampleWithRequiredData: IProduit = {
  id: 'abecae1d-2e00-416d-9f48-7e168079a39b',
  libelle: 'dehors proche de puisque',
  slug: 'assez selon',
  prixUnitaire: 28272.92,
  createdBy: 'dès que remplir',
};

export const sampleWithPartialData: IProduit = {
  id: 'eb68e618-796a-44ec-8f85-55dac15cf82f',
  libelle: 'par',
  description: '../fake-data/blob/hipster.txt',
  slug: 'en dépit de',
  prixUnitaire: 22289.87,
  dateExpiration: dayjs('2024-03-29'),
  createdBy: 'tant sombre',
  createdDate: dayjs('2024-03-30T03:18'),
  lastModifiedBy: 'euh',
};

export const sampleWithFullData: IProduit = {
  id: '38cf4785-800a-4070-bfd6-0e46e9283a66',
  libelle: 'hebdomadaire malgré',
  description: '../fake-data/blob/hipster.txt',
  slug: 'sympathique égoïste',
  prixUnitaire: 13883.9,
  dateExpiration: dayjs('2024-03-30'),
  deleteAt: dayjs('2024-03-30T08:26'),
  createdBy: 'apparemment',
  createdDate: dayjs('2024-03-30T09:28'),
  lastModifiedBy: 'orange insuffisamment',
  lastModifiedDate: dayjs('2024-03-30T10:18'),
};

export const sampleWithNewData: NewProduit = {
  libelle: 'debout',
  slug: 'ferme insipide',
  prixUnitaire: 2992.91,
  createdBy: 'si triste',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
