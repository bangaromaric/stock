import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICategorie, NewCategorie } from '../categorie.model';

export type PartialUpdateCategorie = Partial<ICategorie> & Pick<ICategorie, 'id'>;

type RestOf<T extends ICategorie | NewCategorie> = Omit<T, 'deleteAt' | 'createdDate' | 'lastModifiedDate'> & {
  deleteAt?: string | null;
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

export type RestCategorie = RestOf<ICategorie>;

export type NewRestCategorie = RestOf<NewCategorie>;

export type PartialUpdateRestCategorie = RestOf<PartialUpdateCategorie>;

export type EntityResponseType = HttpResponse<ICategorie>;
export type EntityArrayResponseType = HttpResponse<ICategorie[]>;

@Injectable({ providedIn: 'root' })
export class CategorieService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/categories');

  create(categorie: NewCategorie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(categorie);
    return this.http
      .post<RestCategorie>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(categorie: ICategorie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(categorie);
    return this.http
      .put<RestCategorie>(`${this.resourceUrl}/${this.getCategorieIdentifier(categorie)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(categorie: PartialUpdateCategorie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(categorie);
    return this.http
      .patch<RestCategorie>(`${this.resourceUrl}/${this.getCategorieIdentifier(categorie)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestCategorie>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCategorie[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCategorieIdentifier(categorie: Pick<ICategorie, 'id'>): string {
    return categorie.id;
  }

  compareCategorie(o1: Pick<ICategorie, 'id'> | null, o2: Pick<ICategorie, 'id'> | null): boolean {
    return o1 && o2 ? this.getCategorieIdentifier(o1) === this.getCategorieIdentifier(o2) : o1 === o2;
  }

  addCategorieToCollectionIfMissing<Type extends Pick<ICategorie, 'id'>>(
    categorieCollection: Type[],
    ...categoriesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const categories: Type[] = categoriesToCheck.filter(isPresent);
    if (categories.length > 0) {
      const categorieCollectionIdentifiers = categorieCollection.map(categorieItem => this.getCategorieIdentifier(categorieItem));
      const categoriesToAdd = categories.filter(categorieItem => {
        const categorieIdentifier = this.getCategorieIdentifier(categorieItem);
        if (categorieCollectionIdentifiers.includes(categorieIdentifier)) {
          return false;
        }
        categorieCollectionIdentifiers.push(categorieIdentifier);
        return true;
      });
      return [...categoriesToAdd, ...categorieCollection];
    }
    return categorieCollection;
  }

  protected convertDateFromClient<T extends ICategorie | NewCategorie | PartialUpdateCategorie>(categorie: T): RestOf<T> {
    return {
      ...categorie,
      deleteAt: categorie.deleteAt?.toJSON() ?? null,
      createdDate: categorie.createdDate?.toJSON() ?? null,
      lastModifiedDate: categorie.lastModifiedDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restCategorie: RestCategorie): ICategorie {
    return {
      ...restCategorie,
      deleteAt: restCategorie.deleteAt ? dayjs(restCategorie.deleteAt) : undefined,
      createdDate: restCategorie.createdDate ? dayjs(restCategorie.createdDate) : undefined,
      lastModifiedDate: restCategorie.lastModifiedDate ? dayjs(restCategorie.lastModifiedDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestCategorie>): HttpResponse<ICategorie> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestCategorie[]>): HttpResponse<ICategorie[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
