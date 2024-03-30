import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { InventaireComponent } from './list/inventaire.component';
import { InventaireDetailComponent } from './detail/inventaire-detail.component';
import { InventaireUpdateComponent } from './update/inventaire-update.component';
import InventaireResolve from './route/inventaire-routing-resolve.service';

const inventaireRoute: Routes = [
  {
    path: '',
    component: InventaireComponent,
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InventaireDetailComponent,
    resolve: {
      inventaire: InventaireResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InventaireUpdateComponent,
    resolve: {
      inventaire: InventaireResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InventaireUpdateComponent,
    resolve: {
      inventaire: InventaireResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default inventaireRoute;
