import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IInventaire, NewInventaire } from '../inventaire.model';

export type PartialUpdateInventaire = Partial<IInventaire> & Pick<IInventaire, 'id'>;

type RestOf<T extends IInventaire | NewInventaire> = Omit<T, 'inventaireDate' | 'deleteAt' | 'createdDate' | 'lastModifiedDate'> & {
  inventaireDate?: string | null;
  deleteAt?: string | null;
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

export type RestInventaire = RestOf<IInventaire>;

export type NewRestInventaire = RestOf<NewInventaire>;

export type PartialUpdateRestInventaire = RestOf<PartialUpdateInventaire>;

export type EntityResponseType = HttpResponse<IInventaire>;
export type EntityArrayResponseType = HttpResponse<IInventaire[]>;

@Injectable({ providedIn: 'root' })
export class InventaireService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/inventaires');

  create(inventaire: NewInventaire): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inventaire);
    return this.http
      .post<RestInventaire>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(inventaire: IInventaire): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inventaire);
    return this.http
      .put<RestInventaire>(`${this.resourceUrl}/${this.getInventaireIdentifier(inventaire)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(inventaire: PartialUpdateInventaire): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inventaire);
    return this.http
      .patch<RestInventaire>(`${this.resourceUrl}/${this.getInventaireIdentifier(inventaire)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestInventaire>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestInventaire[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getInventaireIdentifier(inventaire: Pick<IInventaire, 'id'>): string {
    return inventaire.id;
  }

  compareInventaire(o1: Pick<IInventaire, 'id'> | null, o2: Pick<IInventaire, 'id'> | null): boolean {
    return o1 && o2 ? this.getInventaireIdentifier(o1) === this.getInventaireIdentifier(o2) : o1 === o2;
  }

  addInventaireToCollectionIfMissing<Type extends Pick<IInventaire, 'id'>>(
    inventaireCollection: Type[],
    ...inventairesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const inventaires: Type[] = inventairesToCheck.filter(isPresent);
    if (inventaires.length > 0) {
      const inventaireCollectionIdentifiers = inventaireCollection.map(inventaireItem => this.getInventaireIdentifier(inventaireItem));
      const inventairesToAdd = inventaires.filter(inventaireItem => {
        const inventaireIdentifier = this.getInventaireIdentifier(inventaireItem);
        if (inventaireCollectionIdentifiers.includes(inventaireIdentifier)) {
          return false;
        }
        inventaireCollectionIdentifiers.push(inventaireIdentifier);
        return true;
      });
      return [...inventairesToAdd, ...inventaireCollection];
    }
    return inventaireCollection;
  }

  protected convertDateFromClient<T extends IInventaire | NewInventaire | PartialUpdateInventaire>(inventaire: T): RestOf<T> {
    return {
      ...inventaire,
      inventaireDate: inventaire.inventaireDate?.toJSON() ?? null,
      deleteAt: inventaire.deleteAt?.toJSON() ?? null,
      createdDate: inventaire.createdDate?.toJSON() ?? null,
      lastModifiedDate: inventaire.lastModifiedDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restInventaire: RestInventaire): IInventaire {
    return {
      ...restInventaire,
      inventaireDate: restInventaire.inventaireDate ? dayjs(restInventaire.inventaireDate) : undefined,
      deleteAt: restInventaire.deleteAt ? dayjs(restInventaire.deleteAt) : undefined,
      createdDate: restInventaire.createdDate ? dayjs(restInventaire.createdDate) : undefined,
      lastModifiedDate: restInventaire.lastModifiedDate ? dayjs(restInventaire.lastModifiedDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestInventaire>): HttpResponse<IInventaire> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestInventaire[]>): HttpResponse<IInventaire[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
