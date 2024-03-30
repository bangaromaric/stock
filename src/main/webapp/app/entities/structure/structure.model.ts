import dayjs from 'dayjs/esm';

export interface IStructure {
  id: string;
  libelle?: string | null;
  telephone?: string | null;
  email?: string | null;
  adresse?: string | null;
  deleteAt?: dayjs.Dayjs | null;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
}

export type NewStructure = Omit<IStructure, 'id'> & { id: null };
