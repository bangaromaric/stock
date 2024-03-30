import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IInventaire } from '../inventaire.model';
import { InventaireService } from '../service/inventaire.service';

const inventaireResolve = (route: ActivatedRouteSnapshot): Observable<null | IInventaire> => {
  const id = route.params['id'];
  if (id) {
    return inject(InventaireService)
      .find(id)
      .pipe(
        mergeMap((inventaire: HttpResponse<IInventaire>) => {
          if (inventaire.body) {
            return of(inventaire.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default inventaireResolve;
