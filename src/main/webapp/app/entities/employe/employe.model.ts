import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { IStructure } from 'app/entities/structure/structure.model';

export interface IEmploye {
  id: string;
  firstName?: string | null;
  lastName?: string | null;
  login?: string | null;
  email?: string | null;
  deleteAt?: dayjs.Dayjs | null;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
  internalUser?: Pick<IUser, 'id' | 'login'> | null;
  structure?: Pick<IStructure, 'id' | 'libelle'> | null;
}

export type NewEmploye = Omit<IEmploye, 'id'> & { id: null };
