import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IStructure, NewStructure } from '../structure.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IStructure for edit and NewStructureFormGroupInput for create.
 */
type StructureFormGroupInput = IStructure | PartialWithRequiredKeyOf<NewStructure>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IStructure | NewStructure> = Omit<T, 'deleteAt' | 'createdDate' | 'lastModifiedDate'> & {
  deleteAt?: string | null;
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

type StructureFormRawValue = FormValueOf<IStructure>;

type NewStructureFormRawValue = FormValueOf<NewStructure>;

type StructureFormDefaults = Pick<NewStructure, 'id' | 'deleteAt' | 'createdDate' | 'lastModifiedDate'>;

type StructureFormGroupContent = {
  id: FormControl<StructureFormRawValue['id'] | NewStructure['id']>;
  libelle: FormControl<StructureFormRawValue['libelle']>;
  telephone: FormControl<StructureFormRawValue['telephone']>;
  email: FormControl<StructureFormRawValue['email']>;
  adresse: FormControl<StructureFormRawValue['adresse']>;
  deleteAt: FormControl<StructureFormRawValue['deleteAt']>;
  createdBy: FormControl<StructureFormRawValue['createdBy']>;
  createdDate: FormControl<StructureFormRawValue['createdDate']>;
  lastModifiedBy: FormControl<StructureFormRawValue['lastModifiedBy']>;
  lastModifiedDate: FormControl<StructureFormRawValue['lastModifiedDate']>;
};

export type StructureFormGroup = FormGroup<StructureFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class StructureFormService {
  createStructureFormGroup(structure: StructureFormGroupInput = { id: null }): StructureFormGroup {
    const structureRawValue = this.convertStructureToStructureRawValue({
      ...this.getFormDefaults(),
      ...structure,
    });
    return new FormGroup<StructureFormGroupContent>({
      id: new FormControl(
        { value: structureRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      libelle: new FormControl(structureRawValue.libelle, {
        validators: [Validators.required],
      }),
      telephone: new FormControl(structureRawValue.telephone, {
        validators: [Validators.required],
      }),
      email: new FormControl(structureRawValue.email, {
        validators: [Validators.required, Validators.pattern('^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$')],
      }),
      adresse: new FormControl(structureRawValue.adresse),
      deleteAt: new FormControl(structureRawValue.deleteAt),
      createdBy: new FormControl(structureRawValue.createdBy, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      createdDate: new FormControl(structureRawValue.createdDate),
      lastModifiedBy: new FormControl(structureRawValue.lastModifiedBy, {
        validators: [Validators.maxLength(50)],
      }),
      lastModifiedDate: new FormControl(structureRawValue.lastModifiedDate),
    });
  }

  getStructure(form: StructureFormGroup): IStructure | NewStructure {
    return this.convertStructureRawValueToStructure(form.getRawValue() as StructureFormRawValue | NewStructureFormRawValue);
  }

  resetForm(form: StructureFormGroup, structure: StructureFormGroupInput): void {
    const structureRawValue = this.convertStructureToStructureRawValue({ ...this.getFormDefaults(), ...structure });
    form.reset(
      {
        ...structureRawValue,
        id: { value: structureRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): StructureFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      deleteAt: currentTime,
      createdDate: currentTime,
      lastModifiedDate: currentTime,
    };
  }

  private convertStructureRawValueToStructure(rawStructure: StructureFormRawValue | NewStructureFormRawValue): IStructure | NewStructure {
    return {
      ...rawStructure,
      deleteAt: dayjs(rawStructure.deleteAt, DATE_TIME_FORMAT),
      createdDate: dayjs(rawStructure.createdDate, DATE_TIME_FORMAT),
      lastModifiedDate: dayjs(rawStructure.lastModifiedDate, DATE_TIME_FORMAT),
    };
  }

  private convertStructureToStructureRawValue(
    structure: IStructure | (Partial<NewStructure> & StructureFormDefaults),
  ): StructureFormRawValue | PartialWithRequiredKeyOf<NewStructureFormRawValue> {
    return {
      ...structure,
      deleteAt: structure.deleteAt ? structure.deleteAt.format(DATE_TIME_FORMAT) : undefined,
      createdDate: structure.createdDate ? structure.createdDate.format(DATE_TIME_FORMAT) : undefined,
      lastModifiedDate: structure.lastModifiedDate ? structure.lastModifiedDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
