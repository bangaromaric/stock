import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IEntrepot, NewEntrepot } from '../entrepot.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEntrepot for edit and NewEntrepotFormGroupInput for create.
 */
type EntrepotFormGroupInput = IEntrepot | PartialWithRequiredKeyOf<NewEntrepot>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IEntrepot | NewEntrepot> = Omit<T, 'deleteAt' | 'createdDate' | 'lastModifiedDate'> & {
  deleteAt?: string | null;
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

type EntrepotFormRawValue = FormValueOf<IEntrepot>;

type NewEntrepotFormRawValue = FormValueOf<NewEntrepot>;

type EntrepotFormDefaults = Pick<NewEntrepot, 'id' | 'deleteAt' | 'createdDate' | 'lastModifiedDate'>;

type EntrepotFormGroupContent = {
  id: FormControl<EntrepotFormRawValue['id'] | NewEntrepot['id']>;
  libelle: FormControl<EntrepotFormRawValue['libelle']>;
  slug: FormControl<EntrepotFormRawValue['slug']>;
  capacite: FormControl<EntrepotFormRawValue['capacite']>;
  description: FormControl<EntrepotFormRawValue['description']>;
  deleteAt: FormControl<EntrepotFormRawValue['deleteAt']>;
  createdBy: FormControl<EntrepotFormRawValue['createdBy']>;
  createdDate: FormControl<EntrepotFormRawValue['createdDate']>;
  lastModifiedBy: FormControl<EntrepotFormRawValue['lastModifiedBy']>;
  lastModifiedDate: FormControl<EntrepotFormRawValue['lastModifiedDate']>;
  structure: FormControl<EntrepotFormRawValue['structure']>;
};

export type EntrepotFormGroup = FormGroup<EntrepotFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EntrepotFormService {
  createEntrepotFormGroup(entrepot: EntrepotFormGroupInput = { id: null }): EntrepotFormGroup {
    const entrepotRawValue = this.convertEntrepotToEntrepotRawValue({
      ...this.getFormDefaults(),
      ...entrepot,
    });
    return new FormGroup<EntrepotFormGroupContent>({
      id: new FormControl(
        { value: entrepotRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      libelle: new FormControl(entrepotRawValue.libelle, {
        validators: [Validators.required],
      }),
      slug: new FormControl(entrepotRawValue.slug, {
        validators: [Validators.required],
      }),
      capacite: new FormControl(entrepotRawValue.capacite, {
        validators: [Validators.required],
      }),
      description: new FormControl(entrepotRawValue.description),
      deleteAt: new FormControl(entrepotRawValue.deleteAt),
      createdBy: new FormControl(entrepotRawValue.createdBy, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      createdDate: new FormControl(entrepotRawValue.createdDate),
      lastModifiedBy: new FormControl(entrepotRawValue.lastModifiedBy, {
        validators: [Validators.maxLength(50)],
      }),
      lastModifiedDate: new FormControl(entrepotRawValue.lastModifiedDate),
      structure: new FormControl(entrepotRawValue.structure),
    });
  }

  getEntrepot(form: EntrepotFormGroup): IEntrepot | NewEntrepot {
    return this.convertEntrepotRawValueToEntrepot(form.getRawValue() as EntrepotFormRawValue | NewEntrepotFormRawValue);
  }

  resetForm(form: EntrepotFormGroup, entrepot: EntrepotFormGroupInput): void {
    const entrepotRawValue = this.convertEntrepotToEntrepotRawValue({ ...this.getFormDefaults(), ...entrepot });
    form.reset(
      {
        ...entrepotRawValue,
        id: { value: entrepotRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EntrepotFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      deleteAt: currentTime,
      createdDate: currentTime,
      lastModifiedDate: currentTime,
    };
  }

  private convertEntrepotRawValueToEntrepot(rawEntrepot: EntrepotFormRawValue | NewEntrepotFormRawValue): IEntrepot | NewEntrepot {
    return {
      ...rawEntrepot,
      deleteAt: dayjs(rawEntrepot.deleteAt, DATE_TIME_FORMAT),
      createdDate: dayjs(rawEntrepot.createdDate, DATE_TIME_FORMAT),
      lastModifiedDate: dayjs(rawEntrepot.lastModifiedDate, DATE_TIME_FORMAT),
    };
  }

  private convertEntrepotToEntrepotRawValue(
    entrepot: IEntrepot | (Partial<NewEntrepot> & EntrepotFormDefaults),
  ): EntrepotFormRawValue | PartialWithRequiredKeyOf<NewEntrepotFormRawValue> {
    return {
      ...entrepot,
      deleteAt: entrepot.deleteAt ? entrepot.deleteAt.format(DATE_TIME_FORMAT) : undefined,
      createdDate: entrepot.createdDate ? entrepot.createdDate.format(DATE_TIME_FORMAT) : undefined,
      lastModifiedDate: entrepot.lastModifiedDate ? entrepot.lastModifiedDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
