import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IPlansAbonnement } from 'app/entities/plans-abonnement/plans-abonnement.model';
import { PlansAbonnementService } from 'app/entities/plans-abonnement/service/plans-abonnement.service';
import { PaiementService } from '../service/paiement.service';
import { IPaiement } from '../paiement.model';
import { PaiementFormService } from './paiement-form.service';

import { PaiementUpdateComponent } from './paiement-update.component';

describe('Paiement Management Update Component', () => {
  let comp: PaiementUpdateComponent;
  let fixture: ComponentFixture<PaiementUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let paiementFormService: PaiementFormService;
  let paiementService: PaiementService;
  let plansAbonnementService: PlansAbonnementService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), PaiementUpdateComponent],
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
      .overrideTemplate(PaiementUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PaiementUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    paiementFormService = TestBed.inject(PaiementFormService);
    paiementService = TestBed.inject(PaiementService);
    plansAbonnementService = TestBed.inject(PlansAbonnementService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call PlansAbonnement query and add missing value', () => {
      const paiement: IPaiement = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const plansAbonnement: IPlansAbonnement = { id: 'b4954eb2-dfc7-485b-b9ef-da904210e939' };
      paiement.plansAbonnement = plansAbonnement;

      const plansAbonnementCollection: IPlansAbonnement[] = [{ id: '9abab55f-8d75-4d6e-87fb-e7c7edd859d2' }];
      jest.spyOn(plansAbonnementService, 'query').mockReturnValue(of(new HttpResponse({ body: plansAbonnementCollection })));
      const additionalPlansAbonnements = [plansAbonnement];
      const expectedCollection: IPlansAbonnement[] = [...additionalPlansAbonnements, ...plansAbonnementCollection];
      jest.spyOn(plansAbonnementService, 'addPlansAbonnementToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ paiement });
      comp.ngOnInit();

      expect(plansAbonnementService.query).toHaveBeenCalled();
      expect(plansAbonnementService.addPlansAbonnementToCollectionIfMissing).toHaveBeenCalledWith(
        plansAbonnementCollection,
        ...additionalPlansAbonnements.map(expect.objectContaining),
      );
      expect(comp.plansAbonnementsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const paiement: IPaiement = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const plansAbonnement: IPlansAbonnement = { id: '87d4d427-c5f5-431f-aa3b-605e8d83625e' };
      paiement.plansAbonnement = plansAbonnement;

      activatedRoute.data = of({ paiement });
      comp.ngOnInit();

      expect(comp.plansAbonnementsSharedCollection).toContain(plansAbonnement);
      expect(comp.paiement).toEqual(paiement);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaiement>>();
      const paiement = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(paiementFormService, 'getPaiement').mockReturnValue(paiement);
      jest.spyOn(paiementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paiement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paiement }));
      saveSubject.complete();

      // THEN
      expect(paiementFormService.getPaiement).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(paiementService.update).toHaveBeenCalledWith(expect.objectContaining(paiement));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaiement>>();
      const paiement = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(paiementFormService, 'getPaiement').mockReturnValue({ id: null });
      jest.spyOn(paiementService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paiement: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paiement }));
      saveSubject.complete();

      // THEN
      expect(paiementFormService.getPaiement).toHaveBeenCalled();
      expect(paiementService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaiement>>();
      const paiement = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(paiementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paiement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(paiementService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePlansAbonnement', () => {
      it('Should forward to plansAbonnementService', () => {
        const entity = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
        jest.spyOn(plansAbonnementService, 'comparePlansAbonnement');
        comp.comparePlansAbonnement(entity, entity2);
        expect(plansAbonnementService.comparePlansAbonnement).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
