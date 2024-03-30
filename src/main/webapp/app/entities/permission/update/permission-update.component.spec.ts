import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IAuthority } from 'app/entities/admin/authority/authority.model';
import { AuthorityService } from 'app/entities/admin/authority/service/authority.service';
import { PermissionService } from '../service/permission.service';
import { IPermission } from '../permission.model';
import { PermissionFormService } from './permission-form.service';

import { PermissionUpdateComponent } from './permission-update.component';

describe('Permission Management Update Component', () => {
  let comp: PermissionUpdateComponent;
  let fixture: ComponentFixture<PermissionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let permissionFormService: PermissionFormService;
  let permissionService: PermissionService;
  let authorityService: AuthorityService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), PermissionUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PermissionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PermissionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    permissionFormService = TestBed.inject(PermissionFormService);
    permissionService = TestBed.inject(PermissionService);
    authorityService = TestBed.inject(AuthorityService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Authority query and add missing value', () => {
      const permission: IPermission = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const authorities: IAuthority[] = [{ name: '52bcab10-b0c2-4894-a09b-f41b2b81fc35' }];
      permission.authorities = authorities;

      const authorityCollection: IAuthority[] = [{ name: 'ccbee0f1-2977-4d8e-8497-b9d186dbbb9d' }];
      jest.spyOn(authorityService, 'query').mockReturnValue(of(new HttpResponse({ body: authorityCollection })));
      const additionalAuthorities = [...authorities];
      const expectedCollection: IAuthority[] = [...additionalAuthorities, ...authorityCollection];
      jest.spyOn(authorityService, 'addAuthorityToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ permission });
      comp.ngOnInit();

      expect(authorityService.query).toHaveBeenCalled();
      expect(authorityService.addAuthorityToCollectionIfMissing).toHaveBeenCalledWith(
        authorityCollection,
        ...additionalAuthorities.map(expect.objectContaining),
      );
      expect(comp.authoritiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const permission: IPermission = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const authority: IAuthority = { name: '37bbc0d2-5c2c-44f4-a372-ab2ffbb93d51' };
      permission.authorities = [authority];

      activatedRoute.data = of({ permission });
      comp.ngOnInit();

      expect(comp.authoritiesSharedCollection).toContain(authority);
      expect(comp.permission).toEqual(permission);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPermission>>();
      const permission = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(permissionFormService, 'getPermission').mockReturnValue(permission);
      jest.spyOn(permissionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ permission });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: permission }));
      saveSubject.complete();

      // THEN
      expect(permissionFormService.getPermission).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(permissionService.update).toHaveBeenCalledWith(expect.objectContaining(permission));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPermission>>();
      const permission = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(permissionFormService, 'getPermission').mockReturnValue({ id: null });
      jest.spyOn(permissionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ permission: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: permission }));
      saveSubject.complete();

      // THEN
      expect(permissionFormService.getPermission).toHaveBeenCalled();
      expect(permissionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPermission>>();
      const permission = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(permissionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ permission });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(permissionService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareAuthority', () => {
      it('Should forward to authorityService', () => {
        const entity = { name: 'ABC' };
        const entity2 = { name: 'CBA' };
        jest.spyOn(authorityService, 'compareAuthority');
        comp.compareAuthority(entity, entity2);
        expect(authorityService.compareAuthority).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
