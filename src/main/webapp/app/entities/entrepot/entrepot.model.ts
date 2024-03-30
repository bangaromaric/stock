import dayjs from 'dayjs/esm';
import { IStructure } from 'app/entities/structure/structure.model';

export interface IEntrepot {
  id: string;
  libelle?: string | null;
  slug?: string | null;
  capacite?: number | null;
  description?: string | null;
  deleteAt?: dayjs.Dayjs | null;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
  structure?: Pick<IStructure, 'id' | 'libelle'> | null;
}

export type NewEntrepot = Omit<IEntrepot, 'id'> & { id: null };
