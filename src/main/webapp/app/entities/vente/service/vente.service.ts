import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVente, NewVente } from '../vente.model';

export type PartialUpdateVente = Partial<IVente> & Pick<IVente, 'id'>;

type RestOf<T extends IVente | NewVente> = Omit<T, 'venteDate' | 'deleteAt' | 'createdDate' | 'lastModifiedDate'> & {
  venteDate?: string | null;
  deleteAt?: string | null;
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

export type RestVente = RestOf<IVente>;

export type NewRestVente = RestOf<NewVente>;

export type PartialUpdateRestVente = RestOf<PartialUpdateVente>;

export type EntityResponseType = HttpResponse<IVente>;
export type EntityArrayResponseType = HttpResponse<IVente[]>;

@Injectable({ providedIn: 'root' })
export class VenteService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ventes');

  create(vente: NewVente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vente);
    return this.http.post<RestVente>(this.resourceUrl, copy, { observe: 'response' }).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(vente: IVente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vente);
    return this.http
      .put<RestVente>(`${this.resourceUrl}/${this.getVenteIdentifier(vente)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(vente: PartialUpdateVente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vente);
    return this.http
      .patch<RestVente>(`${this.resourceUrl}/${this.getVenteIdentifier(vente)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestVente>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestVente[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getVenteIdentifier(vente: Pick<IVente, 'id'>): string {
    return vente.id;
  }

  compareVente(o1: Pick<IVente, 'id'> | null, o2: Pick<IVente, 'id'> | null): boolean {
    return o1 && o2 ? this.getVenteIdentifier(o1) === this.getVenteIdentifier(o2) : o1 === o2;
  }

  addVenteToCollectionIfMissing<Type extends Pick<IVente, 'id'>>(
    venteCollection: Type[],
    ...ventesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const ventes: Type[] = ventesToCheck.filter(isPresent);
    if (ventes.length > 0) {
      const venteCollectionIdentifiers = venteCollection.map(venteItem => this.getVenteIdentifier(venteItem));
      const ventesToAdd = ventes.filter(venteItem => {
        const venteIdentifier = this.getVenteIdentifier(venteItem);
        if (venteCollectionIdentifiers.includes(venteIdentifier)) {
          return false;
        }
        venteCollectionIdentifiers.push(venteIdentifier);
        return true;
      });
      return [...ventesToAdd, ...venteCollection];
    }
    return venteCollection;
  }

  protected convertDateFromClient<T extends IVente | NewVente | PartialUpdateVente>(vente: T): RestOf<T> {
    return {
      ...vente,
      venteDate: vente.venteDate?.toJSON() ?? null,
      deleteAt: vente.deleteAt?.toJSON() ?? null,
      createdDate: vente.createdDate?.toJSON() ?? null,
      lastModifiedDate: vente.lastModifiedDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restVente: RestVente): IVente {
    return {
      ...restVente,
      venteDate: restVente.venteDate ? dayjs(restVente.venteDate) : undefined,
      deleteAt: restVente.deleteAt ? dayjs(restVente.deleteAt) : undefined,
      createdDate: restVente.createdDate ? dayjs(restVente.createdDate) : undefined,
      lastModifiedDate: restVente.lastModifiedDate ? dayjs(restVente.lastModifiedDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestVente>): HttpResponse<IVente> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestVente[]>): HttpResponse<IVente[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
