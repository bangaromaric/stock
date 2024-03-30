import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: '6f6fa88b-56e2-4d7a-80e1-ff2c69c4cfe0',
};

export const sampleWithPartialData: IAuthority = {
  name: '8587cec4-9e89-4245-99db-73d7b85c41b9',
};

export const sampleWithFullData: IAuthority = {
  name: 'f97d6033-c344-4dfd-9c9d-971ad16b96b7',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
