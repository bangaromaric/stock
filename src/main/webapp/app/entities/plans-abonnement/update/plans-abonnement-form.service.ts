import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPlansAbonnement, NewPlansAbonnement } from '../plans-abonnement.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPlansAbonnement for edit and NewPlansAbonnementFormGroupInput for create.
 */
type PlansAbonnementFormGroupInput = IPlansAbonnement | PartialWithRequiredKeyOf<NewPlansAbonnement>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IPlansAbonnement | NewPlansAbonnement> = Omit<T, 'deleteAt' | 'createdDate' | 'lastModifiedDate'> & {
  deleteAt?: string | null;
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

type PlansAbonnementFormRawValue = FormValueOf<IPlansAbonnement>;

type NewPlansAbonnementFormRawValue = FormValueOf<NewPlansAbonnement>;

type PlansAbonnementFormDefaults = Pick<NewPlansAbonnement, 'id' | 'deleteAt' | 'createdDate' | 'lastModifiedDate'>;

type PlansAbonnementFormGroupContent = {
  id: FormControl<PlansAbonnementFormRawValue['id'] | NewPlansAbonnement['id']>;
  libelle: FormControl<PlansAbonnementFormRawValue['libelle']>;
  description: FormControl<PlansAbonnementFormRawValue['description']>;
  prix: FormControl<PlansAbonnementFormRawValue['prix']>;
  duree: FormControl<PlansAbonnementFormRawValue['duree']>;
  avantage: FormControl<PlansAbonnementFormRawValue['avantage']>;
  deleteAt: FormControl<PlansAbonnementFormRawValue['deleteAt']>;
  createdBy: FormControl<PlansAbonnementFormRawValue['createdBy']>;
  createdDate: FormControl<PlansAbonnementFormRawValue['createdDate']>;
  lastModifiedBy: FormControl<PlansAbonnementFormRawValue['lastModifiedBy']>;
  lastModifiedDate: FormControl<PlansAbonnementFormRawValue['lastModifiedDate']>;
};

export type PlansAbonnementFormGroup = FormGroup<PlansAbonnementFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PlansAbonnementFormService {
  createPlansAbonnementFormGroup(plansAbonnement: PlansAbonnementFormGroupInput = { id: null }): PlansAbonnementFormGroup {
    const plansAbonnementRawValue = this.convertPlansAbonnementToPlansAbonnementRawValue({
      ...this.getFormDefaults(),
      ...plansAbonnement,
    });
    return new FormGroup<PlansAbonnementFormGroupContent>({
      id: new FormControl(
        { value: plansAbonnementRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      libelle: new FormControl(plansAbonnementRawValue.libelle, {
        validators: [Validators.required],
      }),
      description: new FormControl(plansAbonnementRawValue.description, {
        validators: [Validators.required],
      }),
      prix: new FormControl(plansAbonnementRawValue.prix, {
        validators: [Validators.required],
      }),
      duree: new FormControl(plansAbonnementRawValue.duree),
      avantage: new FormControl(plansAbonnementRawValue.avantage),
      deleteAt: new FormControl(plansAbonnementRawValue.deleteAt),
      createdBy: new FormControl(plansAbonnementRawValue.createdBy, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      createdDate: new FormControl(plansAbonnementRawValue.createdDate),
      lastModifiedBy: new FormControl(plansAbonnementRawValue.lastModifiedBy, {
        validators: [Validators.maxLength(50)],
      }),
      lastModifiedDate: new FormControl(plansAbonnementRawValue.lastModifiedDate),
    });
  }

  getPlansAbonnement(form: PlansAbonnementFormGroup): IPlansAbonnement | NewPlansAbonnement {
    return this.convertPlansAbonnementRawValueToPlansAbonnement(
      form.getRawValue() as PlansAbonnementFormRawValue | NewPlansAbonnementFormRawValue,
    );
  }

  resetForm(form: PlansAbonnementFormGroup, plansAbonnement: PlansAbonnementFormGroupInput): void {
    const plansAbonnementRawValue = this.convertPlansAbonnementToPlansAbonnementRawValue({ ...this.getFormDefaults(), ...plansAbonnement });
    form.reset(
      {
        ...plansAbonnementRawValue,
        id: { value: plansAbonnementRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PlansAbonnementFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      deleteAt: currentTime,
      createdDate: currentTime,
      lastModifiedDate: currentTime,
    };
  }

  private convertPlansAbonnementRawValueToPlansAbonnement(
    rawPlansAbonnement: PlansAbonnementFormRawValue | NewPlansAbonnementFormRawValue,
  ): IPlansAbonnement | NewPlansAbonnement {
    return {
      ...rawPlansAbonnement,
      deleteAt: dayjs(rawPlansAbonnement.deleteAt, DATE_TIME_FORMAT),
      createdDate: dayjs(rawPlansAbonnement.createdDate, DATE_TIME_FORMAT),
      lastModifiedDate: dayjs(rawPlansAbonnement.lastModifiedDate, DATE_TIME_FORMAT),
    };
  }

  private convertPlansAbonnementToPlansAbonnementRawValue(
    plansAbonnement: IPlansAbonnement | (Partial<NewPlansAbonnement> & PlansAbonnementFormDefaults),
  ): PlansAbonnementFormRawValue | PartialWithRequiredKeyOf<NewPlansAbonnementFormRawValue> {
    return {
      ...plansAbonnement,
      deleteAt: plansAbonnement.deleteAt ? plansAbonnement.deleteAt.format(DATE_TIME_FORMAT) : undefined,
      createdDate: plansAbonnement.createdDate ? plansAbonnement.createdDate.format(DATE_TIME_FORMAT) : undefined,
      lastModifiedDate: plansAbonnement.lastModifiedDate ? plansAbonnement.lastModifiedDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
