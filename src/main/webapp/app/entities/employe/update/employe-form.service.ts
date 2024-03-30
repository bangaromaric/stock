import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IEmploye, NewEmploye } from '../employe.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEmploye for edit and NewEmployeFormGroupInput for create.
 */
type EmployeFormGroupInput = IEmploye | PartialWithRequiredKeyOf<NewEmploye>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IEmploye | NewEmploye> = Omit<T, 'deleteAt' | 'createdDate' | 'lastModifiedDate'> & {
  deleteAt?: string | null;
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

type EmployeFormRawValue = FormValueOf<IEmploye>;

type NewEmployeFormRawValue = FormValueOf<NewEmploye>;

type EmployeFormDefaults = Pick<NewEmploye, 'id' | 'deleteAt' | 'createdDate' | 'lastModifiedDate'>;

type EmployeFormGroupContent = {
  id: FormControl<EmployeFormRawValue['id'] | NewEmploye['id']>;
  firstName: FormControl<EmployeFormRawValue['firstName']>;
  lastName: FormControl<EmployeFormRawValue['lastName']>;
  login: FormControl<EmployeFormRawValue['login']>;
  email: FormControl<EmployeFormRawValue['email']>;
  deleteAt: FormControl<EmployeFormRawValue['deleteAt']>;
  createdBy: FormControl<EmployeFormRawValue['createdBy']>;
  createdDate: FormControl<EmployeFormRawValue['createdDate']>;
  lastModifiedBy: FormControl<EmployeFormRawValue['lastModifiedBy']>;
  lastModifiedDate: FormControl<EmployeFormRawValue['lastModifiedDate']>;
  internalUser: FormControl<EmployeFormRawValue['internalUser']>;
  structure: FormControl<EmployeFormRawValue['structure']>;
};

export type EmployeFormGroup = FormGroup<EmployeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EmployeFormService {
  createEmployeFormGroup(employe: EmployeFormGroupInput = { id: null }): EmployeFormGroup {
    const employeRawValue = this.convertEmployeToEmployeRawValue({
      ...this.getFormDefaults(),
      ...employe,
    });
    return new FormGroup<EmployeFormGroupContent>({
      id: new FormControl(
        { value: employeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      firstName: new FormControl(employeRawValue.firstName),
      lastName: new FormControl(employeRawValue.lastName, {
        validators: [Validators.required],
      }),
      login: new FormControl(employeRawValue.login, {
        validators: [Validators.required],
      }),
      email: new FormControl(employeRawValue.email, {
        validators: [Validators.pattern('^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$')],
      }),
      deleteAt: new FormControl(employeRawValue.deleteAt),
      createdBy: new FormControl(employeRawValue.createdBy, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      createdDate: new FormControl(employeRawValue.createdDate),
      lastModifiedBy: new FormControl(employeRawValue.lastModifiedBy, {
        validators: [Validators.maxLength(50)],
      }),
      lastModifiedDate: new FormControl(employeRawValue.lastModifiedDate),
      internalUser: new FormControl(employeRawValue.internalUser),
      structure: new FormControl(employeRawValue.structure),
    });
  }

  getEmploye(form: EmployeFormGroup): IEmploye | NewEmploye {
    return this.convertEmployeRawValueToEmploye(form.getRawValue() as EmployeFormRawValue | NewEmployeFormRawValue);
  }

  resetForm(form: EmployeFormGroup, employe: EmployeFormGroupInput): void {
    const employeRawValue = this.convertEmployeToEmployeRawValue({ ...this.getFormDefaults(), ...employe });
    form.reset(
      {
        ...employeRawValue,
        id: { value: employeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EmployeFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      deleteAt: currentTime,
      createdDate: currentTime,
      lastModifiedDate: currentTime,
    };
  }

  private convertEmployeRawValueToEmploye(rawEmploye: EmployeFormRawValue | NewEmployeFormRawValue): IEmploye | NewEmploye {
    return {
      ...rawEmploye,
      deleteAt: dayjs(rawEmploye.deleteAt, DATE_TIME_FORMAT),
      createdDate: dayjs(rawEmploye.createdDate, DATE_TIME_FORMAT),
      lastModifiedDate: dayjs(rawEmploye.lastModifiedDate, DATE_TIME_FORMAT),
    };
  }

  private convertEmployeToEmployeRawValue(
    employe: IEmploye | (Partial<NewEmploye> & EmployeFormDefaults),
  ): EmployeFormRawValue | PartialWithRequiredKeyOf<NewEmployeFormRawValue> {
    return {
      ...employe,
      deleteAt: employe.deleteAt ? employe.deleteAt.format(DATE_TIME_FORMAT) : undefined,
      createdDate: employe.createdDate ? employe.createdDate.format(DATE_TIME_FORMAT) : undefined,
      lastModifiedDate: employe.lastModifiedDate ? employe.lastModifiedDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
