import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICategorie, NewCategorie } from '../categorie.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICategorie for edit and NewCategorieFormGroupInput for create.
 */
type CategorieFormGroupInput = ICategorie | PartialWithRequiredKeyOf<NewCategorie>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICategorie | NewCategorie> = Omit<T, 'deleteAt' | 'createdDate' | 'lastModifiedDate'> & {
  deleteAt?: string | null;
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

type CategorieFormRawValue = FormValueOf<ICategorie>;

type NewCategorieFormRawValue = FormValueOf<NewCategorie>;

type CategorieFormDefaults = Pick<NewCategorie, 'id' | 'deleteAt' | 'createdDate' | 'lastModifiedDate'>;

type CategorieFormGroupContent = {
  id: FormControl<CategorieFormRawValue['id'] | NewCategorie['id']>;
  libelle: FormControl<CategorieFormRawValue['libelle']>;
  description: FormControl<CategorieFormRawValue['description']>;
  deleteAt: FormControl<CategorieFormRawValue['deleteAt']>;
  createdBy: FormControl<CategorieFormRawValue['createdBy']>;
  createdDate: FormControl<CategorieFormRawValue['createdDate']>;
  lastModifiedBy: FormControl<CategorieFormRawValue['lastModifiedBy']>;
  lastModifiedDate: FormControl<CategorieFormRawValue['lastModifiedDate']>;
};

export type CategorieFormGroup = FormGroup<CategorieFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CategorieFormService {
  createCategorieFormGroup(categorie: CategorieFormGroupInput = { id: null }): CategorieFormGroup {
    const categorieRawValue = this.convertCategorieToCategorieRawValue({
      ...this.getFormDefaults(),
      ...categorie,
    });
    return new FormGroup<CategorieFormGroupContent>({
      id: new FormControl(
        { value: categorieRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      libelle: new FormControl(categorieRawValue.libelle, {
        validators: [Validators.required],
      }),
      description: new FormControl(categorieRawValue.description),
      deleteAt: new FormControl(categorieRawValue.deleteAt),
      createdBy: new FormControl(categorieRawValue.createdBy, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      createdDate: new FormControl(categorieRawValue.createdDate),
      lastModifiedBy: new FormControl(categorieRawValue.lastModifiedBy, {
        validators: [Validators.maxLength(50)],
      }),
      lastModifiedDate: new FormControl(categorieRawValue.lastModifiedDate),
    });
  }

  getCategorie(form: CategorieFormGroup): ICategorie | NewCategorie {
    return this.convertCategorieRawValueToCategorie(form.getRawValue() as CategorieFormRawValue | NewCategorieFormRawValue);
  }

  resetForm(form: CategorieFormGroup, categorie: CategorieFormGroupInput): void {
    const categorieRawValue = this.convertCategorieToCategorieRawValue({ ...this.getFormDefaults(), ...categorie });
    form.reset(
      {
        ...categorieRawValue,
        id: { value: categorieRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CategorieFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      deleteAt: currentTime,
      createdDate: currentTime,
      lastModifiedDate: currentTime,
    };
  }

  private convertCategorieRawValueToCategorie(rawCategorie: CategorieFormRawValue | NewCategorieFormRawValue): ICategorie | NewCategorie {
    return {
      ...rawCategorie,
      deleteAt: dayjs(rawCategorie.deleteAt, DATE_TIME_FORMAT),
      createdDate: dayjs(rawCategorie.createdDate, DATE_TIME_FORMAT),
      lastModifiedDate: dayjs(rawCategorie.lastModifiedDate, DATE_TIME_FORMAT),
    };
  }

  private convertCategorieToCategorieRawValue(
    categorie: ICategorie | (Partial<NewCategorie> & CategorieFormDefaults),
  ): CategorieFormRawValue | PartialWithRequiredKeyOf<NewCategorieFormRawValue> {
    return {
      ...categorie,
      deleteAt: categorie.deleteAt ? categorie.deleteAt.format(DATE_TIME_FORMAT) : undefined,
      createdDate: categorie.createdDate ? categorie.createdDate.format(DATE_TIME_FORMAT) : undefined,
      lastModifiedDate: categorie.lastModifiedDate ? categorie.lastModifiedDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
