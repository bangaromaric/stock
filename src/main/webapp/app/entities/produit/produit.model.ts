import dayjs from 'dayjs/esm';
import { ICategorie } from 'app/entities/categorie/categorie.model';

export interface IProduit {
  id: string;
  libelle?: string | null;
  description?: string | null;
  slug?: string | null;
  prixUnitaire?: number | null;
  dateExpiration?: dayjs.Dayjs | null;
  deleteAt?: dayjs.Dayjs | null;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
  categorie?: Pick<ICategorie, 'id' | 'libelle'> | null;
}

export type NewProduit = Omit<IProduit, 'id'> & { id: null };
