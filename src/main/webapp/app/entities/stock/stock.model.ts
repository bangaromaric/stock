import dayjs from 'dayjs/esm';
import { IEntrepot } from 'app/entities/entrepot/entrepot.model';
import { IProduit } from 'app/entities/produit/produit.model';

export interface IStock {
  id: string;
  quantite?: number | null;
  deleteAt?: dayjs.Dayjs | null;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
  entrepot?: Pick<IEntrepot, 'id' | 'libelle'> | null;
  produit?: Pick<IProduit, 'id' | 'libelle'> | null;
}

export type NewStock = Omit<IStock, 'id'> & { id: null };
