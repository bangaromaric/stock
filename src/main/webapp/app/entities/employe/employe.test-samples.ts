import dayjs from 'dayjs/esm';

import { IEmploye, NewEmploye } from './employe.model';

export const sampleWithRequiredData: IEmploye = {
  id: 'd1947648-d2c5-4bed-bc4a-3e7e737be714',
  lastName: 'Joly',
  login: 'soudain de façon à susciter',
  createdBy: 'amorphe',
};

export const sampleWithPartialData: IEmploye = {
  id: 'e878f0f1-8dbb-4ffb-9d6b-0553b55dd43e',
  lastName: 'Robin',
  login: 'cuicui apte répéter',
  email: '-1u@)n(7.J3~7z',
  deleteAt: dayjs('2024-03-29T22:01'),
  createdBy: 'afin que',
  lastModifiedDate: dayjs('2024-03-30T03:01'),
};

export const sampleWithFullData: IEmploye = {
  id: '78e1d8d0-063f-487f-ab7a-fd104b44002b',
  firstName: 'Dorian',
  lastName: 'Vasseur',
  login: 'sous',
  email: "-rY}/@'_>.fS;",
  deleteAt: dayjs('2024-03-30T13:22'),
  createdBy: 'au-dedans de',
  createdDate: dayjs('2024-03-30T07:11'),
  lastModifiedBy: 'séculaire',
  lastModifiedDate: dayjs('2024-03-30T14:11'),
};

export const sampleWithNewData: NewEmploye = {
  lastName: 'Masson',
  login: 'fort bondir interpréter',
  createdBy: 'de sorte que ici',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
