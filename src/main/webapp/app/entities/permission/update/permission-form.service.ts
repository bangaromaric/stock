import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPermission, NewPermission } from '../permission.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPermission for edit and NewPermissionFormGroupInput for create.
 */
type PermissionFormGroupInput = IPermission | PartialWithRequiredKeyOf<NewPermission>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IPermission | NewPermission> = Omit<T, 'deleteAt' | 'createdDate' | 'lastModifiedDate'> & {
  deleteAt?: string | null;
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

type PermissionFormRawValue = FormValueOf<IPermission>;

type NewPermissionFormRawValue = FormValueOf<NewPermission>;

type PermissionFormDefaults = Pick<NewPermission, 'id' | 'deleteAt' | 'createdDate' | 'lastModifiedDate' | 'authorities'>;

type PermissionFormGroupContent = {
  id: FormControl<PermissionFormRawValue['id'] | NewPermission['id']>;
  ressource: FormControl<PermissionFormRawValue['ressource']>;
  action: FormControl<PermissionFormRawValue['action']>;
  deleteAt: FormControl<PermissionFormRawValue['deleteAt']>;
  createdDate: FormControl<PermissionFormRawValue['createdDate']>;
  createdBy: FormControl<PermissionFormRawValue['createdBy']>;
  lastModifiedDate: FormControl<PermissionFormRawValue['lastModifiedDate']>;
  lastModifiedBy: FormControl<PermissionFormRawValue['lastModifiedBy']>;
  authorities: FormControl<PermissionFormRawValue['authorities']>;
};

export type PermissionFormGroup = FormGroup<PermissionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PermissionFormService {
  createPermissionFormGroup(permission: PermissionFormGroupInput = { id: null }): PermissionFormGroup {
    const permissionRawValue = this.convertPermissionToPermissionRawValue({
      ...this.getFormDefaults(),
      ...permission,
    });
    return new FormGroup<PermissionFormGroupContent>({
      id: new FormControl(
        { value: permissionRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      ressource: new FormControl(permissionRawValue.ressource, {
        validators: [Validators.required],
      }),
      action: new FormControl(permissionRawValue.action, {
        validators: [Validators.required],
      }),
      deleteAt: new FormControl(permissionRawValue.deleteAt),
      createdDate: new FormControl(permissionRawValue.createdDate, {
        validators: [Validators.required],
      }),
      createdBy: new FormControl(permissionRawValue.createdBy),
      lastModifiedDate: new FormControl(permissionRawValue.lastModifiedDate),
      lastModifiedBy: new FormControl(permissionRawValue.lastModifiedBy),
      authorities: new FormControl(permissionRawValue.authorities ?? []),
    });
  }

  getPermission(form: PermissionFormGroup): IPermission | NewPermission {
    return this.convertPermissionRawValueToPermission(form.getRawValue() as PermissionFormRawValue | NewPermissionFormRawValue);
  }

  resetForm(form: PermissionFormGroup, permission: PermissionFormGroupInput): void {
    const permissionRawValue = this.convertPermissionToPermissionRawValue({ ...this.getFormDefaults(), ...permission });
    form.reset(
      {
        ...permissionRawValue,
        id: { value: permissionRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PermissionFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      deleteAt: currentTime,
      createdDate: currentTime,
      lastModifiedDate: currentTime,
      authorities: [],
    };
  }

  private convertPermissionRawValueToPermission(
    rawPermission: PermissionFormRawValue | NewPermissionFormRawValue,
  ): IPermission | NewPermission {
    return {
      ...rawPermission,
      deleteAt: dayjs(rawPermission.deleteAt, DATE_TIME_FORMAT),
      createdDate: dayjs(rawPermission.createdDate, DATE_TIME_FORMAT),
      lastModifiedDate: dayjs(rawPermission.lastModifiedDate, DATE_TIME_FORMAT),
    };
  }

  private convertPermissionToPermissionRawValue(
    permission: IPermission | (Partial<NewPermission> & PermissionFormDefaults),
  ): PermissionFormRawValue | PartialWithRequiredKeyOf<NewPermissionFormRawValue> {
    return {
      ...permission,
      deleteAt: permission.deleteAt ? permission.deleteAt.format(DATE_TIME_FORMAT) : undefined,
      createdDate: permission.createdDate ? permission.createdDate.format(DATE_TIME_FORMAT) : undefined,
      lastModifiedDate: permission.lastModifiedDate ? permission.lastModifiedDate.format(DATE_TIME_FORMAT) : undefined,
      authorities: permission.authorities ?? [],
    };
  }
}
