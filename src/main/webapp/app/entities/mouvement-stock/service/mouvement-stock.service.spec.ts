import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMouvementStock } from '../mouvement-stock.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../mouvement-stock.test-samples';

import { MouvementStockService, RestMouvementStock } from './mouvement-stock.service';

const requireRestSample: RestMouvementStock = {
  ...sampleWithRequiredData,
  transactionDate: sampleWithRequiredData.transactionDate?.toJSON(),
  deleteAt: sampleWithRequiredData.deleteAt?.toJSON(),
  createdDate: sampleWithRequiredData.createdDate?.toJSON(),
  lastModifiedDate: sampleWithRequiredData.lastModifiedDate?.toJSON(),
};

describe('MouvementStock Service', () => {
  let service: MouvementStockService;
  let httpMock: HttpTestingController;
  let expectedResult: IMouvementStock | IMouvementStock[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MouvementStockService);
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

    it('should create a MouvementStock', () => {
      const mouvementStock = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(mouvementStock).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MouvementStock', () => {
      const mouvementStock = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(mouvementStock).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MouvementStock', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MouvementStock', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a MouvementStock', () => {
      const expected = true;

      service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMouvementStockToCollectionIfMissing', () => {
      it('should add a MouvementStock to an empty array', () => {
        const mouvementStock: IMouvementStock = sampleWithRequiredData;
        expectedResult = service.addMouvementStockToCollectionIfMissing([], mouvementStock);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mouvementStock);
      });

      it('should not add a MouvementStock to an array that contains it', () => {
        const mouvementStock: IMouvementStock = sampleWithRequiredData;
        const mouvementStockCollection: IMouvementStock[] = [
          {
            ...mouvementStock,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMouvementStockToCollectionIfMissing(mouvementStockCollection, mouvementStock);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MouvementStock to an array that doesn't contain it", () => {
        const mouvementStock: IMouvementStock = sampleWithRequiredData;
        const mouvementStockCollection: IMouvementStock[] = [sampleWithPartialData];
        expectedResult = service.addMouvementStockToCollectionIfMissing(mouvementStockCollection, mouvementStock);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mouvementStock);
      });

      it('should add only unique MouvementStock to an array', () => {
        const mouvementStockArray: IMouvementStock[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const mouvementStockCollection: IMouvementStock[] = [sampleWithRequiredData];
        expectedResult = service.addMouvementStockToCollectionIfMissing(mouvementStockCollection, ...mouvementStockArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const mouvementStock: IMouvementStock = sampleWithRequiredData;
        const mouvementStock2: IMouvementStock = sampleWithPartialData;
        expectedResult = service.addMouvementStockToCollectionIfMissing([], mouvementStock, mouvementStock2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mouvementStock);
        expect(expectedResult).toContain(mouvementStock2);
      });

      it('should accept null and undefined values', () => {
        const mouvementStock: IMouvementStock = sampleWithRequiredData;
        expectedResult = service.addMouvementStockToCollectionIfMissing([], null, mouvementStock, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mouvementStock);
      });

      it('should return initial array if no MouvementStock is added', () => {
        const mouvementStockCollection: IMouvementStock[] = [sampleWithRequiredData];
        expectedResult = service.addMouvementStockToCollectionIfMissing(mouvementStockCollection, undefined, null);
        expect(expectedResult).toEqual(mouvementStockCollection);
      });
    });

    describe('compareMouvementStock', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMouvementStock(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = null;

        const compareResult1 = service.compareMouvementStock(entity1, entity2);
        const compareResult2 = service.compareMouvementStock(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };

        const compareResult1 = service.compareMouvementStock(entity1, entity2);
        const compareResult2 = service.compareMouvementStock(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

        const compareResult1 = service.compareMouvementStock(entity1, entity2);
        const compareResult2 = service.compareMouvementStock(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
