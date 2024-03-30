import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPaiement, NewPaiement } from '../paiement.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPaiement for edit and NewPaiementFormGroupInput for create.
 */
type PaiementFormGroupInput = IPaiement | PartialWithRequiredKeyOf<NewPaiement>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IPaiement | NewPaiement> = Omit<T, 'deleteAt' | 'createdDate' | 'lastModifiedDate'> & {
  deleteAt?: string | null;
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

type PaiementFormRawValue = FormValueOf<IPaiement>;

type NewPaiementFormRawValue = FormValueOf<NewPaiement>;

type PaiementFormDefaults = Pick<NewPaiement, 'id' | 'deleteAt' | 'createdDate' | 'lastModifiedDate'>;

type PaiementFormGroupContent = {
  id: FormControl<PaiementFormRawValue['id'] | NewPaiement['id']>;
  montant: FormControl<PaiementFormRawValue['montant']>;
  methodePaiement: FormControl<PaiementFormRawValue['methodePaiement']>;
  statutPaiement: FormControl<PaiementFormRawValue['statutPaiement']>;
  deleteAt: FormControl<PaiementFormRawValue['deleteAt']>;
  createdBy: FormControl<PaiementFormRawValue['createdBy']>;
  createdDate: FormControl<PaiementFormRawValue['createdDate']>;
  lastModifiedBy: FormControl<PaiementFormRawValue['lastModifiedBy']>;
  lastModifiedDate: FormControl<PaiementFormRawValue['lastModifiedDate']>;
  plansAbonnement: FormControl<PaiementFormRawValue['plansAbonnement']>;
};

export type PaiementFormGroup = FormGroup<PaiementFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PaiementFormService {
  createPaiementFormGroup(paiement: PaiementFormGroupInput = { id: null }): PaiementFormGroup {
    const paiementRawValue = this.convertPaiementToPaiementRawValue({
      ...this.getFormDefaults(),
      ...paiement,
    });
    return new FormGroup<PaiementFormGroupContent>({
      id: new FormControl(
        { value: paiementRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      montant: new FormControl(paiementRawValue.montant, {
        validators: [Validators.required],
      }),
      methodePaiement: new FormControl(paiementRawValue.methodePaiement, {
        validators: [Validators.required],
      }),
      statutPaiement: new FormControl(paiementRawValue.statutPaiement, {
        validators: [Validators.required],
      }),
      deleteAt: new FormControl(paiementRawValue.deleteAt),
      createdBy: new FormControl(paiementRawValue.createdBy, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      createdDate: new FormControl(paiementRawValue.createdDate),
      lastModifiedBy: new FormControl(paiementRawValue.lastModifiedBy, {
        validators: [Validators.maxLength(50)],
      }),
      lastModifiedDate: new FormControl(paiementRawValue.lastModifiedDate),
      plansAbonnement: new FormControl(paiementRawValue.plansAbonnement, {
        validators: [Validators.required],
      }),
    });
  }

  getPaiement(form: PaiementFormGroup): IPaiement | NewPaiement {
    return this.convertPaiementRawValueToPaiement(form.getRawValue() as PaiementFormRawValue | NewPaiementFormRawValue);
  }

  resetForm(form: PaiementFormGroup, paiement: PaiementFormGroupInput): void {
    const paiementRawValue = this.convertPaiementToPaiementRawValue({ ...this.getFormDefaults(), ...paiement });
    form.reset(
      {
        ...paiementRawValue,
        id: { value: paiementRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PaiementFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      deleteAt: currentTime,
      createdDate: currentTime,
      lastModifiedDate: currentTime,
    };
  }

  private convertPaiementRawValueToPaiement(rawPaiement: PaiementFormRawValue | NewPaiementFormRawValue): IPaiement | NewPaiement {
    return {
      ...rawPaiement,
      deleteAt: dayjs(rawPaiement.deleteAt, DATE_TIME_FORMAT),
      createdDate: dayjs(rawPaiement.createdDate, DATE_TIME_FORMAT),
      lastModifiedDate: dayjs(rawPaiement.lastModifiedDate, DATE_TIME_FORMAT),
    };
  }

  private convertPaiementToPaiementRawValue(
    paiement: IPaiement | (Partial<NewPaiement> & PaiementFormDefaults),
  ): PaiementFormRawValue | PartialWithRequiredKeyOf<NewPaiementFormRawValue> {
    return {
      ...paiement,
      deleteAt: paiement.deleteAt ? paiement.deleteAt.format(DATE_TIME_FORMAT) : undefined,
      createdDate: paiement.createdDate ? paiement.createdDate.format(DATE_TIME_FORMAT) : undefined,
      lastModifiedDate: paiement.lastModifiedDate ? paiement.lastModifiedDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
