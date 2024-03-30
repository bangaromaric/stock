import dayjs from 'dayjs/esm';

import { ICategorie, NewCategorie } from './categorie.model';

export const sampleWithRequiredData: ICategorie = {
  id: '0acf466c-9feb-4032-b96e-44f650b4d9e1',
  libelle: 'miaou surmonter de façon à',
  createdBy: "engendrer à l'entour de mince",
};

export const sampleWithPartialData: ICategorie = {
  id: '142f7356-77ca-4d8c-8a60-193c7e8835ca',
  libelle: 'autour',
  description: '../fake-data/blob/hipster.txt',
  createdBy: 'cyan',
  createdDate: dayjs('2024-03-30T11:30'),
};

export const sampleWithFullData: ICategorie = {
  id: '4359595f-0dbc-41cb-952f-c51a24a8c8dd',
  libelle: 'cot cot fidèle',
  description: '../fake-data/blob/hipster.txt',
  deleteAt: dayjs('2024-03-29T23:47'),
  createdBy: 'atchoum',
  createdDate: dayjs('2024-03-30T08:14'),
  lastModifiedBy: 'que',
  lastModifiedDate: dayjs('2024-03-29T19:34'),
};

export const sampleWithNewData: NewCategorie = {
  libelle: 'par',
  createdBy: 'près',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
