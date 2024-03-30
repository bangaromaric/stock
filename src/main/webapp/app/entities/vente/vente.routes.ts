import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { VenteComponent } from './list/vente.component';
import { VenteDetailComponent } from './detail/vente-detail.component';
import { VenteUpdateComponent } from './update/vente-update.component';
import VenteResolve from './route/vente-routing-resolve.service';

const venteRoute: Routes = [
  {
    path: '',
    component: VenteComponent,
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VenteDetailComponent,
    resolve: {
      vente: VenteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VenteUpdateComponent,
    resolve: {
      vente: VenteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VenteUpdateComponent,
    resolve: {
      vente: VenteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default venteRoute;
