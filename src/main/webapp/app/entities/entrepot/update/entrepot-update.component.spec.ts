import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IStructure } from 'app/entities/structure/structure.model';
import { StructureService } from 'app/entities/structure/service/structure.service';
import { EntrepotService } from '../service/entrepot.service';
import { IEntrepot } from '../entrepot.model';
import { EntrepotFormService } from './entrepot-form.service';

import { EntrepotUpdateComponent } from './entrepot-update.component';

describe('Entrepot Management Update Component', () => {
  let comp: EntrepotUpdateComponent;
  let fixture: ComponentFixture<EntrepotUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let entrepotFormService: EntrepotFormService;
  let entrepotService: EntrepotService;
  let structureService: StructureService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), EntrepotUpdateComponent],
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
      .overrideTemplate(EntrepotUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EntrepotUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    entrepotFormService = TestBed.inject(EntrepotFormService);
    entrepotService = TestBed.inject(EntrepotService);
    structureService = TestBed.inject(StructureService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Structure query and add missing value', () => {
      const entrepot: IEntrepot = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const structure: IStructure = { id: 'ad5f7a72-75be-42b0-ad27-f848947e870b' };
      entrepot.structure = structure;

      const structureCollection: IStructure[] = [{ id: '7146cd97-715a-47b0-93e2-738c6b18b234' }];
      jest.spyOn(structureService, 'query').mockReturnValue(of(new HttpResponse({ body: structureCollection })));
      const additionalStructures = [structure];
      const expectedCollection: IStructure[] = [...additionalStructures, ...structureCollection];
      jest.spyOn(structureService, 'addStructureToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ entrepot });
      comp.ngOnInit();

      expect(structureService.query).toHaveBeenCalled();
      expect(structureService.addStructureToCollectionIfMissing).toHaveBeenCalledWith(
        structureCollection,
        ...additionalStructures.map(expect.objectContaining),
      );
      expect(comp.structuresSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const entrepot: IEntrepot = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const structure: IStructure = { id: '77b3e7e1-b38f-4a68-ae69-d329024b8fad' };
      entrepot.structure = structure;

      activatedRoute.data = of({ entrepot });
      comp.ngOnInit();

      expect(comp.structuresSharedCollection).toContain(structure);
      expect(comp.entrepot).toEqual(entrepot);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEntrepot>>();
      const entrepot = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(entrepotFormService, 'getEntrepot').mockReturnValue(entrepot);
      jest.spyOn(entrepotService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ entrepot });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: entrepot }));
      saveSubject.complete();

      // THEN
      expect(entrepotFormService.getEntrepot).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(entrepotService.update).toHaveBeenCalledWith(expect.objectContaining(entrepot));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEntrepot>>();
      const entrepot = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(entrepotFormService, 'getEntrepot').mockReturnValue({ id: null });
      jest.spyOn(entrepotService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ entrepot: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: entrepot }));
      saveSubject.complete();

      // THEN
      expect(entrepotFormService.getEntrepot).toHaveBeenCalled();
      expect(entrepotService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEntrepot>>();
      const entrepot = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(entrepotService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ entrepot });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(entrepotService.update).toHaveBeenCalled();
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
  });
});
