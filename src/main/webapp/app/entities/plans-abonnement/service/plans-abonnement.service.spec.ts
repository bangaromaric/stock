import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPlansAbonnement } from '../plans-abonnement.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../plans-abonnement.test-samples';

import { PlansAbonnementService, RestPlansAbonnement } from './plans-abonnement.service';

const requireRestSample: RestPlansAbonnement = {
  ...sampleWithRequiredData,
  deleteAt: sampleWithRequiredData.deleteAt?.toJSON(),
  createdDate: sampleWithRequiredData.createdDate?.toJSON(),
  lastModifiedDate: sampleWithRequiredData.lastModifiedDate?.toJSON(),
};

describe('PlansAbonnement Service', () => {
  let service: PlansAbonnementService;
  let httpMock: HttpTestingController;
  let expectedResult: IPlansAbonnement | IPlansAbonnement[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PlansAbonnementService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a PlansAbonnement', () => {
      const plansAbonnement = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(plansAbonnement).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PlansAbonnement', () => {
      const plansAbonnement = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(plansAbonnement).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PlansAbonnement', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PlansAbonnement', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PlansAbonnement', () => {
      const expected = true;

      service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPlansAbonnementToCollectionIfMissing', () => {
      it('should add a PlansAbonnement to an empty array', () => {
        const plansAbonnement: IPlansAbonnement = sampleWithRequiredData;
        expectedResult = service.addPlansAbonnementToCollectionIfMissing([], plansAbonnement);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(plansAbonnement);
      });

      it('should not add a PlansAbonnement to an array that contains it', () => {
        const plansAbonnement: IPlansAbonnement = sampleWithRequiredData;
        const plansAbonnementCollection: IPlansAbonnement[] = [
          {
            ...plansAbonnement,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPlansAbonnementToCollectionIfMissing(plansAbonnementCollection, plansAbonnement);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PlansAbonnement to an array that doesn't contain it", () => {
        const plansAbonnement: IPlansAbonnement = sampleWithRequiredData;
        const plansAbonnementCollection: IPlansAbonnement[] = [sampleWithPartialData];
        expectedResult = service.addPlansAbonnementToCollectionIfMissing(plansAbonnementCollection, plansAbonnement);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(plansAbonnement);
      });

      it('should add only unique PlansAbonnement to an array', () => {
        const plansAbonnementArray: IPlansAbonnement[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const plansAbonnementCollection: IPlansAbonnement[] = [sampleWithRequiredData];
        expectedResult = service.addPlansAbonnementToCollectionIfMissing(plansAbonnementCollection, ...plansAbonnementArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const plansAbonnement: IPlansAbonnement = sampleWithRequiredData;
        const plansAbonnement2: IPlansAbonnement = sampleWithPartialData;
        expectedResult = service.addPlansAbonnementToCollectionIfMissing([], plansAbonnement, plansAbonnement2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(plansAbonnement);
        expect(expectedResult).toContain(plansAbonnement2);
      });

      it('should accept null and undefined values', () => {
        const plansAbonnement: IPlansAbonnement = sampleWithRequiredData;
        expectedResult = service.addPlansAbonnementToCollectionIfMissing([], null, plansAbonnement, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(plansAbonnement);
      });

      it('should return initial array if no PlansAbonnement is added', () => {
        const plansAbonnementCollection: IPlansAbonnement[] = [sampleWithRequiredData];
        expectedResult = service.addPlansAbonnementToCollectionIfMissing(plansAbonnementCollection, undefined, null);
        expect(expectedResult).toEqual(plansAbonnementCollection);
      });
    });

    describe('comparePlansAbonnement', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePlansAbonnement(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = null;

        const compareResult1 = service.comparePlansAbonnement(entity1, entity2);
        const compareResult2 = service.comparePlansAbonnement(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };

        const compareResult1 = service.comparePlansAbonnement(entity1, entity2);
        const compareResult2 = service.comparePlansAbonnement(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

        const compareResult1 = service.comparePlansAbonnement(entity1, entity2);
        const compareResult2 = service.comparePlansAbonnement(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
