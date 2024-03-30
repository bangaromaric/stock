import dayjs from 'dayjs/esm';
import { IPlansAbonnement } from 'app/entities/plans-abonnement/plans-abonnement.model';
import { MethodePaiement } from 'app/entities/enumerations/methode-paiement.model';
import { StatutPaiement } from 'app/entities/enumerations/statut-paiement.model';

export interface IPaiement {
  id: string;
  montant?: number | null;
  methodePaiement?: keyof typeof MethodePaiement | null;
  statutPaiement?: keyof typeof StatutPaiement | null;
  deleteAt?: dayjs.Dayjs | null;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
  plansAbonnement?: Pick<IPlansAbonnement, 'id' | 'libelle'> | null;
}

export type NewPaiement = Omit<IPaiement, 'id'> & { id: null };
