import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PlansAbonnementComponent } from './list/plans-abonnement.component';
import { PlansAbonnementDetailComponent } from './detail/plans-abonnement-detail.component';
import { PlansAbonnementUpdateComponent } from './update/plans-abonnement-update.component';
import PlansAbonnementResolve from './route/plans-abonnement-routing-resolve.service';

const plansAbonnementRoute: Routes = [
  {
    path: '',
    component: PlansAbonnementComponent,
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PlansAbonnementDetailComponent,
    resolve: {
      plansAbonnement: PlansAbonnementResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PlansAbonnementUpdateComponent,
    resolve: {
      plansAbonnement: PlansAbonnementResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PlansAbonnementUpdateComponent,
    resolve: {
      plansAbonnement: PlansAbonnementResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default plansAbonnementRoute;
