import dayjs from 'dayjs/esm';
import { IEntrepot } from 'app/entities/entrepot/entrepot.model';
import { IProduit } from 'app/entities/produit/produit.model';

export interface IInventaire {
  id: string;
  quantite?: number | null;
  inventaireDate?: dayjs.Dayjs | null;
  deleteAt?: dayjs.Dayjs | null;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
  entrepot?: Pick<IEntrepot, 'id' | 'libelle'> | null;
  produit?: Pick<IProduit, 'id' | 'libelle'> | null;
}

export type NewInventaire = Omit<IInventaire, 'id'> & { id: null };
