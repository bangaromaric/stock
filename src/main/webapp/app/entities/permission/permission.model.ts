import dayjs from 'dayjs/esm';
import { IAuthority } from 'app/entities/admin/authority/authority.model';

export interface IPermission {
  id: string;
  ressource?: string | null;
  action?: string | null;
  deleteAt?: dayjs.Dayjs | null;
  createdDate?: dayjs.Dayjs | null;
  createdBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  authorities?: IAuthority[] | null;
}

export type NewPermission = Omit<IPermission, 'id'> & { id: null };
