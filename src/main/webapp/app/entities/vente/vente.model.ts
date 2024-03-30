import dayjs from 'dayjs/esm';
import { IProduit } from 'app/entities/produit/produit.model';
import { IStructure } from 'app/entities/structure/structure.model';

export interface IVente {
  id: string;
  quantite?: number | null;
  montant?: number | null;
  venteDate?: dayjs.Dayjs | null;
  deleteAt?: dayjs.Dayjs | null;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
  produit?: Pick<IProduit, 'id' | 'libelle'> | null;
  structure?: Pick<IStructure, 'id' | 'libelle'> | null;
}

export type NewVente = Omit<IVente, 'id'> & { id: null };
