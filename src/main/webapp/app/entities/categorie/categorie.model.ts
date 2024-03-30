import dayjs from 'dayjs/esm';

export interface ICategorie {
  id: string;
  libelle?: string | null;
  description?: string | null;
  deleteAt?: dayjs.Dayjs | null;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
}

export type NewCategorie = Omit<ICategorie, 'id'> & { id: null };
