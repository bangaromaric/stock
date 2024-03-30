import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EmployeComponent } from './list/employe.component';
import { EmployeDetailComponent } from './detail/employe-detail.component';
import { EmployeUpdateComponent } from './update/employe-update.component';
import EmployeResolve from './route/employe-routing-resolve.service';

const employeRoute: Routes = [
  {
    path: '',
    component: EmployeComponent,
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmployeDetailComponent,
    resolve: {
      employe: EmployeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmployeUpdateComponent,
    resolve: {
      employe: EmployeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmployeUpdateComponent,
    resolve: {
      employe: EmployeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default employeRoute;
