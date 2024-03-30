import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IProduit } from 'app/entities/produit/produit.model';
import { ProduitService } from 'app/entities/produit/service/produit.service';
import { IEntrepot } from 'app/entities/entrepot/entrepot.model';
import { EntrepotService } from 'app/entities/entrepot/service/entrepot.service';
import { IMouvementStock } from '../mouvement-stock.model';
import { MouvementStockService } from '../service/mouvement-stock.service';
import { MouvementStockFormService } from './mouvement-stock-form.service';

import { MouvementStockUpdateComponent } from './mouvement-stock-update.component';

describe('MouvementStock Management Update Component', () => {
  let comp: MouvementStockUpdateComponent;
  let fixture: ComponentFixture<MouvementStockUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let mouvementStockFormService: MouvementStockFormService;
  let mouvementStockService: MouvementStockService;
  let produitService: ProduitService;
  let entrepotService: EntrepotService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), MouvementStockUpdateComponent],
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
      .overrideTemplate(MouvementStockUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MouvementStockUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    mouvementStockFormService = TestBed.inject(MouvementStockFormService);
    mouvementStockService = TestBed.inject(MouvementStockService);
    produitService = TestBed.inject(ProduitService);
    entrepotService = TestBed.inject(EntrepotService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Produit query and add missing value', () => {
      const mouvementStock: IMouvementStock = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const produit: IProduit = { id: 'c82e61de-56c4-4ec0-972b-2b50a49ae5de' };
      mouvementStock.produit = produit;

      const produitCollection: IProduit[] = [{ id: 'dda2a5ab-c320-487a-956e-512fae857b2b' }];
      jest.spyOn(produitService, 'query').mockReturnValue(of(new HttpResponse({ body: produitCollection })));
      const additionalProduits = [produit];
      const expectedCollection: IProduit[] = [...additionalProduits, ...produitCollection];
      jest.spyOn(produitService, 'addProduitToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ mouvementStock });
      comp.ngOnInit();

      expect(produitService.query).toHaveBeenCalled();
      expect(produitService.addProduitToCollectionIfMissing).toHaveBeenCalledWith(
        produitCollection,
        ...additionalProduits.map(expect.objectContaining),
      );
      expect(comp.produitsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Entrepot query and add missing value', () => {
      const mouvementStock: IMouvementStock = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const entrepot: IEntrepot = { id: '522e576d-c34e-4c13-b55d-15b0ced00f31' };
      mouvementStock.entrepot = entrepot;

      const entrepotCollection: IEntrepot[] = [{ id: '187dcba1-6534-4799-b9c9-e19d62a11738' }];
      jest.spyOn(entrepotService, 'query').mockReturnValue(of(new HttpResponse({ body: entrepotCollection })));
      const additionalEntrepots = [entrepot];
      const expectedCollection: IEntrepot[] = [...additionalEntrepots, ...entrepotCollection];
      jest.spyOn(entrepotService, 'addEntrepotToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ mouvementStock });
      comp.ngOnInit();

      expect(entrepotService.query).toHaveBeenCalled();
      expect(entrepotService.addEntrepotToCollectionIfMissing).toHaveBeenCalledWith(
        entrepotCollection,
        ...additionalEntrepots.map(expect.objectContaining),
      );
      expect(comp.entrepotsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const mouvementStock: IMouvementStock = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const produit: IProduit = { id: 'd90859fb-0322-40ad-a989-bd1314e2528f' };
      mouvementStock.produit = produit;
      const entrepot: IEntrepot = { id: '45ea10d0-fc52-4d9f-a320-70f1df947629' };
      mouvementStock.entrepot = entrepot;

      activatedRoute.data = of({ mouvementStock });
      comp.ngOnInit();

      expect(comp.produitsSharedCollection).toContain(produit);
      expect(comp.entrepotsSharedCollection).toContain(entrepot);
      expect(comp.mouvementStock).toEqual(mouvementStock);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMouvementStock>>();
      const mouvementStock = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(mouvementStockFormService, 'getMouvementStock').mockReturnValue(mouvementStock);
      jest.spyOn(mouvementStockService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mouvementStock });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mouvementStock }));
      saveSubject.complete();

      // THEN
      expect(mouvementStockFormService.getMouvementStock).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(mouvementStockService.update).toHaveBeenCalledWith(expect.objectContaining(mouvementStock));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMouvementStock>>();
      const mouvementStock = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(mouvementStockFormService, 'getMouvementStock').mockReturnValue({ id: null });
      jest.spyOn(mouvementStockService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mouvementStock: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mouvementStock }));
      saveSubject.complete();

      // THEN
      expect(mouvementStockFormService.getMouvementStock).toHaveBeenCalled();
      expect(mouvementStockService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMouvementStock>>();
      const mouvementStock = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(mouvementStockService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mouvementStock });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(mouvementStockService.update).toHaveBeenCalled();
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

    describe('compareEntrepot', () => {
      it('Should forward to entrepotService', () => {
        const entity = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
        jest.spyOn(entrepotService, 'compareEntrepot');
        comp.compareEntrepot(entity, entity2);
        expect(entrepotService.compareEntrepot).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
