import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IEntrepot } from 'app/entities/entrepot/entrepot.model';
import { EntrepotService } from 'app/entities/entrepot/service/entrepot.service';
import { IProduit } from 'app/entities/produit/produit.model';
import { ProduitService } from 'app/entities/produit/service/produit.service';
import { IStock } from '../stock.model';
import { StockService } from '../service/stock.service';
import { StockFormService } from './stock-form.service';

import { StockUpdateComponent } from './stock-update.component';

describe('Stock Management Update Component', () => {
  let comp: StockUpdateComponent;
  let fixture: ComponentFixture<StockUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let stockFormService: StockFormService;
  let stockService: StockService;
  let entrepotService: EntrepotService;
  let produitService: ProduitService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), StockUpdateComponent],
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
      .overrideTemplate(StockUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(StockUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    stockFormService = TestBed.inject(StockFormService);
    stockService = TestBed.inject(StockService);
    entrepotService = TestBed.inject(EntrepotService);
    produitService = TestBed.inject(ProduitService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Entrepot query and add missing value', () => {
      const stock: IStock = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const entrepot: IEntrepot = { id: '367ae7f4-c531-4a13-84ed-1dcb1c99605f' };
      stock.entrepot = entrepot;

      const entrepotCollection: IEntrepot[] = [{ id: 'd17888a3-0e93-4f60-951e-2ac4e258ff83' }];
      jest.spyOn(entrepotService, 'query').mockReturnValue(of(new HttpResponse({ body: entrepotCollection })));
      const additionalEntrepots = [entrepot];
      const expectedCollection: IEntrepot[] = [...additionalEntrepots, ...entrepotCollection];
      jest.spyOn(entrepotService, 'addEntrepotToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ stock });
      comp.ngOnInit();

      expect(entrepotService.query).toHaveBeenCalled();
      expect(entrepotService.addEntrepotToCollectionIfMissing).toHaveBeenCalledWith(
        entrepotCollection,
        ...additionalEntrepots.map(expect.objectContaining),
      );
      expect(comp.entrepotsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Produit query and add missing value', () => {
      const stock: IStock = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const produit: IProduit = { id: '35c3520a-51c2-4df4-aad7-deb6e695ef0e' };
      stock.produit = produit;

      const produitCollection: IProduit[] = [{ id: '32606a84-49b4-4333-9a4a-0f53a4459db9' }];
      jest.spyOn(produitService, 'query').mockReturnValue(of(new HttpResponse({ body: produitCollection })));
      const additionalProduits = [produit];
      const expectedCollection: IProduit[] = [...additionalProduits, ...produitCollection];
      jest.spyOn(produitService, 'addProduitToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ stock });
      comp.ngOnInit();

      expect(produitService.query).toHaveBeenCalled();
      expect(produitService.addProduitToCollectionIfMissing).toHaveBeenCalledWith(
        produitCollection,
        ...additionalProduits.map(expect.objectContaining),
      );
      expect(comp.produitsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const stock: IStock = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const entrepot: IEntrepot = { id: '9f00488a-73a8-408c-bd71-53e16fb573fa' };
      stock.entrepot = entrepot;
      const produit: IProduit = { id: '6d4a5550-6f7a-4f31-8652-a4524668d096' };
      stock.produit = produit;

      activatedRoute.data = of({ stock });
      comp.ngOnInit();

      expect(comp.entrepotsSharedCollection).toContain(entrepot);
      expect(comp.produitsSharedCollection).toContain(produit);
      expect(comp.stock).toEqual(stock);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStock>>();
      const stock = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(stockFormService, 'getStock').mockReturnValue(stock);
      jest.spyOn(stockService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ stock });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: stock }));
      saveSubject.complete();

      // THEN
      expect(stockFormService.getStock).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(stockService.update).toHaveBeenCalledWith(expect.objectContaining(stock));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStock>>();
      const stock = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(stockFormService, 'getStock').mockReturnValue({ id: null });
      jest.spyOn(stockService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ stock: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: stock }));
      saveSubject.complete();

      // THEN
      expect(stockFormService.getStock).toHaveBeenCalled();
      expect(stockService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStock>>();
      const stock = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(stockService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ stock });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(stockService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEntrepot', () => {
      it('Should forward to entrepotService', () => {
        const entity = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
        jest.spyOn(entrepotService, 'compareEntrepot');
        comp.compareEntrepot(entity, entity2);
        expect(entrepotService.compareEntrepot).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareProduit', () => {
      it('Should forward to produitService', () => {
        const entity = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
        jest.spyOn(produitService, 'compareProduit');
        comp.compareProduit(entity, entity2);
        expect(produitService.compareProduit).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
