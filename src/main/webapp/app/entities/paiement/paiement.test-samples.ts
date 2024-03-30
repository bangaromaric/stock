import dayjs from 'dayjs/esm';

import { IPaiement, NewPaiement } from './paiement.model';

export const sampleWithRequiredData: IPaiement = {
  id: 'e25cb774-e61a-4828-9636-16d2d1aaa8a1',
  montant: 241.21,
  methodePaiement: 'AIRTEL_MONEY',
  statutPaiement: 'EN_ATTENTE',
  createdBy: 'là-haut vite afin que',
};

export const sampleWithPartialData: IPaiement = {
  id: 'a614f791-a1ae-4a6b-b100-15ec8a0b910a',
  montant: 10361.49,
  methodePaiement: 'MOOV_MONEY',
  statutPaiement: 'CONFIRME',
  createdBy: 'dîner innombrable',
  lastModifiedDate: dayjs('2024-03-29T18:00'),
};

export const sampleWithFullData: IPaiement = {
  id: 'cf11fdb7-ca45-40b8-a137-2d97244581cb',
  montant: 31463.95,
  methodePaiement: 'AIRTEL_MONEY',
  statutPaiement: 'CONFIRME',
  deleteAt: dayjs('2024-03-30T06:14'),
  createdBy: 'corps enseignant circulaire',
  createdDate: dayjs('2024-03-30T10:50'),
  lastModifiedBy: 'volontiers proche de vivre',
  lastModifiedDate: dayjs('2024-03-30T09:00'),
};

export const sampleWithNewData: NewPaiement = {
  montant: 18668.4,
  methodePaiement: 'MOOV_MONEY',
  statutPaiement: 'CONFIRME',
  createdBy: 'tôt',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
