import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPlansAbonnement, NewPlansAbonnement } from '../plans-abonnement.model';

export type PartialUpdatePlansAbonnement = Partial<IPlansAbonnement> & Pick<IPlansAbonnement, 'id'>;

type RestOf<T extends IPlansAbonnement | NewPlansAbonnement> = Omit<T, 'deleteAt' | 'createdDate' | 'lastModifiedDate'> & {
  deleteAt?: string | null;
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

export type RestPlansAbonnement = RestOf<IPlansAbonnement>;

export type NewRestPlansAbonnement = RestOf<NewPlansAbonnement>;

export type PartialUpdateRestPlansAbonnement = RestOf<PartialUpdatePlansAbonnement>;

export type EntityResponseType = HttpResponse<IPlansAbonnement>;
export type EntityArrayResponseType = HttpResponse<IPlansAbonnement[]>;

@Injectable({ providedIn: 'root' })
export class PlansAbonnementService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/plans-abonnements');

  create(plansAbonnement: NewPlansAbonnement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(plansAbonnement);
    return this.http
      .post<RestPlansAbonnement>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(plansAbonnement: IPlansAbonnement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(plansAbonnement);
    return this.http
      .put<RestPlansAbonnement>(`${this.resourceUrl}/${this.getPlansAbonnementIdentifier(plansAbonnement)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(plansAbonnement: PartialUpdatePlansAbonnement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(plansAbonnement);
    return this.http
      .patch<RestPlansAbonnement>(`${this.resourceUrl}/${this.getPlansAbonnementIdentifier(plansAbonnement)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestPlansAbonnement>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestPlansAbonnement[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPlansAbonnementIdentifier(plansAbonnement: Pick<IPlansAbonnement, 'id'>): string {
    return plansAbonnement.id;
  }

  comparePlansAbonnement(o1: Pick<IPlansAbonnement, 'id'> | null, o2: Pick<IPlansAbonnement, 'id'> | null): boolean {
    return o1 && o2 ? this.getPlansAbonnementIdentifier(o1) === this.getPlansAbonnementIdentifier(o2) : o1 === o2;
  }

  addPlansAbonnementToCollectionIfMissing<Type extends Pick<IPlansAbonnement, 'id'>>(
    plansAbonnementCollection: Type[],
    ...plansAbonnementsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const plansAbonnements: Type[] = plansAbonnementsToCheck.filter(isPresent);
    if (plansAbonnements.length > 0) {
      const plansAbonnementCollectionIdentifiers = plansAbonnementCollection.map(plansAbonnementItem =>
        this.getPlansAbonnementIdentifier(plansAbonnementItem),
      );
      const plansAbonnementsToAdd = plansAbonnements.filter(plansAbonnementItem => {
        const plansAbonnementIdentifier = this.getPlansAbonnementIdentifier(plansAbonnementItem);
        if (plansAbonnementCollectionIdentifiers.includes(plansAbonnementIdentifier)) {
          return false;
        }
        plansAbonnementCollectionIdentifiers.push(plansAbonnementIdentifier);
        return true;
      });
      return [...plansAbonnementsToAdd, ...plansAbonnementCollection];
    }
    return plansAbonnementCollection;
  }

  protected convertDateFromClient<T extends IPlansAbonnement | NewPlansAbonnement | PartialUpdatePlansAbonnement>(
    plansAbonnement: T,
  ): RestOf<T> {
    return {
      ...plansAbonnement,
      deleteAt: plansAbonnement.deleteAt?.toJSON() ?? null,
      createdDate: plansAbonnement.createdDate?.toJSON() ?? null,
      lastModifiedDate: plansAbonnement.lastModifiedDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restPlansAbonnement: RestPlansAbonnement): IPlansAbonnement {
    return {
      ...restPlansAbonnement,
      deleteAt: restPlansAbonnement.deleteAt ? dayjs(restPlansAbonnement.deleteAt) : undefined,
      createdDate: restPlansAbonnement.createdDate ? dayjs(restPlansAbonnement.createdDate) : undefined,
      lastModifiedDate: restPlansAbonnement.lastModifiedDate ? dayjs(restPlansAbonnement.lastModifiedDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestPlansAbonnement>): HttpResponse<IPlansAbonnement> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestPlansAbonnement[]>): HttpResponse<IPlansAbonnement[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
