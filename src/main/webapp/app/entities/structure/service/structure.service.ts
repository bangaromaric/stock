import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IStructure, NewStructure } from '../structure.model';

export type PartialUpdateStructure = Partial<IStructure> & Pick<IStructure, 'id'>;

type RestOf<T extends IStructure | NewStructure> = Omit<T, 'deleteAt' | 'createdDate' | 'lastModifiedDate'> & {
  deleteAt?: string | null;
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

export type RestStructure = RestOf<IStructure>;

export type NewRestStructure = RestOf<NewStructure>;

export type PartialUpdateRestStructure = RestOf<PartialUpdateStructure>;

export type EntityResponseType = HttpResponse<IStructure>;
export type EntityArrayResponseType = HttpResponse<IStructure[]>;

@Injectable({ providedIn: 'root' })
export class StructureService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/structures');

  create(structure: NewStructure): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(structure);
    return this.http
      .post<RestStructure>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(structure: IStructure): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(structure);
    return this.http
      .put<RestStructure>(`${this.resourceUrl}/${this.getStructureIdentifier(structure)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(structure: PartialUpdateStructure): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(structure);
    return this.http
      .patch<RestStructure>(`${this.resourceUrl}/${this.getStructureIdentifier(structure)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestStructure>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestStructure[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getStructureIdentifier(structure: Pick<IStructure, 'id'>): string {
    return structure.id;
  }

  compareStructure(o1: Pick<IStructure, 'id'> | null, o2: Pick<IStructure, 'id'> | null): boolean {
    return o1 && o2 ? this.getStructureIdentifier(o1) === this.getStructureIdentifier(o2) : o1 === o2;
  }

  addStructureToCollectionIfMissing<Type extends Pick<IStructure, 'id'>>(
    structureCollection: Type[],
    ...structuresToCheck: (Type | null | undefined)[]
  ): Type[] {
    const structures: Type[] = structuresToCheck.filter(isPresent);
    if (structures.length > 0) {
      const structureCollectionIdentifiers = structureCollection.map(structureItem => this.getStructureIdentifier(structureItem));
      const structuresToAdd = structures.filter(structureItem => {
        const structureIdentifier = this.getStructureIdentifier(structureItem);
        if (structureCollectionIdentifiers.includes(structureIdentifier)) {
          return false;
        }
        structureCollectionIdentifiers.push(structureIdentifier);
        return true;
      });
      return [...structuresToAdd, ...structureCollection];
    }
    return structureCollection;
  }

  protected convertDateFromClient<T extends IStructure | NewStructure | PartialUpdateStructure>(structure: T): RestOf<T> {
    return {
      ...structure,
      deleteAt: structure.deleteAt?.toJSON() ?? null,
      createdDate: structure.createdDate?.toJSON() ?? null,
      lastModifiedDate: structure.lastModifiedDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restStructure: RestStructure): IStructure {
    return {
      ...restStructure,
      deleteAt: restStructure.deleteAt ? dayjs(restStructure.deleteAt) : undefined,
      createdDate: restStructure.createdDate ? dayjs(restStructure.createdDate) : undefined,
      lastModifiedDate: restStructure.lastModifiedDate ? dayjs(restStructure.lastModifiedDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestStructure>): HttpResponse<IStructure> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestStructure[]>): HttpResponse<IStructure[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
