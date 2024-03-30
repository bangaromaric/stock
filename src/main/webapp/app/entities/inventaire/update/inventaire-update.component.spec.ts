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
import { IInventaire } from '../inventaire.model';
import { InventaireService } from '../service/inventaire.service';
import { InventaireFormService } from './inventaire-form.service';

import { InventaireUpdateComponent } from './inventaire-update.component';

describe('Inventaire Management Update Component', () => {
  let comp: InventaireUpdateComponent;
  let fixture: ComponentFixture<InventaireUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let inventaireFormService: InventaireFormService;
  let inventaireService: InventaireService;
  let entrepotService: EntrepotService;
  let produitService: ProduitService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), InventaireUpdateComponent],
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
      .overrideTemplate(InventaireUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(InventaireUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    inventaireFormService = TestBed.inject(InventaireFormService);
    inventaireService = TestBed.inject(InventaireService);
    entrepotService = TestBed.inject(EntrepotService);
    produitService = TestBed.inject(ProduitService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Entrepot query and add missing value', () => {
      const inventaire: IInventaire = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const entrepot: IEntrepot = { id: '1169c893-7f32-4f40-826c-c7103bd33ccf' };
      inventaire.entrepot = entrepot;

      const entrepotCollection: IEntrepot[] = [{ id: '5be06504-a7c7-4a55-aab8-ac0c7a55ace1' }];
      jest.spyOn(entrepotService, 'query').mockReturnValue(of(new HttpResponse({ body: entrepotCollection })));
      const additionalEntrepots = [entrepot];
      const expectedCollection: IEntrepot[] = [...additionalEntrepots, ...entrepotCollection];
      jest.spyOn(entrepotService, 'addEntrepotToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ inventaire });
      comp.ngOnInit();

      expect(entrepotService.query).toHaveBeenCalled();
      expect(entrepotService.addEntrepotToCollectionIfMissing).toHaveBeenCalledWith(
        entrepotCollection,
        ...additionalEntrepots.map(expect.objectContaining),
      );
      expect(comp.entrepotsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Produit query and add missing value', () => {
      const inventaire: IInventaire = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const produit: IProduit = { id: '9e747711-8b87-4cf3-af7f-edfaa672fbe1' };
      inventaire.produit = produit;

      const produitCollection: IProduit[] = [{ id: '77382150-59c0-4c02-bbed-47ebb09c89ff' }];
      jest.spyOn(produitService, 'query').mockReturnValue(of(new HttpResponse({ body: produitCollection })));
      const additionalProduits = [produit];
      const expectedCollection: IProduit[] = [...additionalProduits, ...produitCollection];
      jest.spyOn(produitService, 'addProduitToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ inventaire });
      comp.ngOnInit();

      expect(produitService.query).toHaveBeenCalled();
      expect(produitService.addProduitToCollectionIfMissing).toHaveBeenCalledWith(
        produitCollection,
        ...additionalProduits.map(expect.objectContaining),
      );
      expect(comp.produitsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const inventaire: IInventaire = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const entrepot: IEntrepot = { id: 'a4886f92-db41-4d5a-9787-72fc616067b0' };
      inventaire.entrepot = entrepot;
      const produit: IProduit = { id: '773cfbfb-1bec-4af9-a71f-2c521278967b' };
      inventaire.produit = produit;

      activatedRoute.data = of({ inventaire });
      comp.ngOnInit();

      expect(comp.entrepotsSharedCollection).toContain(entrepot);
      expect(comp.produitsSharedCollection).toContain(produit);
      expect(comp.inventaire).toEqual(inventaire);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInventaire>>();
      const inventaire = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(inventaireFormService, 'getInventaire').mockReturnValue(inventaire);
      jest.spyOn(inventaireService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ inventaire });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: inventaire }));
      saveSubject.complete();

      // THEN
      expect(inventaireFormService.getInventaire).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(inventaireService.update).toHaveBeenCalledWith(expect.objectContaining(inventaire));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInventaire>>();
      const inventaire = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(inventaireFormService, 'getInventaire').mockReturnValue({ id: null });
      jest.spyOn(inventaireService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ inventaire: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: inventaire }));
      saveSubject.complete();

      // THEN
      expect(inventaireFormService.getInventaire).toHaveBeenCalled();
      expect(inventaireService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInventaire>>();
      const inventaire = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(inventaireService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ inventaire });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(inventaireService.update).toHaveBeenCalled();
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
