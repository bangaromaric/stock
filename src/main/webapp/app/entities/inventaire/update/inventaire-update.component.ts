import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEntrepot } from 'app/entities/entrepot/entrepot.model';
import { EntrepotService } from 'app/entities/entrepot/service/entrepot.service';
import { IProduit } from 'app/entities/produit/produit.model';
import { ProduitService } from 'app/entities/produit/service/produit.service';
import { InventaireService } from '../service/inventaire.service';
import { IInventaire } from '../inventaire.model';
import { InventaireFormService, InventaireFormGroup } from './inventaire-form.service';

@Component({
  standalone: true,
  selector: 'jhi-inventaire-update',
  templateUrl: './inventaire-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class InventaireUpdateComponent implements OnInit {
  isSaving = false;
  inventaire: IInventaire | null = null;

  entrepotsSharedCollection: IEntrepot[] = [];
  produitsSharedCollection: IProduit[] = [];

  protected inventaireService = inject(InventaireService);
  protected inventaireFormService = inject(InventaireFormService);
  protected entrepotService = inject(EntrepotService);
  protected produitService = inject(ProduitService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: InventaireFormGroup = this.inventaireFormService.createInventaireFormGroup();

  compareEntrepot = (o1: IEntrepot | null, o2: IEntrepot | null): boolean => this.entrepotService.compareEntrepot(o1, o2);

  compareProduit = (o1: IProduit | null, o2: IProduit | null): boolean => this.produitService.compareProduit(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inventaire }) => {
      this.inventaire = inventaire;
      if (inventaire) {
        this.updateForm(inventaire);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const inventaire = this.inventaireFormService.getInventaire(this.editForm);
    if (inventaire.id !== null) {
      this.subscribeToSaveResponse(this.inventaireService.update(inventaire));
    } else {
      this.subscribeToSaveResponse(this.inventaireService.create(inventaire));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInventaire>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(inventaire: IInventaire): void {
    this.inventaire = inventaire;
    this.inventaireFormService.resetForm(this.editForm, inventaire);

    this.entrepotsSharedCollection = this.entrepotService.addEntrepotToCollectionIfMissing<IEntrepot>(
      this.entrepotsSharedCollection,
      inventaire.entrepot,
    );
    this.produitsSharedCollection = this.produitService.addProduitToCollectionIfMissing<IProduit>(
      this.produitsSharedCollection,
      inventaire.produit,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.entrepotService
      .query()
      .pipe(map((res: HttpResponse<IEntrepot[]>) => res.body ?? []))
      .pipe(
        map((entrepots: IEntrepot[]) =>
          this.entrepotService.addEntrepotToCollectionIfMissing<IEntrepot>(entrepots, this.inventaire?.entrepot),
        ),
      )
      .subscribe((entrepots: IEntrepot[]) => (this.entrepotsSharedCollection = entrepots));

    this.produitService
      .query()
      .pipe(map((res: HttpResponse<IProduit[]>) => res.body ?? []))
      .pipe(
        map((produits: IProduit[]) => this.produitService.addProduitToCollectionIfMissing<IProduit>(produits, this.inventaire?.produit)),
      )
      .subscribe((produits: IProduit[]) => (this.produitsSharedCollection = produits));
  }
}
