import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IVente, NewVente } from '../vente.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IVente for edit and NewVenteFormGroupInput for create.
 */
type VenteFormGroupInput = IVente | PartialWithRequiredKeyOf<NewVente>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IVente | NewVente> = Omit<T, 'venteDate' | 'deleteAt' | 'createdDate' | 'lastModifiedDate'> & {
  venteDate?: string | null;
  deleteAt?: string | null;
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

type VenteFormRawValue = FormValueOf<IVente>;

type NewVenteFormRawValue = FormValueOf<NewVente>;

type VenteFormDefaults = Pick<NewVente, 'id' | 'venteDate' | 'deleteAt' | 'createdDate' | 'lastModifiedDate'>;

type VenteFormGroupContent = {
  id: FormControl<VenteFormRawValue['id'] | NewVente['id']>;
  quantite: FormControl<VenteFormRawValue['quantite']>;
  montant: FormControl<VenteFormRawValue['montant']>;
  venteDate: FormControl<VenteFormRawValue['venteDate']>;
  deleteAt: FormControl<VenteFormRawValue['deleteAt']>;
  createdBy: FormControl<VenteFormRawValue['createdBy']>;
  createdDate: FormControl<VenteFormRawValue['createdDate']>;
  lastModifiedBy: FormControl<VenteFormRawValue['lastModifiedBy']>;
  lastModifiedDate: FormControl<VenteFormRawValue['lastModifiedDate']>;
  produit: FormControl<VenteFormRawValue['produit']>;
  structure: FormControl<VenteFormRawValue['structure']>;
};

export type VenteFormGroup = FormGroup<VenteFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class VenteFormService {
  createVenteFormGroup(vente: VenteFormGroupInput = { id: null }): VenteFormGroup {
    const venteRawValue = this.convertVenteToVenteRawValue({
      ...this.getFormDefaults(),
      ...vente,
    });
    return new FormGroup<VenteFormGroupContent>({
      id: new FormControl(
        { value: venteRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      quantite: new FormControl(venteRawValue.quantite, {
        validators: [Validators.required],
      }),
      montant: new FormControl(venteRawValue.montant, {
        validators: [Validators.required],
      }),
      venteDate: new FormControl(venteRawValue.venteDate, {
        validators: [Validators.required],
      }),
      deleteAt: new FormControl(venteRawValue.deleteAt),
      createdBy: new FormControl(venteRawValue.createdBy, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      createdDate: new FormControl(venteRawValue.createdDate),
      lastModifiedBy: new FormControl(venteRawValue.lastModifiedBy, {
        validators: [Validators.maxLength(50)],
      }),
      lastModifiedDate: new FormControl(venteRawValue.lastModifiedDate),
      produit: new FormControl(venteRawValue.produit, {
        validators: [Validators.required],
      }),
      structure: new FormControl(venteRawValue.structure, {
        validators: [Validators.required],
      }),
    });
  }

  getVente(form: VenteFormGroup): IVente | NewVente {
    return this.convertVenteRawValueToVente(form.getRawValue() as VenteFormRawValue | NewVenteFormRawValue);
  }

  resetForm(form: VenteFormGroup, vente: VenteFormGroupInput): void {
    const venteRawValue = this.convertVenteToVenteRawValue({ ...this.getFormDefaults(), ...vente });
    form.reset(
      {
        ...venteRawValue,
        id: { value: venteRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): VenteFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      venteDate: currentTime,
      deleteAt: currentTime,
      createdDate: currentTime,
      lastModifiedDate: currentTime,
    };
  }

  private convertVenteRawValueToVente(rawVente: VenteFormRawValue | NewVenteFormRawValue): IVente | NewVente {
    return {
      ...rawVente,
      venteDate: dayjs(rawVente.venteDate, DATE_TIME_FORMAT),
      deleteAt: dayjs(rawVente.deleteAt, DATE_TIME_FORMAT),
      createdDate: dayjs(rawVente.createdDate, DATE_TIME_FORMAT),
      lastModifiedDate: dayjs(rawVente.lastModifiedDate, DATE_TIME_FORMAT),
    };
  }

  private convertVenteToVenteRawValue(
    vente: IVente | (Partial<NewVente> & VenteFormDefaults),
  ): VenteFormRawValue | PartialWithRequiredKeyOf<NewVenteFormRawValue> {
    return {
      ...vente,
      venteDate: vente.venteDate ? vente.venteDate.format(DATE_TIME_FORMAT) : undefined,
      deleteAt: vente.deleteAt ? vente.deleteAt.format(DATE_TIME_FORMAT) : undefined,
      createdDate: vente.createdDate ? vente.createdDate.format(DATE_TIME_FORMAT) : undefined,
      lastModifiedDate: vente.lastModifiedDate ? vente.lastModifiedDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
