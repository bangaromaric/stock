import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IStructure } from 'app/entities/structure/structure.model';
import { StructureService } from 'app/entities/structure/service/structure.service';
import { IPlansAbonnement } from 'app/entities/plans-abonnement/plans-abonnement.model';
import { PlansAbonnementService } from 'app/entities/plans-abonnement/service/plans-abonnement.service';
import { IPaiement } from 'app/entities/paiement/paiement.model';
import { PaiementService } from 'app/entities/paiement/service/paiement.service';
import { IAbonnement } from '../abonnement.model';
import { AbonnementService } from '../service/abonnement.service';
import { AbonnementFormService } from './abonnement-form.service';

import { AbonnementUpdateComponent } from './abonnement-update.component';

describe('Abonnement Management Update Component', () => {
  let comp: AbonnementUpdateComponent;
  let fixture: ComponentFixture<AbonnementUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let abonnementFormService: AbonnementFormService;
  let abonnementService: AbonnementService;
  let structureService: StructureService;
  let plansAbonnementService: PlansAbonnementService;
  let paiementService: PaiementService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), AbonnementUpdateComponent],
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
      .overrideTemplate(AbonnementUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AbonnementUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    abonnementFormService = TestBed.inject(AbonnementFormService);
    abonnementService = TestBed.inject(AbonnementService);
    structureService = TestBed.inject(StructureService);
    plansAbonnementService = TestBed.inject(PlansAbonnementService);
    paiementService = TestBed.inject(PaiementService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Structure query and add missing value', () => {
      const abonnement: IAbonnement = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const structure: IStructure = { id: '693562e8-f143-4b8e-83ea-aea10db8ba30' };
      abonnement.structure = structure;

      const structureCollection: IStructure[] = [{ id: '184857c3-3bd8-44dd-b09d-b9e1301a3709' }];
      jest.spyOn(structureService, 'query').mockReturnValue(of(new HttpResponse({ body: structureCollection })));
      const additionalStructures = [structure];
      const expectedCollection: IStructure[] = [...additionalStructures, ...structureCollection];
      jest.spyOn(structureService, 'addStructureToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ abonnement });
      comp.ngOnInit();

      expect(structureService.query).toHaveBeenCalled();
      expect(structureService.addStructureToCollectionIfMissing).toHaveBeenCalledWith(
        structureCollection,
        ...additionalStructures.map(expect.objectContaining),
      );
      expect(comp.structuresSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PlansAbonnement query and add missing value', () => {
      const abonnement: IAbonnement = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const plansAbonnement: IPlansAbonnement = { id: 'c7b12041-eab9-4b34-ae72-4956cd1bbd4e' };
      abonnement.plansAbonnement = plansAbonnement;

      const plansAbonnementCollection: IPlansAbonnement[] = [{ id: 'c96be549-35ad-4ef1-ab8b-52102bf15d18' }];
      jest.spyOn(plansAbonnementService, 'query').mockReturnValue(of(new HttpResponse({ body: plansAbonnementCollection })));
      const additionalPlansAbonnements = [plansAbonnement];
      const expectedCollection: IPlansAbonnement[] = [...additionalPlansAbonnements, ...plansAbonnementCollection];
      jest.spyOn(plansAbonnementService, 'addPlansAbonnementToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ abonnement });
      comp.ngOnInit();

      expect(plansAbonnementService.query).toHaveBeenCalled();
      expect(plansAbonnementService.addPlansAbonnementToCollectionIfMissing).toHaveBeenCalledWith(
        plansAbonnementCollection,
        ...additionalPlansAbonnements.map(expect.objectContaining),
      );
      expect(comp.plansAbonnementsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Paiement query and add missing value', () => {
      const abonnement: IAbonnement = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const paiement: IPaiement = { id: '4ec0453a-72fc-4a0e-9cc7-3229a68211b7' };
      abonnement.paiement = paiement;

      const paiementCollection: IPaiement[] = [{ id: 'ce34c135-0a5e-4836-89fb-9ad168813061' }];
      jest.spyOn(paiementService, 'query').mockReturnValue(of(new HttpResponse({ body: paiementCollection })));
      const additionalPaiements = [paiement];
      const expectedCollection: IPaiement[] = [...additionalPaiements, ...paiementCollection];
      jest.spyOn(paiementService, 'addPaiementToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ abonnement });
      comp.ngOnInit();

      expect(paiementService.query).toHaveBeenCalled();
      expect(paiementService.addPaiementToCollectionIfMissing).toHaveBeenCalledWith(
        paiementCollection,
        ...additionalPaiements.map(expect.objectContaining),
      );
      expect(comp.paiementsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const abonnement: IAbonnement = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const structure: IStructure = { id: '22470a04-a396-4f6b-842b-ff19efa3840a' };
      abonnement.structure = structure;
      const plansAbonnement: IPlansAbonnement = { id: '888138ed-1334-49e6-8383-19efbd4b4f74' };
      abonnement.plansAbonnement = plansAbonnement;
      const paiement: IPaiement = { id: 'a6d05ea6-3d23-4c59-b37d-fa0ff978caac' };
      abonnement.paiement = paiement;

      activatedRoute.data = of({ abonnement });
      comp.ngOnInit();

      expect(comp.structuresSharedCollection).toContain(structure);
      expect(comp.plansAbonnementsSharedCollection).toContain(plansAbonnement);
      expect(comp.paiementsSharedCollection).toContain(paiement);
      expect(comp.abonnement).toEqual(abonnement);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAbonnement>>();
      const abonnement = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(abonnementFormService, 'getAbonnement').mockReturnValue(abonnement);
      jest.spyOn(abonnementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ abonnement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: abonnement }));
      saveSubject.complete();

      // THEN
      expect(abonnementFormService.getAbonnement).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(abonnementService.update).toHaveBeenCalledWith(expect.objectContaining(abonnement));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAbonnement>>();
      const abonnement = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(abonnementFormService, 'getAbonnement').mockReturnValue({ id: null });
      jest.spyOn(abonnementService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ abonnement: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: abonnement }));
      saveSubject.complete();

      // THEN
      expect(abonnementFormService.getAbonnement).toHaveBeenCalled();
      expect(abonnementService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAbonnement>>();
      const abonnement = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(abonnementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ abonnement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(abonnementService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareStructure', () => {
      it('Should forward to structureService', () => {
        const entity = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
        jest.spyOn(structureService, 'compareStructure');
        comp.compareStructure(entity, entity2);
        expect(structureService.compareStructure).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePlansAbonnement', () => {
      it('Should forward to plansAbonnementService', () => {
        const entity = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
        jest.spyOn(plansAbonnementService, 'comparePlansAbonnement');
        comp.comparePlansAbonnement(entity, entity2);
        expect(plansAbonnementService.comparePlansAbonnement).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePaiement', () => {
      it('Should forward to paiementService', () => {
        const entity = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
        jest.spyOn(paiementService, 'comparePaiement');
        comp.comparePaiement(entity, entity2);
        expect(paiementService.comparePaiement).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
