import dayjs from 'dayjs/esm';

import { IPermission, NewPermission } from './permission.model';

export const sampleWithRequiredData: IPermission = {
  id: 'e1bda72f-b65a-42d8-8874-9a890d7a6eb3',
  ressource: 'en',
  action: 'élargir spécialiste',
  createdDate: dayjs('2024-03-30T14:08'),
};

export const sampleWithPartialData: IPermission = {
  id: '07bc7187-e5a7-4388-a375-cf9a26efd8cd',
  ressource: 'céans',
  action: 'tsoin-tsoin remettre sauvage',
  createdDate: dayjs('2024-03-29T23:54'),
  createdBy: 'vis-à-vie de commis afin que',
  lastModifiedDate: dayjs('2024-03-29T17:01'),
};

export const sampleWithFullData: IPermission = {
  id: '5086e2e9-e688-45a9-be50-f9fa03e19b26',
  ressource: 'si détecter',
  action: 'police gratis déjà',
  deleteAt: dayjs('2024-03-30T15:16'),
  createdDate: dayjs('2024-03-30T11:40'),
  createdBy: 'partenaire passablement autour de',
  lastModifiedDate: dayjs('2024-03-30T01:52'),
  lastModifiedBy: 'revoir cocorico atchoum',
};

export const sampleWithNewData: NewPermission = {
  ressource: 'céans spécialiste pourvu que',
  action: 'drelin',
  createdDate: dayjs('2024-03-30T12:51'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);