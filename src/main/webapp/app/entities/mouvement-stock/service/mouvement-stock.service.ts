import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMouvementStock, NewMouvementStock } from '../mouvement-stock.model';

export type PartialUpdateMouvementStock = Partial<IMouvementStock> & Pick<IMouvementStock, 'id'>;

type RestOf<T extends IMouvementStock | NewMouvementStock> = Omit<
  T,
  'transactionDate' | 'deleteAt' | 'createdDate' | 'lastModifiedDate'
> & {
  transactionDate?: string | null;
  deleteAt?: string | null;
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

export type RestMouvementStock = RestOf<IMouvementStock>;

export type NewRestMouvementStock = RestOf<NewMouvementStock>;

export type PartialUpdateRestMouvementStock = RestOf<PartialUpdateMouvementStock>;

export type EntityResponseType = HttpResponse<IMouvementStock>;
export type EntityArrayResponseType = HttpResponse<IMouvementStock[]>;

@Injectable({ providedIn: 'root' })
export class MouvementStockService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/mouvement-stocks');

  create(mouvementStock: NewMouvementStock): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mouvementStock);
    return this.http
      .post<RestMouvementStock>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(mouvementStock: IMouvementStock): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mouvementStock);
    return this.http
      .put<RestMouvementStock>(`${this.resourceUrl}/${this.getMouvementStockIdentifier(mouvementStock)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(mouvementStock: PartialUpdateMouvementStock): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mouvementStock);
    return this.http
      .patch<RestMouvementStock>(`${this.resourceUrl}/${this.getMouvementStockIdentifier(mouvementStock)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestMouvementStock>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestMouvementStock[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMouvementStockIdentifier(mouvementStock: Pick<IMouvementStock, 'id'>): string {
    return mouvementStock.id;
  }

  compareMouvementStock(o1: Pick<IMouvementStock, 'id'> | null, o2: Pick<IMouvementStock, 'id'> | null): boolean {
    return o1 && o2 ? this.getMouvementStockIdentifier(o1) === this.getMouvementStockIdentifier(o2) : o1 === o2;
  }

  addMouvementStockToCollectionIfMissing<Type extends Pick<IMouvementStock, 'id'>>(
    mouvementStockCollection: Type[],
    ...mouvementStocksToCheck: (Type | null | undefined)[]
  ): Type[] {
    const mouvementStocks: Type[] = mouvementStocksToCheck.filter(isPresent);
    if (mouvementStocks.length > 0) {
      const mouvementStockCollectionIdentifiers = mouvementStockCollection.map(mouvementStockItem =>
        this.getMouvementStockIdentifier(mouvementStockItem),
      );
      const mouvementStocksToAdd = mouvementStocks.filter(mouvementStockItem => {
        const mouvementStockIdentifier = this.getMouvementStockIdentifier(mouvementStockItem);
        if (mouvementStockCollectionIdentifiers.includes(mouvementStockIdentifier)) {
          return false;
        }
        mouvementStockCollectionIdentifiers.push(mouvementStockIdentifier);
        return true;
      });
      return [...mouvementStocksToAdd, ...mouvementStockCollection];
    }
    return mouvementStockCollection;
  }

  protected convertDateFromClient<T extends IMouvementStock | NewMouvementStock | PartialUpdateMouvementStock>(
    mouvementStock: T,
  ): RestOf<T> {
    return {
      ...mouvementStock,
      transactionDate: mouvementStock.transactionDate?.toJSON() ?? null,
      deleteAt: mouvementStock.deleteAt?.toJSON() ?? null,
      createdDate: mouvementStock.createdDate?.toJSON() ?? null,
      lastModifiedDate: mouvementStock.lastModifiedDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restMouvementStock: RestMouvementStock): IMouvementStock {
    return {
      ...restMouvementStock,
      transactionDate: restMouvementStock.transactionDate ? dayjs(restMouvementStock.transactionDate) : undefined,
      deleteAt: restMouvementStock.deleteAt ? dayjs(restMouvementStock.deleteAt) : undefined,
      createdDate: restMouvementStock.createdDate ? dayjs(restMouvementStock.createdDate) : undefined,
      lastModifiedDate: restMouvementStock.lastModifiedDate ? dayjs(restMouvementStock.lastModifiedDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestMouvementStock>): HttpResponse<IMouvementStock> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestMouvementStock[]>): HttpResponse<IMouvementStock[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
