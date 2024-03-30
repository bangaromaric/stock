import dayjs from 'dayjs/esm';

import { IPlansAbonnement, NewPlansAbonnement } from './plans-abonnement.model';

export const sampleWithRequiredData: IPlansAbonnement = {
  id: '95710422-d535-45cb-8aa7-c6a0bfb2d142',
  libelle: 'accentuer',
  description: '../fake-data/blob/hipster.txt',
  prix: 17631.75,
  createdBy: 'maigre zzzz sédentaire',
};

export const sampleWithPartialData: IPlansAbonnement = {
  id: '5c1b31ef-96ef-4338-81f0-816c6d25ee89',
  libelle: 'triathlète loufoque',
  description: '../fake-data/blob/hipster.txt',
  prix: 25765.73,
  duree: 'habiller',
  createdBy: 'détruire amorphe',
  createdDate: dayjs('2024-03-29T16:44'),
};

export const sampleWithFullData: IPlansAbonnement = {
  id: '20ef8c09-e16f-4dcd-a3e1-c89df247962f',
  libelle: 'au-dessous de pff',
  description: '../fake-data/blob/hipster.txt',
  prix: 8801.93,
  duree: 'en dedans de',
  avantage: '../fake-data/blob/hipster.txt',
  deleteAt: dayjs('2024-03-30T03:46'),
  createdBy: 'étant donné que pschitt toc-toc',
  createdDate: dayjs('2024-03-29T22:14'),
  lastModifiedBy: "soutenir hôte aujourd'hui",
  lastModifiedDate: dayjs('2024-03-30T04:16'),
};

export const sampleWithNewData: NewPlansAbonnement = {
  libelle: 'afin de quitter',
  description: '../fake-data/blob/hipster.txt',
  prix: 7698.38,
  createdBy: 'candide en dépit de',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
