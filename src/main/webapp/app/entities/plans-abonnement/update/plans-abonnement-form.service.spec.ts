import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../plans-abonnement.test-samples';

import { PlansAbonnementFormService } from './plans-abonnement-form.service';

describe('PlansAbonnement Form Service', () => {
  let service: PlansAbonnementFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PlansAbonnementFormService);
  });

  describe('Service methods', () => {
    describe('createPlansAbonnementFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPlansAbonnementFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            libelle: expect.any(Object),
            description: expect.any(Object),
            prix: expect.any(Object),
            duree: expect.any(Object),
            avantage: expect.any(Object),
            deleteAt: expect.any(Object),
            createdBy: expect.any(Object),
            createdDate: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            lastModifiedDate: expect.any(Object),
          }),
        );
      });

      it('passing IPlansAbonnement should create a new form with FormGroup', () => {
        const formGroup = service.createPlansAbonnementFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            libelle: expect.any(Object),
            description: expect.any(Object),
            prix: expect.any(Object),
            duree: expect.any(Object),
            avantage: expect.any(Object),
            deleteAt: expect.any(Object),
            createdBy: expect.any(Object),
            createdDate: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            lastModifiedDate: expect.any(Object),
          }),
        );
      });
    });

    describe('getPlansAbonnement', () => {
      it('should return NewPlansAbonnement for default PlansAbonnement initial value', () => {
        const formGroup = service.createPlansAbonnementFormGroup(sampleWithNewData);

        const plansAbonnement = service.getPlansAbonnement(formGroup) as any;

        expect(plansAbonnement).toMatchObject(sampleWithNewData);
      });

      it('should return NewPlansAbonnement for empty PlansAbonnement initial value', () => {
        const formGroup = service.createPlansAbonnementFormGroup();

        const plansAbonnement = service.getPlansAbonnement(formGroup) as any;

        expect(plansAbonnement).toMatchObject({});
      });

      it('should return IPlansAbonnement', () => {
        const formGroup = service.createPlansAbonnementFormGroup(sampleWithRequiredData);

        const plansAbonnement = service.getPlansAbonnement(formGroup) as any;

        expect(plansAbonnement).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPlansAbonnement should not enable id FormControl', () => {
        const formGroup = service.createPlansAbonnementFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPlansAbonnement should disable id FormControl', () => {
        const formGroup = service.createPlansAbonnementFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
