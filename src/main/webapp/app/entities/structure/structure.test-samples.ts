import dayjs from 'dayjs/esm';

import { IStructure, NewStructure } from './structure.model';

export const sampleWithRequiredData: IStructure = {
  id: '245d03a6-1a2a-4cdd-80a9-93bc34c0e48e',
  libelle: 'tantôt',
  telephone: '+33 347159010',
  email: '"l@yGfN.eg8U<0',
  createdBy: "à l'exception de ha commissionnaire",
};

export const sampleWithPartialData: IStructure = {
  id: '5d354dc4-7120-40b8-b9f9-90d82e30cd94',
  libelle: 'depuis',
  telephone: '+33 361672654',
  email: 'um6,{@Dflklh.{+',
  createdBy: 'de façon à ce que',
  lastModifiedBy: 'autrefois avare mairie',
};

export const sampleWithFullData: IStructure = {
  id: 'cb384a7d-3307-4bf0-91e6-0224f7c979b6',
  libelle: 'certainement',
  telephone: '0187750408',
  email: '5EZD9-@I54w.y%',
  adresse: 'peser de peur de',
  deleteAt: dayjs('2024-03-30T09:55'),
  createdBy: 'antagoniste',
  createdDate: dayjs('2024-03-29T19:10'),
  lastModifiedBy: 'autrement',
  lastModifiedDate: dayjs('2024-03-29T17:27'),
};

export const sampleWithNewData: NewStructure = {
  libelle: 'hi si bien que',
  telephone: '+33 387644921',
  email: 'q^@W".k8Y',
  createdBy: 'du fait que entre menacer',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
