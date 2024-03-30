import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IVente } from '../vente.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../vente.test-samples';

import { VenteService, RestVente } from './vente.service';

const requireRestSample: RestVente = {
  ...sampleWithRequiredData,
  venteDate: sampleWithRequiredData.venteDate?.toJSON(),
  deleteAt: sampleWithRequiredData.deleteAt?.toJSON(),
  createdDate: sampleWithRequiredData.createdDate?.toJSON(),
  lastModifiedDate: sampleWithRequiredData.lastModifiedDate?.toJSON(),
};

describe('Vente Service', () => {
  let service: VenteService;
  let httpMock: HttpTestingController;
  let expectedResult: IVente | IVente[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(VenteService);
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

    it('should create a Vente', () => {
      const vente = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(vente).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Vente', () => {
      const vente = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(vente).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Vente', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Vente', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Vente', () => {
      const expected = true;

      service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addVenteToCollectionIfMissing', () => {
      it('should add a Vente to an empty array', () => {
        const vente: IVente = sampleWithRequiredData;
        expectedResult = service.addVenteToCollectionIfMissing([], vente);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(vente);
      });

      it('should not add a Vente to an array that contains it', () => {
        const vente: IVente = sampleWithRequiredData;
        const venteCollection: IVente[] = [
          {
            ...vente,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addVenteToCollectionIfMissing(venteCollection, vente);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Vente to an array that doesn't contain it", () => {
        const vente: IVente = sampleWithRequiredData;
        const venteCollection: IVente[] = [sampleWithPartialData];
        expectedResult = service.addVenteToCollectionIfMissing(venteCollection, vente);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(vente);
      });

      it('should add only unique Vente to an array', () => {
        const venteArray: IVente[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const venteCollection: IVente[] = [sampleWithRequiredData];
        expectedResult = service.addVenteToCollectionIfMissing(venteCollection, ...venteArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const vente: IVente = sampleWithRequiredData;
        const vente2: IVente = sampleWithPartialData;
        expectedResult = service.addVenteToCollectionIfMissing([], vente, vente2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(vente);
        expect(expectedResult).toContain(vente2);
      });

      it('should accept null and undefined values', () => {
        const vente: IVente = sampleWithRequiredData;
        expectedResult = service.addVenteToCollectionIfMissing([], null, vente, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(vente);
      });

      it('should return initial array if no Vente is added', () => {
        const venteCollection: IVente[] = [sampleWithRequiredData];
        expectedResult = service.addVenteToCollectionIfMissing(venteCollection, undefined, null);
        expect(expectedResult).toEqual(venteCollection);
      });
    });

    describe('compareVente', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareVente(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = null;

        const compareResult1 = service.compareVente(entity1, entity2);
        const compareResult2 = service.compareVente(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };

        const compareResult1 = service.compareVente(entity1, entity2);
        const compareResult2 = service.compareVente(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

        const compareResult1 = service.compareVente(entity1, entity2);
        const compareResult2 = service.compareVente(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
