import dayjs from 'dayjs/esm';
import { IStructure } from 'app/entities/structure/structure.model';
import { IPlansAbonnement } from 'app/entities/plans-abonnement/plans-abonnement.model';
import { IPaiement } from 'app/entities/paiement/paiement.model';
import { StatutAbonnement } from 'app/entities/enumerations/statut-abonnement.model';

export interface IAbonnement {
  id: string;
  dateDebut?: dayjs.Dayjs | null;
  dateFin?: dayjs.Dayjs | null;
  statutAbonnement?: keyof typeof StatutAbonnement | null;
  prix?: number | null;
  deleteAt?: dayjs.Dayjs | null;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
  structure?: Pick<IStructure, 'id' | 'libelle'> | null;
  plansAbonnement?: Pick<IPlansAbonnement, 'id' | 'libelle'> | null;
  paiement?: Pick<IPaiement, 'id' | 'montant'> | null;
}

export type NewAbonnement = Omit<IAbonnement, 'id'> & { id: null };
