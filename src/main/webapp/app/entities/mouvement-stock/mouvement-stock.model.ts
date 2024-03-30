import dayjs from 'dayjs/esm';
import { IProduit } from 'app/entities/produit/produit.model';
import { IEntrepot } from 'app/entities/entrepot/entrepot.model';
import { TypeMouvement } from 'app/entities/enumerations/type-mouvement.model';

export interface IMouvementStock {
  id: string;
  quantite?: number | null;
  typeMouvement?: keyof typeof TypeMouvement | null;
  transactionDate?: dayjs.Dayjs | null;
  deleteAt?: dayjs.Dayjs | null;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
  produit?: Pick<IProduit, 'id' | 'libelle'> | null;
  entrepot?: Pick<IEntrepot, 'id' | 'libelle'> | null;
}

export type NewMouvementStock = Omit<IMouvementStock, 'id'> & { id: null };
