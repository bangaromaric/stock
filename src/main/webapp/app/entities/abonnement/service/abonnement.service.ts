import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAbonnement, NewAbonnement } from '../abonnement.model';

export type PartialUpdateAbonnement = Partial<IAbonnement> & Pick<IAbonnement, 'id'>;

type RestOf<T extends IAbonnement | NewAbonnement> = Omit<T, 'dateDebut' | 'dateFin' | 'deleteAt' | 'createdDate' | 'lastModifiedDate'> & {
  dateDebut?: string | null;
  dateFin?: string | null;
  deleteAt?: string | null;
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

export type RestAbonnement = RestOf<IAbonnement>;

export type NewRestAbonnement = RestOf<NewAbonnement>;

export type PartialUpdateRestAbonnement = RestOf<PartialUpdateAbonnement>;

export type EntityResponseType = HttpResponse<IAbonnement>;
export type EntityArrayResponseType = HttpResponse<IAbonnement[]>;

@Injectable({ providedIn: 'root' })
export class AbonnementService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/abonnements');

  create(abonnement: NewAbonnement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(abonnement);
    return this.http
      .post<RestAbonnement>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(abonnement: IAbonnement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(abonnement);
    return this.http
      .put<RestAbonnement>(`${this.resourceUrl}/${this.getAbonnementIdentifier(abonnement)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(abonnement: PartialUpdateAbonnement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(abonnement);
    return this.http
      .patch<RestAbonnement>(`${this.resourceUrl}/${this.getAbonnementIdentifier(abonnement)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestAbonnement>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAbonnement[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAbonnementIdentifier(abonnement: Pick<IAbonnement, 'id'>): string {
    return abonnement.id;
  }

  compareAbonnement(o1: Pick<IAbonnement, 'id'> | null, o2: Pick<IAbonnement, 'id'> | null): boolean {
    return o1 && o2 ? this.getAbonnementIdentifier(o1) === this.getAbonnementIdentifier(o2) : o1 === o2;
  }

  addAbonnementToCollectionIfMissing<Type extends Pick<IAbonnement, 'id'>>(
    abonnementCollection: Type[],
    ...abonnementsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const abonnements: Type[] = abonnementsToCheck.filter(isPresent);
    if (abonnements.length > 0) {
      const abonnementCollectionIdentifiers = abonnementCollection.map(abonnementItem => this.getAbonnementIdentifier(abonnementItem));
      const abonnementsToAdd = abonnements.filter(abonnementItem => {
        const abonnementIdentifier = this.getAbonnementIdentifier(abonnementItem);
        if (abonnementCollectionIdentifiers.includes(abonnementIdentifier)) {
          return false;
        }
        abonnementCollectionIdentifiers.push(abonnementIdentifier);
        return true;
      });
      return [...abonnementsToAdd, ...abonnementCollection];
    }
    return abonnementCollection;
  }

  protected convertDateFromClient<T extends IAbonnement | NewAbonnement | PartialUpdateAbonnement>(abonnement: T): RestOf<T> {
    return {
      ...abonnement,
      dateDebut: abonnement.dateDebut?.toJSON() ?? null,
      dateFin: abonnement.dateFin?.toJSON() ?? null,
      deleteAt: abonnement.deleteAt?.toJSON() ?? null,
      createdDate: abonnement.createdDate?.toJSON() ?? null,
      lastModifiedDate: abonnement.lastModifiedDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restAbonnement: RestAbonnement): IAbonnement {
    return {
      ...restAbonnement,
      dateDebut: restAbonnement.dateDebut ? dayjs(restAbonnement.dateDebut) : undefined,
      dateFin: restAbonnement.dateFin ? dayjs(restAbonnement.dateFin) : undefined,
      deleteAt: restAbonnement.deleteAt ? dayjs(restAbonnement.deleteAt) : undefined,
      createdDate: restAbonnement.createdDate ? dayjs(restAbonnement.createdDate) : undefined,
      lastModifiedDate: restAbonnement.lastModifiedDate ? dayjs(restAbonnement.lastModifiedDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAbonnement>): HttpResponse<IAbonnement> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestAbonnement[]>): HttpResponse<IAbonnement[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
