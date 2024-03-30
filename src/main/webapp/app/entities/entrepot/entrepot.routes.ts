import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EntrepotComponent } from './list/entrepot.component';
import { EntrepotDetailComponent } from './detail/entrepot-detail.component';
import { EntrepotUpdateComponent } from './update/entrepot-update.component';
import EntrepotResolve from './route/entrepot-routing-resolve.service';

const entrepotRoute: Routes = [
  {
    path: '',
    component: EntrepotComponent,
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EntrepotDetailComponent,
    resolve: {
      entrepot: EntrepotResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EntrepotUpdateComponent,
    resolve: {
      entrepot: EntrepotResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EntrepotUpdateComponent,
    resolve: {
      entrepot: EntrepotResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default entrepotRoute;
