import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVente } from '../vente.model';
import { VenteService } from '../service/vente.service';

const venteResolve = (route: ActivatedRouteSnapshot): Observable<null | IVente> => {
  const id = route.params['id'];
  if (id) {
    return inject(VenteService)
      .find(id)
      .pipe(
        mergeMap((vente: HttpResponse<IVente>) => {
          if (vente.body) {
            return of(vente.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default venteResolve;
