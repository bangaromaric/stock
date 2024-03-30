import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEntrepot, NewEntrepot } from '../entrepot.model';

export type PartialUpdateEntrepot = Partial<IEntrepot> & Pick<IEntrepot, 'id'>;

type RestOf<T extends IEntrepot | NewEntrepot> = Omit<T, 'deleteAt' | 'createdDate' | 'lastModifiedDate'> & {
  deleteAt?: string | null;
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

export type RestEntrepot = RestOf<IEntrepot>;

export type NewRestEntrepot = RestOf<NewEntrepot>;

export type PartialUpdateRestEntrepot = RestOf<PartialUpdateEntrepot>;

export type EntityResponseType = HttpResponse<IEntrepot>;
export type EntityArrayResponseType = HttpResponse<IEntrepot[]>;

@Injectable({ providedIn: 'root' })
export class EntrepotService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/entrepots');

  create(entrepot: NewEntrepot): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(entrepot);
    return this.http
      .post<RestEntrepot>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(entrepot: IEntrepot): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(entrepot);
    return this.http
      .put<RestEntrepot>(`${this.resourceUrl}/${this.getEntrepotIdentifier(entrepot)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(entrepot: PartialUpdateEntrepot): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(entrepot);
    return this.http
      .patch<RestEntrepot>(`${this.resourceUrl}/${this.getEntrepotIdentifier(entrepot)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestEntrepot>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestEntrepot[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEntrepotIdentifier(entrepot: Pick<IEntrepot, 'id'>): string {
    return entrepot.id;
  }

  compareEntrepot(o1: Pick<IEntrepot, 'id'> | null, o2: Pick<IEntrepot, 'id'> | null): boolean {
    return o1 && o2 ? this.getEntrepotIdentifier(o1) === this.getEntrepotIdentifier(o2) : o1 === o2;
  }

  addEntrepotToCollectionIfMissing<Type extends Pick<IEntrepot, 'id'>>(
    entrepotCollection: Type[],
    ...entrepotsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const entrepots: Type[] = entrepotsToCheck.filter(isPresent);
    if (entrepots.length > 0) {
      const entrepotCollectionIdentifiers = entrepotCollection.map(entrepotItem => this.getEntrepotIdentifier(entrepotItem));
      const entrepotsToAdd = entrepots.filter(entrepotItem => {
        const entrepotIdentifier = this.getEntrepotIdentifier(entrepotItem);
        if (entrepotCollectionIdentifiers.includes(entrepotIdentifier)) {
          return false;
        }
        entrepotCollectionIdentifiers.push(entrepotIdentifier);
        return true;
      });
      return [...entrepotsToAdd, ...entrepotCollection];
    }
    return entrepotCollection;
  }

  protected convertDateFromClient<T extends IEntrepot | NewEntrepot | PartialUpdateEntrepot>(entrepot: T): RestOf<T> {
    return {
      ...entrepot,
      deleteAt: entrepot.deleteAt?.toJSON() ?? null,
      createdDate: entrepot.createdDate?.toJSON() ?? null,
      lastModifiedDate: entrepot.lastModifiedDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restEntrepot: RestEntrepot): IEntrepot {
    return {
      ...restEntrepot,
      deleteAt: restEntrepot.deleteAt ? dayjs(restEntrepot.deleteAt) : undefined,
      createdDate: restEntrepot.createdDate ? dayjs(restEntrepot.createdDate) : undefined,
      lastModifiedDate: restEntrepot.lastModifiedDate ? dayjs(restEntrepot.lastModifiedDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestEntrepot>): HttpResponse<IEntrepot> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestEntrepot[]>): HttpResponse<IEntrepot[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
