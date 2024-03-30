import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'stockApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'entrepot',
    data: { pageTitle: 'stockApp.entrepot.home.title' },
    loadChildren: () => import('./entrepot/entrepot.routes'),
  },
  {
    path: 'structure',
    data: { pageTitle: 'stockApp.structure.home.title' },
    loadChildren: () => import('./structure/structure.routes'),
  },
  {
    path: 'produit',
    data: { pageTitle: 'stockApp.produit.home.title' },
    loadChildren: () => import('./produit/produit.routes'),
  },
  {
    path: 'categorie',
    data: { pageTitle: 'stockApp.categorie.home.title' },
    loadChildren: () => import('./categorie/categorie.routes'),
  },
  {
    path: 'mouvement-stock',
    data: { pageTitle: 'stockApp.mouvementStock.home.title' },
    loadChildren: () => import('./mouvement-stock/mouvement-stock.routes'),
  },
  {
    path: 'stock',
    data: { pageTitle: 'stockApp.stock.home.title' },
    loadChildren: () => import('./stock/stock.routes'),
  },
  {
    path: 'inventaire',
    data: { pageTitle: 'stockApp.inventaire.home.title' },
    loadChildren: () => import('./inventaire/inventaire.routes'),
  },
  {
    path: 'vente',
    data: { pageTitle: 'stockApp.vente.home.title' },
    loadChildren: () => import('./vente/vente.routes'),
  },
  {
    path: 'abonnement',
    data: { pageTitle: 'stockApp.abonnement.home.title' },
    loadChildren: () => import('./abonnement/abonnement.routes'),
  },
  {
    path: 'paiement',
    data: { pageTitle: 'stockApp.paiement.home.title' },
    loadChildren: () => import('./paiement/paiement.routes'),
  },
  {
    path: 'plans-abonnement',
    data: { pageTitle: 'stockApp.plansAbonnement.home.title' },
    loadChildren: () => import('./plans-abonnement/plans-abonnement.routes'),
  },
  {
    path: 'employe',
    data: { pageTitle: 'stockApp.employe.home.title' },
    loadChildren: () => import('./employe/employe.routes'),
  },
  {
    path: 'permission',
    data: { pageTitle: 'stockApp.permission.home.title' },
    loadChildren: () => import('./permission/permission.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
