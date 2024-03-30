import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAbonnement, NewAbonnement } from '../abonnement.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAbonnement for edit and NewAbonnementFormGroupInput for create.
 */
type AbonnementFormGroupInput = IAbonnement | PartialWithRequiredKeyOf<NewAbonnement>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAbonnement | NewAbonnement> = Omit<
  T,
  'dateDebut' | 'dateFin' | 'deleteAt' | 'createdDate' | 'lastModifiedDate'
> & {
  dateDebut?: string | null;
  dateFin?: string | null;
  deleteAt?: string | null;
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

type AbonnementFormRawValue = FormValueOf<IAbonnement>;

type NewAbonnementFormRawValue = FormValueOf<NewAbonnement>;

type AbonnementFormDefaults = Pick<NewAbonnement, 'id' | 'dateDebut' | 'dateFin' | 'deleteAt' | 'createdDate' | 'lastModifiedDate'>;

type AbonnementFormGroupContent = {
  id: FormControl<AbonnementFormRawValue['id'] | NewAbonnement['id']>;
  dateDebut: FormControl<AbonnementFormRawValue['dateDebut']>;
  dateFin: FormControl<AbonnementFormRawValue['dateFin']>;
  statutAbonnement: FormControl<AbonnementFormRawValue['statutAbonnement']>;
  prix: FormControl<AbonnementFormRawValue['prix']>;
  deleteAt: FormControl<AbonnementFormRawValue['deleteAt']>;
  createdBy: FormControl<AbonnementFormRawValue['createdBy']>;
  createdDate: FormControl<AbonnementFormRawValue['createdDate']>;
  lastModifiedBy: FormControl<AbonnementFormRawValue['lastModifiedBy']>;
  lastModifiedDate: FormControl<AbonnementFormRawValue['lastModifiedDate']>;
  structure: FormControl<AbonnementFormRawValue['structure']>;
  plansAbonnement: FormControl<AbonnementFormRawValue['plansAbonnement']>;
  paiement: FormControl<AbonnementFormRawValue['paiement']>;
};

export type AbonnementFormGroup = FormGroup<AbonnementFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AbonnementFormService {
  createAbonnementFormGroup(abonnement: AbonnementFormGroupInput = { id: null }): AbonnementFormGroup {
    const abonnementRawValue = this.convertAbonnementToAbonnementRawValue({
      ...this.getFormDefaults(),
      ...abonnement,
    });
    return new FormGroup<AbonnementFormGroupContent>({
      id: new FormControl(
        { value: abonnementRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      dateDebut: new FormControl(abonnementRawValue.dateDebut, {
        validators: [Validators.required],
      }),
      dateFin: new FormControl(abonnementRawValue.dateFin, {
        validators: [Validators.required],
      }),
      statutAbonnement: new FormControl(abonnementRawValue.statutAbonnement, {
        validators: [Validators.required],
      }),
      prix: new FormControl(abonnementRawValue.prix, {
        validators: [Validators.required],
      }),
      deleteAt: new FormControl(abonnementRawValue.deleteAt),
      createdBy: new FormControl(abonnementRawValue.createdBy, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      createdDate: new FormControl(abonnementRawValue.createdDate),
      lastModifiedBy: new FormControl(abonnementRawValue.lastModifiedBy, {
        validators: [Validators.maxLength(50)],
      }),
      lastModifiedDate: new FormControl(abonnementRawValue.lastModifiedDate),
      structure: new FormControl(abonnementRawValue.structure, {
        validators: [Validators.required],
      }),
      plansAbonnement: new FormControl(abonnementRawValue.plansAbonnement, {
        validators: [Validators.required],
      }),
      paiement: new FormControl(abonnementRawValue.paiement, {
        validators: [Validators.required],
      }),
    });
  }

  getAbonnement(form: AbonnementFormGroup): IAbonnement | NewAbonnement {
    return this.convertAbonnementRawValueToAbonnement(form.getRawValue() as AbonnementFormRawValue | NewAbonnementFormRawValue);
  }

  resetForm(form: AbonnementFormGroup, abonnement: AbonnementFormGroupInput): void {
    const abonnementRawValue = this.convertAbonnementToAbonnementRawValue({ ...this.getFormDefaults(), ...abonnement });
    form.reset(
      {
        ...abonnementRawValue,
        id: { value: abonnementRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AbonnementFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dateDebut: currentTime,
      dateFin: currentTime,
      deleteAt: currentTime,
      createdDate: currentTime,
      lastModifiedDate: currentTime,
    };
  }

  private convertAbonnementRawValueToAbonnement(
    rawAbonnement: AbonnementFormRawValue | NewAbonnementFormRawValue,
  ): IAbonnement | NewAbonnement {
    return {
      ...rawAbonnement,
      dateDebut: dayjs(rawAbonnement.dateDebut, DATE_TIME_FORMAT),
      dateFin: dayjs(rawAbonnement.dateFin, DATE_TIME_FORMAT),
      deleteAt: dayjs(rawAbonnement.deleteAt, DATE_TIME_FORMAT),
      createdDate: dayjs(rawAbonnement.createdDate, DATE_TIME_FORMAT),
      lastModifiedDate: dayjs(rawAbonnement.lastModifiedDate, DATE_TIME_FORMAT),
    };
  }

  private convertAbonnementToAbonnementRawValue(
    abonnement: IAbonnement | (Partial<NewAbonnement> & AbonnementFormDefaults),
  ): AbonnementFormRawValue | PartialWithRequiredKeyOf<NewAbonnementFormRawValue> {
    return {
      ...abonnement,
      dateDebut: abonnement.dateDebut ? abonnement.dateDebut.format(DATE_TIME_FORMAT) : undefined,
      dateFin: abonnement.dateFin ? abonnement.dateFin.format(DATE_TIME_FORMAT) : undefined,
      deleteAt: abonnement.deleteAt ? abonnement.deleteAt.format(DATE_TIME_FORMAT) : undefined,
      createdDate: abonnement.createdDate ? abonnement.createdDate.format(DATE_TIME_FORMAT) : undefined,
      lastModifiedDate: abonnement.lastModifiedDate ? abonnement.lastModifiedDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
