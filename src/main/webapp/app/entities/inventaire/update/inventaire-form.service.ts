import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IInventaire, NewInventaire } from '../inventaire.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IInventaire for edit and NewInventaireFormGroupInput for create.
 */
type InventaireFormGroupInput = IInventaire | PartialWithRequiredKeyOf<NewInventaire>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IInventaire | NewInventaire> = Omit<T, 'inventaireDate' | 'deleteAt' | 'createdDate' | 'lastModifiedDate'> & {
  inventaireDate?: string | null;
  deleteAt?: string | null;
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

type InventaireFormRawValue = FormValueOf<IInventaire>;

type NewInventaireFormRawValue = FormValueOf<NewInventaire>;

type InventaireFormDefaults = Pick<NewInventaire, 'id' | 'inventaireDate' | 'deleteAt' | 'createdDate' | 'lastModifiedDate'>;

type InventaireFormGroupContent = {
  id: FormControl<InventaireFormRawValue['id'] | NewInventaire['id']>;
  quantite: FormControl<InventaireFormRawValue['quantite']>;
  inventaireDate: FormControl<InventaireFormRawValue['inventaireDate']>;
  deleteAt: FormControl<InventaireFormRawValue['deleteAt']>;
  createdBy: FormControl<InventaireFormRawValue['createdBy']>;
  createdDate: FormControl<InventaireFormRawValue['createdDate']>;
  lastModifiedBy: FormControl<InventaireFormRawValue['lastModifiedBy']>;
  lastModifiedDate: FormControl<InventaireFormRawValue['lastModifiedDate']>;
  entrepot: FormControl<InventaireFormRawValue['entrepot']>;
  produit: FormControl<InventaireFormRawValue['produit']>;
};

export type InventaireFormGroup = FormGroup<InventaireFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class InventaireFormService {
  createInventaireFormGroup(inventaire: InventaireFormGroupInput = { id: null }): InventaireFormGroup {
    const inventaireRawValue = this.convertInventaireToInventaireRawValue({
      ...this.getFormDefaults(),
      ...inventaire,
    });
    return new FormGroup<InventaireFormGroupContent>({
      id: new FormControl(
        { value: inventaireRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      quantite: new FormControl(inventaireRawValue.quantite, {
        validators: [Validators.required],
      }),
      inventaireDate: new FormControl(inventaireRawValue.inventaireDate, {
        validators: [Validators.required],
      }),
      deleteAt: new FormControl(inventaireRawValue.deleteAt),
      createdBy: new FormControl(inventaireRawValue.createdBy, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      createdDate: new FormControl(inventaireRawValue.createdDate),
      lastModifiedBy: new FormControl(inventaireRawValue.lastModifiedBy, {
        validators: [Validators.maxLength(50)],
      }),
      lastModifiedDate: new FormControl(inventaireRawValue.lastModifiedDate),
      entrepot: new FormControl(inventaireRawValue.entrepot, {
        validators: [Validators.required],
      }),
      produit: new FormControl(inventaireRawValue.produit, {
        validators: [Validators.required],
      }),
    });
  }

  getInventaire(form: InventaireFormGroup): IInventaire | NewInventaire {
    return this.convertInventaireRawValueToInventaire(form.getRawValue() as InventaireFormRawValue | NewInventaireFormRawValue);
  }

  resetForm(form: InventaireFormGroup, inventaire: InventaireFormGroupInput): void {
    const inventaireRawValue = this.convertInventaireToInventaireRawValue({ ...this.getFormDefaults(), ...inventaire });
    form.reset(
      {
        ...inventaireRawValue,
        id: { value: inventaireRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): InventaireFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      inventaireDate: currentTime,
      deleteAt: currentTime,
      createdDate: currentTime,
      lastModifiedDate: currentTime,
    };
  }

  private convertInventaireRawValueToInventaire(
    rawInventaire: InventaireFormRawValue | NewInventaireFormRawValue,
  ): IInventaire | NewInventaire {
    return {
      ...rawInventaire,
      inventaireDate: dayjs(rawInventaire.inventaireDate, DATE_TIME_FORMAT),
      deleteAt: dayjs(rawInventaire.deleteAt, DATE_TIME_FORMAT),
      createdDate: dayjs(rawInventaire.createdDate, DATE_TIME_FORMAT),
      lastModifiedDate: dayjs(rawInventaire.lastModifiedDate, DATE_TIME_FORMAT),
    };
  }

  private convertInventaireToInventaireRawValue(
    inventaire: IInventaire | (Partial<NewInventaire> & InventaireFormDefaults),
  ): InventaireFormRawValue | PartialWithRequiredKeyOf<NewInventaireFormRawValue> {
    return {
      ...inventaire,
      inventaireDate: inventaire.inventaireDate ? inventaire.inventaireDate.format(DATE_TIME_FORMAT) : undefined,
      deleteAt: inventaire.deleteAt ? inventaire.deleteAt.format(DATE_TIME_FORMAT) : undefined,
      createdDate: inventaire.createdDate ? inventaire.createdDate.format(DATE_TIME_FORMAT) : undefined,
      lastModifiedDate: inventaire.lastModifiedDate ? inventaire.lastModifiedDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
