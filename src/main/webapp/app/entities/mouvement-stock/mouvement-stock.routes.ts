import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MouvementStockComponent } from './list/mouvement-stock.component';
import { MouvementStockDetailComponent } from './detail/mouvement-stock-detail.component';
import { MouvementStockUpdateComponent } from './update/mouvement-stock-update.component';
import MouvementStockResolve from './route/mouvement-stock-routing-resolve.service';

const mouvementStockRoute: Routes = [
  {
    path: '',
    component: MouvementStockComponent,
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MouvementStockDetailComponent,
    resolve: {
      mouvementStock: MouvementStockResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MouvementStockUpdateComponent,
    resolve: {
      mouvementStock: MouvementStockResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MouvementStockUpdateComponent,
    resolve: {
      mouvementStock: MouvementStockResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default mouvementStockRoute;
