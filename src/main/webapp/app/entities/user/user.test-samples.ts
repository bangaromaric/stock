import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 21179,
  login: 'iXq_@INLNzw\\pFAMHOK',
};

export const sampleWithPartialData: IUser = {
  id: 15920,
  login: '5Yzw',
};

export const sampleWithFullData: IUser = {
  id: 6394,
  login: 'K',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
