import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEmploye } from '../employe.model';
import { EmployeService } from '../service/employe.service';

const employeResolve = (route: ActivatedRouteSnapshot): Observable<null | IEmploye> => {
  const id = route.params['id'];
  if (id) {
    return inject(EmployeService)
      .find(id)
      .pipe(
        mergeMap((employe: HttpResponse<IEmploye>) => {
          if (employe.body) {
            return of(employe.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default employeResolve;
