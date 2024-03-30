import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PlansAbonnementService } from '../service/plans-abonnement.service';
import { IPlansAbonnement } from '../plans-abonnement.model';
import { PlansAbonnementFormService } from './plans-abonnement-form.service';

import { PlansAbonnementUpdateComponent } from './plans-abonnement-update.component';

describe('PlansAbonnement Management Update Component', () => {
  let comp: PlansAbonnementUpdateComponent;
  let fixture: ComponentFixture<PlansAbonnementUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let plansAbonnementFormService: PlansAbonnementFormService;
  let plansAbonnementService: PlansAbonnementService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), PlansAbonnementUpdateComponent],
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
      .overrideTemplate(PlansAbonnementUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PlansAbonnementUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    plansAbonnementFormService = TestBed.inject(PlansAbonnementFormService);
    plansAbonnementService = TestBed.inject(PlansAbonnementService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const plansAbonnement: IPlansAbonnement = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };

      activatedRoute.data = of({ plansAbonnement });
      comp.ngOnInit();

      expect(comp.plansAbonnement).toEqual(plansAbonnement);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlansAbonnement>>();
      const plansAbonnement = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(plansAbonnementFormService, 'getPlansAbonnement').mockReturnValue(plansAbonnement);
      jest.spyOn(plansAbonnementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ plansAbonnement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: plansAbonnement }));
      saveSubject.complete();

      // THEN
      expect(plansAbonnementFormService.getPlansAbonnement).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(plansAbonnementService.update).toHaveBeenCalledWith(expect.objectContaining(plansAbonnement));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlansAbonnement>>();
      const plansAbonnement = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(plansAbonnementFormService, 'getPlansAbonnement').mockReturnValue({ id: null });
      jest.spyOn(plansAbonnementService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ plansAbonnement: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: plansAbonnement }));
      saveSubject.complete();

      // THEN
      expect(plansAbonnementFormService.getPlansAbonnement).toHaveBeenCalled();
      expect(plansAbonnementService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlansAbonnement>>();
      const plansAbonnement = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(plansAbonnementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ plansAbonnement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(plansAbonnementService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
