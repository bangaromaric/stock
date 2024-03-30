import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPlansAbonnement } from '../plans-abonnement.model';
import { PlansAbonnementService } from '../service/plans-abonnement.service';

const plansAbonnementResolve = (route: ActivatedRouteSnapshot): Observable<null | IPlansAbonnement> => {
  const id = route.params['id'];
  if (id) {
    return inject(PlansAbonnementService)
      .find(id)
      .pipe(
        mergeMap((plansAbonnement: HttpResponse<IPlansAbonnement>) => {
          if (plansAbonnement.body) {
            return of(plansAbonnement.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default plansAbonnementResolve;
