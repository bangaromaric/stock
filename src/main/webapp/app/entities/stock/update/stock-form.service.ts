import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IStock, NewStock } from '../stock.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IStock for edit and NewStockFormGroupInput for create.
 */
type StockFormGroupInput = IStock | PartialWithRequiredKeyOf<NewStock>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IStock | NewStock> = Omit<T, 'deleteAt' | 'createdDate' | 'lastModifiedDate'> & {
  deleteAt?: string | null;
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

type StockFormRawValue = FormValueOf<IStock>;

type NewStockFormRawValue = FormValueOf<NewStock>;

type StockFormDefaults = Pick<NewStock, 'id' | 'deleteAt' | 'createdDate' | 'lastModifiedDate'>;

type StockFormGroupContent = {
  id: FormControl<StockFormRawValue['id'] | NewStock['id']>;
  quantite: FormControl<StockFormRawValue['quantite']>;
  deleteAt: FormControl<StockFormRawValue['deleteAt']>;
  createdBy: FormControl<StockFormRawValue['createdBy']>;
  createdDate: FormControl<StockFormRawValue['createdDate']>;
  lastModifiedBy: FormControl<StockFormRawValue['lastModifiedBy']>;
  lastModifiedDate: FormControl<StockFormRawValue['lastModifiedDate']>;
  entrepot: FormControl<StockFormRawValue['entrepot']>;
  produit: FormControl<StockFormRawValue['produit']>;
};

export type StockFormGroup = FormGroup<StockFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class StockFormService {
  createStockFormGroup(stock: StockFormGroupInput = { id: null }): StockFormGroup {
    const stockRawValue = this.convertStockToStockRawValue({
      ...this.getFormDefaults(),
      ...stock,
    });
    return new FormGroup<StockFormGroupContent>({
      id: new FormControl(
        { value: stockRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      quantite: new FormControl(stockRawValue.quantite, {
        validators: [Validators.required],
      }),
      deleteAt: new FormControl(stockRawValue.deleteAt),
      createdBy: new FormControl(stockRawValue.createdBy, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      createdDate: new FormControl(stockRawValue.createdDate),
      lastModifiedBy: new FormControl(stockRawValue.lastModifiedBy, {
        validators: [Validators.maxLength(50)],
      }),
      lastModifiedDate: new FormControl(stockRawValue.lastModifiedDate),
      entrepot: new FormControl(stockRawValue.entrepot, {
        validators: [Validators.required],
      }),
      produit: new FormControl(stockRawValue.produit, {
        validators: [Validators.required],
      }),
    });
  }

  getStock(form: StockFormGroup): IStock | NewStock {
    return this.convertStockRawValueToStock(form.getRawValue() as StockFormRawValue | NewStockFormRawValue);
  }

  resetForm(form: StockFormGroup, stock: StockFormGroupInput): void {
    const stockRawValue = this.convertStockToStockRawValue({ ...this.getFormDefaults(), ...stock });
    form.reset(
      {
        ...stockRawValue,
        id: { value: stockRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): StockFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      deleteAt: currentTime,
      createdDate: currentTime,
      lastModifiedDate: currentTime,
    };
  }

  private convertStockRawValueToStock(rawStock: StockFormRawValue | NewStockFormRawValue): IStock | NewStock {
    return {
      ...rawStock,
      deleteAt: dayjs(rawStock.deleteAt, DATE_TIME_FORMAT),
      createdDate: dayjs(rawStock.createdDate, DATE_TIME_FORMAT),
      lastModifiedDate: dayjs(rawStock.lastModifiedDate, DATE_TIME_FORMAT),
    };
  }

  private convertStockToStockRawValue(
    stock: IStock | (Partial<NewStock> & StockFormDefaults),
  ): StockFormRawValue | PartialWithRequiredKeyOf<NewStockFormRawValue> {
    return {
      ...stock,
      deleteAt: stock.deleteAt ? stock.deleteAt.format(DATE_TIME_FORMAT) : undefined,
      createdDate: stock.createdDate ? stock.createdDate.format(DATE_TIME_FORMAT) : undefined,
      lastModifiedDate: stock.lastModifiedDate ? stock.lastModifiedDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
