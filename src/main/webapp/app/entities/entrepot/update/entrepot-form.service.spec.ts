import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../entrepot.test-samples';

import { EntrepotFormService } from './entrepot-form.service';

describe('Entrepot Form Service', () => {
  let service: EntrepotFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EntrepotFormService);
  });

  describe('Service methods', () => {
    describe('createEntrepotFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEntrepotFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            libelle: expect.any(Object),
            slug: expect.any(Object),
            capacite: expect.any(Object),
            description: expect.any(Object),
            deleteAt: expect.any(Object),
            createdBy: expect.any(Object),
            createdDate: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            lastModifiedDate: expect.any(Object),
            structure: expect.any(Object),
          }),
        );
      });

      it('passing IEntrepot should create a new form with FormGroup', () => {
        const formGroup = service.createEntrepotFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            libelle: expect.any(Object),
            slug: expect.any(Object),
            capacite: expect.any(Object),
            description: expect.any(Object),
            deleteAt: expect.any(Object),
            createdBy: expect.any(Object),
            createdDate: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            lastModifiedDate: expect.any(Object),
            structure: expect.any(Object),
          }),
        );
      });
    });

    describe('getEntrepot', () => {
      it('should return NewEntrepot for default Entrepot initial value', () => {
        const formGroup = service.createEntrepotFormGroup(sampleWithNewData);

        const entrepot = service.getEntrepot(formGroup) as any;

        expect(entrepot).toMatchObject(sampleWithNewData);
      });

      it('should return NewEntrepot for empty Entrepot initial value', () => {
        const formGroup = service.createEntrepotFormGroup();

        const entrepot = service.getEntrepot(formGroup) as any;

        expect(entrepot).toMatchObject({});
      });

      it('should return IEntrepot', () => {
        const formGroup = service.createEntrepotFormGroup(sampleWithRequiredData);

        const entrepot = service.getEntrepot(formGroup) as any;

        expect(entrepot).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEntrepot should not enable id FormControl', () => {
        const formGroup = service.createEntrepotFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEntrepot should disable id FormControl', () => {
        const formGroup = service.createEntrepotFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
