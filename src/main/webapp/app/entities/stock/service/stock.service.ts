import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IStock, NewStock } from '../stock.model';

export type PartialUpdateStock = Partial<IStock> & Pick<IStock, 'id'>;

type RestOf<T extends IStock | NewStock> = Omit<T, 'deleteAt' | 'createdDate' | 'lastModifiedDate'> & {
  deleteAt?: string | null;
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

export type RestStock = RestOf<IStock>;

export type NewRestStock = RestOf<NewStock>;

export type PartialUpdateRestStock = RestOf<PartialUpdateStock>;

export type EntityResponseType = HttpResponse<IStock>;
export type EntityArrayResponseType = HttpResponse<IStock[]>;

@Injectable({ providedIn: 'root' })
export class StockService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/stocks');

  create(stock: NewStock): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(stock);
    return this.http.post<RestStock>(this.resourceUrl, copy, { observe: 'response' }).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(stock: IStock): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(stock);
    return this.http
      .put<RestStock>(`${this.resourceUrl}/${this.getStockIdentifier(stock)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(stock: PartialUpdateStock): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(stock);
    return this.http
      .patch<RestStock>(`${this.resourceUrl}/${this.getStockIdentifier(stock)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestStock>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestStock[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getStockIdentifier(stock: Pick<IStock, 'id'>): string {
    return stock.id;
  }

  compareStock(o1: Pick<IStock, 'id'> | null, o2: Pick<IStock, 'id'> | null): boolean {
    return o1 && o2 ? this.getStockIdentifier(o1) === this.getStockIdentifier(o2) : o1 === o2;
  }

  addStockToCollectionIfMissing<Type extends Pick<IStock, 'id'>>(
    stockCollection: Type[],
    ...stocksToCheck: (Type | null | undefined)[]
  ): Type[] {
    const stocks: Type[] = stocksToCheck.filter(isPresent);
    if (stocks.length > 0) {
      const stockCollectionIdentifiers = stockCollection.map(stockItem => this.getStockIdentifier(stockItem));
      const stocksToAdd = stocks.filter(stockItem => {
        const stockIdentifier = this.getStockIdentifier(stockItem);
        if (stockCollectionIdentifiers.includes(stockIdentifier)) {
          return false;
        }
        stockCollectionIdentifiers.push(stockIdentifier);
        return true;
      });
      return [...stocksToAdd, ...stockCollection];
    }
    return stockCollection;
  }

  protected convertDateFromClient<T extends IStock | NewStock | PartialUpdateStock>(stock: T): RestOf<T> {
    return {
      ...stock,
      deleteAt: stock.deleteAt?.toJSON() ?? null,
      createdDate: stock.createdDate?.toJSON() ?? null,
      lastModifiedDate: stock.lastModifiedDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restStock: RestStock): IStock {
    return {
      ...restStock,
      deleteAt: restStock.deleteAt ? dayjs(restStock.deleteAt) : undefined,
      createdDate: restStock.createdDate ? dayjs(restStock.createdDate) : undefined,
      lastModifiedDate: restStock.lastModifiedDate ? dayjs(restStock.lastModifiedDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestStock>): HttpResponse<IStock> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestStock[]>): HttpResponse<IStock[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
