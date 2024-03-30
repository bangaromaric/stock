import dayjs from 'dayjs/esm';

export interface IPlansAbonnement {
  id: string;
  libelle?: string | null;
  description?: string | null;
  prix?: number | null;
  duree?: string | null;
  avantage?: string | null;
  deleteAt?: dayjs.Dayjs | null;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
}

export type NewPlansAbonnement = Omit<IPlansAbonnement, 'id'> & { id: null };
