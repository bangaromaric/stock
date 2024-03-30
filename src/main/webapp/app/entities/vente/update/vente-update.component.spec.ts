import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IProduit } from 'app/entities/produit/produit.model';
import { ProduitService } from 'app/entities/produit/service/produit.service';
import { IStructure } from 'app/entities/structure/structure.model';
import { StructureService } from 'app/entities/structure/service/structure.service';
import { IVente } from '../vente.model';
import { VenteService } from '../service/vente.service';
import { VenteFormService } from './vente-form.service';

import { VenteUpdateComponent } from './vente-update.component';

describe('Vente Management Update Component', () => {
  let comp: VenteUpdateComponent;
  let fixture: ComponentFixture<VenteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let venteFormService: VenteFormService;
  let venteService: VenteService;
  let produitService: ProduitService;
  let structureService: StructureService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), VenteUpdateComponent],
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
      .overrideTemplate(VenteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(VenteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    venteFormService = TestBed.inject(VenteFormService);
    venteService = TestBed.inject(VenteService);
    produitService = TestBed.inject(ProduitService);
    structureService = TestBed.inject(StructureService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Produit query and add missing value', () => {
      const vente: IVente = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const produit: IProduit = { id: '12ca5c7f-d17a-4e93-a640-6176e5f6f10d' };
      vente.produit = produit;

      const produitCollection: IProduit[] = [{ id: 'dc7188a0-dcb0-4044-8ea5-85b14c1d5f2a' }];
      jest.spyOn(produitService, 'query').mockReturnValue(of(new HttpResponse({ body: produitCollection })));
      const additionalProduits = [produit];
      const expectedCollection: IProduit[] = [...additionalProduits, ...produitCollection];
      jest.spyOn(produitService, 'addProduitToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ vente });
      comp.ngOnInit();

      expect(produitService.query).toHaveBeenCalled();
      expect(produitService.addProduitToCollectionIfMissing).toHaveBeenCalledWith(
        produitCollection,
        ...additionalProduits.map(expect.objectContaining),
      );
      expect(comp.produitsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Structure query and add missing value', () => {
      const vente: IVente = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const structure: IStructure = { id: 'c9fec6ad-ae1c-4cd8-bde4-c2dcef339edf' };
      vente.structure = structure;

      const structureCollection: IStructure[] = [{ id: 'f0317b13-2c95-46e5-9ecc-fa0a073f7fb8' }];
      jest.spyOn(structureService, 'query').mockReturnValue(of(new HttpResponse({ body: structureCollection })));
      const additionalStructures = [structure];
      const expectedCollection: IStructure[] = [...additionalStructures, ...structureCollection];
      jest.spyOn(structureService, 'addStructureToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ vente });
      comp.ngOnInit();

      expect(structureService.query).toHaveBeenCalled();
      expect(structureService.addStructureToCollectionIfMissing).toHaveBeenCalledWith(
        structureCollection,
        ...additionalStructures.map(expect.objectContaining),
      );
      expect(comp.structuresSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const vente: IVente = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const produit: IProduit = { id: '23cbf285-810e-4ffa-bfdd-571bde860721' };
      vente.produit = produit;
      const structure: IStructure = { id: '1ff7e953-2b1e-4900-b79d-22b9c0960d77' };
      vente.structure = structure;

      activatedRoute.data = of({ vente });
      comp.ngOnInit();

      expect(comp.produitsSharedCollection).toContain(produit);
      expect(comp.structuresSharedCollection).toContain(structure);
      expect(comp.vente).toEqual(vente);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVente>>();
      const vente = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(venteFormService, 'getVente').mockReturnValue(vente);
      jest.spyOn(venteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ vente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: vente }));
      saveSubject.complete();

      // THEN
      expect(venteFormService.getVente).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(venteService.update).toHaveBeenCalledWith(expect.objectContaining(vente));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVente>>();
      const vente = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(venteFormService, 'getVente').mockReturnValue({ id: null });
      jest.spyOn(venteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ vente: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: vente }));
      saveSubject.complete();

      // THEN
      expect(venteFormService.getVente).toHaveBeenCalled();
      expect(venteService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVente>>();
      const vente = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(venteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ vente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(venteService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareProduit', () => {
      it('Should forward to produitService', () => {
        const entity = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
        jest.spyOn(produitService, 'compareProduit');
        comp.compareProduit(entity, entity2);
        expect(produitService.compareProduit).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareStructure', () => {
      it('Should forward to structureService', () => {
        const entity = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
        jest.spyOn(structureService, 'compareStructure');
        comp.compareStructure(entity, entity2);
        expect(structureService.compareStructure).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
