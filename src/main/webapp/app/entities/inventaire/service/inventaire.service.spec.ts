import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IInventaire } from '../inventaire.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../inventaire.test-samples';

import { InventaireService, RestInventaire } from './inventaire.service';

const requireRestSample: RestInventaire = {
  ...sampleWithRequiredData,
  inventaireDate: sampleWithRequiredData.inventaireDate?.toJSON(),
  deleteAt: sampleWithRequiredData.deleteAt?.toJSON(),
  createdDate: sampleWithRequiredData.createdDate?.toJSON(),
  lastModifiedDate: sampleWithRequiredData.lastModifiedDate?.toJSON(),
};

describe('Inventaire Service', () => {
  let service: InventaireService;
  let httpMock: HttpTestingController;
  let expectedResult: IInventaire | IInventaire[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(InventaireService);
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

    it('should create a Inventaire', () => {
      const inventaire = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(inventaire).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Inventaire', () => {
      const inventaire = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(inventaire).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Inventaire', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Inventaire', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Inventaire', () => {
      const expected = true;

      service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addInventaireToCollectionIfMissing', () => {
      it('should add a Inventaire to an empty array', () => {
        const inventaire: IInventaire = sampleWithRequiredData;
        expectedResult = service.addInventaireToCollectionIfMissing([], inventaire);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(inventaire);
      });

      it('should not add a Inventaire to an array that contains it', () => {
        const inventaire: IInventaire = sampleWithRequiredData;
        const inventaireCollection: IInventaire[] = [
          {
            ...inventaire,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addInventaireToCollectionIfMissing(inventaireCollection, inventaire);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Inventaire to an array that doesn't contain it", () => {
        const inventaire: IInventaire = sampleWithRequiredData;
        const inventaireCollection: IInventaire[] = [sampleWithPartialData];
        expectedResult = service.addInventaireToCollectionIfMissing(inventaireCollection, inventaire);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(inventaire);
      });

      it('should add only unique Inventaire to an array', () => {
        const inventaireArray: IInventaire[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const inventaireCollection: IInventaire[] = [sampleWithRequiredData];
        expectedResult = service.addInventaireToCollectionIfMissing(inventaireCollection, ...inventaireArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const inventaire: IInventaire = sampleWithRequiredData;
        const inventaire2: IInventaire = sampleWithPartialData;
        expectedResult = service.addInventaireToCollectionIfMissing([], inventaire, inventaire2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(inventaire);
        expect(expectedResult).toContain(inventaire2);
      });

      it('should accept null and undefined values', () => {
        const inventaire: IInventaire = sampleWithRequiredData;
        expectedResult = service.addInventaireToCollectionIfMissing([], null, inventaire, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(inventaire);
      });

      it('should return initial array if no Inventaire is added', () => {
        const inventaireCollection: IInventaire[] = [sampleWithRequiredData];
        expectedResult = service.addInventaireToCollectionIfMissing(inventaireCollection, undefined, null);
        expect(expectedResult).toEqual(inventaireCollection);
      });
    });

    describe('compareInventaire', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareInventaire(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = null;

        const compareResult1 = service.compareInventaire(entity1, entity2);
        const compareResult2 = service.compareInventaire(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };

        const compareResult1 = service.compareInventaire(entity1, entity2);
        const compareResult2 = service.compareInventaire(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

        const compareResult1 = service.compareInventaire(entity1, entity2);
        const compareResult2 = service.compareInventaire(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
