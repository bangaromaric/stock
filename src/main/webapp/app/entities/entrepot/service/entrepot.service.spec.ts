import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEntrepot } from '../entrepot.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../entrepot.test-samples';

import { EntrepotService, RestEntrepot } from './entrepot.service';

const requireRestSample: RestEntrepot = {
  ...sampleWithRequiredData,
  deleteAt: sampleWithRequiredData.deleteAt?.toJSON(),
  createdDate: sampleWithRequiredData.createdDate?.toJSON(),
  lastModifiedDate: sampleWithRequiredData.lastModifiedDate?.toJSON(),
};

describe('Entrepot Service', () => {
  let service: EntrepotService;
  let httpMock: HttpTestingController;
  let expectedResult: IEntrepot | IEntrepot[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EntrepotService);
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

    it('should create a Entrepot', () => {
      const entrepot = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(entrepot).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Entrepot', () => {
      const entrepot = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(entrepot).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Entrepot', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Entrepot', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Entrepot', () => {
      const expected = true;

      service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEntrepotToCollectionIfMissing', () => {
      it('should add a Entrepot to an empty array', () => {
        const entrepot: IEntrepot = sampleWithRequiredData;
        expectedResult = service.addEntrepotToCollectionIfMissing([], entrepot);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(entrepot);
      });

      it('should not add a Entrepot to an array that contains it', () => {
        const entrepot: IEntrepot = sampleWithRequiredData;
        const entrepotCollection: IEntrepot[] = [
          {
            ...entrepot,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEntrepotToCollectionIfMissing(entrepotCollection, entrepot);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Entrepot to an array that doesn't contain it", () => {
        const entrepot: IEntrepot = sampleWithRequiredData;
        const entrepotCollection: IEntrepot[] = [sampleWithPartialData];
        expectedResult = service.addEntrepotToCollectionIfMissing(entrepotCollection, entrepot);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(entrepot);
      });

      it('should add only unique Entrepot to an array', () => {
        const entrepotArray: IEntrepot[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const entrepotCollection: IEntrepot[] = [sampleWithRequiredData];
        expectedResult = service.addEntrepotToCollectionIfMissing(entrepotCollection, ...entrepotArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const entrepot: IEntrepot = sampleWithRequiredData;
        const entrepot2: IEntrepot = sampleWithPartialData;
        expectedResult = service.addEntrepotToCollectionIfMissing([], entrepot, entrepot2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(entrepot);
        expect(expectedResult).toContain(entrepot2);
      });

      it('should accept null and undefined values', () => {
        const entrepot: IEntrepot = sampleWithRequiredData;
        expectedResult = service.addEntrepotToCollectionIfMissing([], null, entrepot, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(entrepot);
      });

      it('should return initial array if no Entrepot is added', () => {
        const entrepotCollection: IEntrepot[] = [sampleWithRequiredData];
        expectedResult = service.addEntrepotToCollectionIfMissing(entrepotCollection, undefined, null);
        expect(expectedResult).toEqual(entrepotCollection);
      });
    });

    describe('compareEntrepot', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEntrepot(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = null;

        const compareResult1 = service.compareEntrepot(entity1, entity2);
        const compareResult2 = service.compareEntrepot(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };

        const compareResult1 = service.compareEntrepot(entity1, entity2);
        const compareResult2 = service.compareEntrepot(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

        const compareResult1 = service.compareEntrepot(entity1, entity2);
        const compareResult2 = service.compareEntrepot(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
