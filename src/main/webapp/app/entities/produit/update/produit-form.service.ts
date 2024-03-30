import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IProduit, NewProduit } from '../produit.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProduit for edit and NewProduitFormGroupInput for create.
 */
type ProduitFormGroupInput = IProduit | PartialWithRequiredKeyOf<NewProduit>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IProduit | NewProduit> = Omit<T, 'deleteAt' | 'createdDate' | 'lastModifiedDate'> & {
  deleteAt?: string | null;
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

type ProduitFormRawValue = FormValueOf<IProduit>;

type NewProduitFormRawValue = FormValueOf<NewProduit>;

type ProduitFormDefaults = Pick<NewProduit, 'id' | 'deleteAt' | 'createdDate' | 'lastModifiedDate'>;

type ProduitFormGroupContent = {
  id: FormControl<ProduitFormRawValue['id'] | NewProduit['id']>;
  libelle: FormControl<ProduitFormRawValue['libelle']>;
  description: FormControl<ProduitFormRawValue['description']>;
  slug: FormControl<ProduitFormRawValue['slug']>;
  prixUnitaire: FormControl<ProduitFormRawValue['prixUnitaire']>;
  dateExpiration: FormControl<ProduitFormRawValue['dateExpiration']>;
  deleteAt: FormControl<ProduitFormRawValue['deleteAt']>;
  createdBy: FormControl<ProduitFormRawValue['createdBy']>;
  createdDate: FormControl<ProduitFormRawValue['createdDate']>;
  lastModifiedBy: FormControl<ProduitFormRawValue['lastModifiedBy']>;
  lastModifiedDate: FormControl<ProduitFormRawValue['lastModifiedDate']>;
  categorie: FormControl<ProduitFormRawValue['categorie']>;
};

export type ProduitFormGroup = FormGroup<ProduitFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProduitFormService {
  createProduitFormGroup(produit: ProduitFormGroupInput = { id: null }): ProduitFormGroup {
    const produitRawValue = this.convertProduitToProduitRawValue({
      ...this.getFormDefaults(),
      ...produit,
    });
    return new FormGroup<ProduitFormGroupContent>({
      id: new FormControl(
        { value: produitRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      libelle: new FormControl(produitRawValue.libelle, {
        validators: [Validators.required],
      }),
      description: new FormControl(produitRawValue.description),
      slug: new FormControl(produitRawValue.slug, {
        validators: [Validators.required],
      }),
      prixUnitaire: new FormControl(produitRawValue.prixUnitaire, {
        validators: [Validators.required],
      }),
      dateExpiration: new FormControl(produitRawValue.dateExpiration),
      deleteAt: new FormControl(produitRawValue.deleteAt),
      createdBy: new FormControl(produitRawValue.createdBy, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      createdDate: new FormControl(produitRawValue.createdDate),
      lastModifiedBy: new FormControl(produitRawValue.lastModifiedBy, {
        validators: [Validators.maxLength(50)],
      }),
      lastModifiedDate: new FormControl(produitRawValue.lastModifiedDate),
      categorie: new FormControl(produitRawValue.categorie, {
        validators: [Validators.required],
      }),
    });
  }

  getProduit(form: ProduitFormGroup): IProduit | NewProduit {
    return this.convertProduitRawValueToProduit(form.getRawValue() as ProduitFormRawValue | NewProduitFormRawValue);
  }

  resetForm(form: ProduitFormGroup, produit: ProduitFormGroupInput): void {
    const produitRawValue = this.convertProduitToProduitRawValue({ ...this.getFormDefaults(), ...produit });
    form.reset(
      {
        ...produitRawValue,
        id: { value: produitRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ProduitFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      deleteAt: currentTime,
      createdDate: currentTime,
      lastModifiedDate: currentTime,
    };
  }

  private convertProduitRawValueToProduit(rawProduit: ProduitFormRawValue | NewProduitFormRawValue): IProduit | NewProduit {
    return {
      ...rawProduit,
      deleteAt: dayjs(rawProduit.deleteAt, DATE_TIME_FORMAT),
      createdDate: dayjs(rawProduit.createdDate, DATE_TIME_FORMAT),
      lastModifiedDate: dayjs(rawProduit.lastModifiedDate, DATE_TIME_FORMAT),
    };
  }

  private convertProduitToProduitRawValue(
    produit: IProduit | (Partial<NewProduit> & ProduitFormDefaults),
  ): ProduitFormRawValue | PartialWithRequiredKeyOf<NewProduitFormRawValue> {
    return {
      ...produit,
      deleteAt: produit.deleteAt ? produit.deleteAt.format(DATE_TIME_FORMAT) : undefined,
      createdDate: produit.createdDate ? produit.createdDate.format(DATE_TIME_FORMAT) : undefined,
      lastModifiedDate: produit.lastModifiedDate ? produit.lastModifiedDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
