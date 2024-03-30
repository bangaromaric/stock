import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IMouvementStock, NewMouvementStock } from '../mouvement-stock.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMouvementStock for edit and NewMouvementStockFormGroupInput for create.
 */
type MouvementStockFormGroupInput = IMouvementStock | PartialWithRequiredKeyOf<NewMouvementStock>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IMouvementStock | NewMouvementStock> = Omit<
  T,
  'transactionDate' | 'deleteAt' | 'createdDate' | 'lastModifiedDate'
> & {
  transactionDate?: string | null;
  deleteAt?: string | null;
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

type MouvementStockFormRawValue = FormValueOf<IMouvementStock>;

type NewMouvementStockFormRawValue = FormValueOf<NewMouvementStock>;

type MouvementStockFormDefaults = Pick<NewMouvementStock, 'id' | 'transactionDate' | 'deleteAt' | 'createdDate' | 'lastModifiedDate'>;

type MouvementStockFormGroupContent = {
  id: FormControl<MouvementStockFormRawValue['id'] | NewMouvementStock['id']>;
  quantite: FormControl<MouvementStockFormRawValue['quantite']>;
  typeMouvement: FormControl<MouvementStockFormRawValue['typeMouvement']>;
  transactionDate: FormControl<MouvementStockFormRawValue['transactionDate']>;
  deleteAt: FormControl<MouvementStockFormRawValue['deleteAt']>;
  createdBy: FormControl<MouvementStockFormRawValue['createdBy']>;
  createdDate: FormControl<MouvementStockFormRawValue['createdDate']>;
  lastModifiedBy: FormControl<MouvementStockFormRawValue['lastModifiedBy']>;
  lastModifiedDate: FormControl<MouvementStockFormRawValue['lastModifiedDate']>;
  produit: FormControl<MouvementStockFormRawValue['produit']>;
  entrepot: FormControl<MouvementStockFormRawValue['entrepot']>;
};

export type MouvementStockFormGroup = FormGroup<MouvementStockFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MouvementStockFormService {
  createMouvementStockFormGroup(mouvementStock: MouvementStockFormGroupInput = { id: null }): MouvementStockFormGroup {
    const mouvementStockRawValue = this.convertMouvementStockToMouvementStockRawValue({
      ...this.getFormDefaults(),
      ...mouvementStock,
    });
    return new FormGroup<MouvementStockFormGroupContent>({
      id: new FormControl(
        { value: mouvementStockRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      quantite: new FormControl(mouvementStockRawValue.quantite, {
        validators: [Validators.required],
      }),
      typeMouvement: new FormControl(mouvementStockRawValue.typeMouvement, {
        validators: [Validators.required],
      }),
      transactionDate: new FormControl(mouvementStockRawValue.transactionDate, {
        validators: [Validators.required],
      }),
      deleteAt: new FormControl(mouvementStockRawValue.deleteAt),
      createdBy: new FormControl(mouvementStockRawValue.createdBy, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      createdDate: new FormControl(mouvementStockRawValue.createdDate),
      lastModifiedBy: new FormControl(mouvementStockRawValue.lastModifiedBy, {
        validators: [Validators.maxLength(50)],
      }),
      lastModifiedDate: new FormControl(mouvementStockRawValue.lastModifiedDate),
      produit: new FormControl(mouvementStockRawValue.produit, {
        validators: [Validators.required],
      }),
      entrepot: new FormControl(mouvementStockRawValue.entrepot, {
        validators: [Validators.required],
      }),
    });
  }

  getMouvementStock(form: MouvementStockFormGroup): IMouvementStock | NewMouvementStock {
    return this.convertMouvementStockRawValueToMouvementStock(
      form.getRawValue() as MouvementStockFormRawValue | NewMouvementStockFormRawValue,
    );
  }

  resetForm(form: MouvementStockFormGroup, mouvementStock: MouvementStockFormGroupInput): void {
    const mouvementStockRawValue = this.convertMouvementStockToMouvementStockRawValue({ ...this.getFormDefaults(), ...mouvementStock });
    form.reset(
      {
        ...mouvementStockRawValue,
        id: { value: mouvementStockRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): MouvementStockFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      transactionDate: currentTime,
      deleteAt: currentTime,
      createdDate: currentTime,
      lastModifiedDate: currentTime,
    };
  }

  private convertMouvementStockRawValueToMouvementStock(
    rawMouvementStock: MouvementStockFormRawValue | NewMouvementStockFormRawValue,
  ): IMouvementStock | NewMouvementStock {
    return {
      ...rawMouvementStock,
      transactionDate: dayjs(rawMouvementStock.transactionDate, DATE_TIME_FORMAT),
      deleteAt: dayjs(rawMouvementStock.deleteAt, DATE_TIME_FORMAT),
      createdDate: dayjs(rawMouvementStock.createdDate, DATE_TIME_FORMAT),
      lastModifiedDate: dayjs(rawMouvementStock.lastModifiedDate, DATE_TIME_FORMAT),
    };
  }

  private convertMouvementStockToMouvementStockRawValue(
    mouvementStock: IMouvementStock | (Partial<NewMouvementStock> & MouvementStockFormDefaults),
  ): MouvementStockFormRawValue | PartialWithRequiredKeyOf<NewMouvementStockFormRawValue> {
    return {
      ...mouvementStock,
      transactionDate: mouvementStock.transactionDate ? mouvementStock.transactionDate.format(DATE_TIME_FORMAT) : undefined,
      deleteAt: mouvementStock.deleteAt ? mouvementStock.deleteAt.format(DATE_TIME_FORMAT) : undefined,
      createdDate: mouvementStock.createdDate ? mouvementStock.createdDate.format(DATE_TIME_FORMAT) : undefined,
      lastModifiedDate: mouvementStock.lastModifiedDate ? mouvementStock.lastModifiedDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
