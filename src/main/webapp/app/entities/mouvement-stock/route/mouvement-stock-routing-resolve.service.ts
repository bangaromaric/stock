import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMouvementStock } from '../mouvement-stock.model';
import { MouvementStockService } from '../service/mouvement-stock.service';

const mouvementStockResolve = (route: ActivatedRouteSnapshot): Observable<null | IMouvementStock> => {
  const id = route.params['id'];
  if (id) {
    return inject(MouvementStockService)
      .find(id)
      .pipe(
        mergeMap((mouvementStock: HttpResponse<IMouvementStock>) => {
          if (mouvementStock.body) {
            return of(mouvementStock.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default mouvementStockResolve;
