import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IProduit } from 'app/entities/produit/produit.model';
import { ProduitService } from 'app/entities/produit/service/produit.service';
import { IEntrepot } from 'app/entities/entrepot/entrepot.model';
import { EntrepotService } from 'app/entities/entrepot/service/entrepot.service';
import { TypeMouvement } from 'app/entities/enumerations/type-mouvement.model';
import { MouvementStockService } from '../service/mouvement-stock.service';
import { IMouvementStock } from '../mouvement-stock.model';
import { MouvementStockFormService, MouvementStockFormGroup } from './mouvement-stock-form.service';

@Component({
  standalone: true,
  selector: 'jhi-mouvement-stock-update',
  templateUrl: './mouvement-stock-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class MouvementStockUpdateComponent implements OnInit {
  isSaving = false;
  mouvementStock: IMouvementStock | null = null;
  typeMouvementValues = Object.keys(TypeMouvement);

  produitsSharedCollection: IProduit[] = [];
  entrepotsSharedCollection: IEntrepot[] = [];

  protected mouvementStockService = inject(MouvementStockService);
  protected mouvementStockFormService = inject(MouvementStockFormService);
  protected produitService = inject(ProduitService);
  protected entrepotService = inject(EntrepotService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: MouvementStockFormGroup = this.mouvementStockFormService.createMouvementStockFormGroup();

  compareProduit = (o1: IProduit | null, o2: IProduit | null): boolean => this.produitService.compareProduit(o1, o2);

  compareEntrepot = (o1: IEntrepot | null, o2: IEntrepot | null): boolean => this.entrepotService.compareEntrepot(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mouvementStock }) => {
      this.mouvementStock = mouvementStock;
      if (mouvementStock) {
        this.updateForm(mouvementStock);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mouvementStock = this.mouvementStockFormService.getMouvementStock(this.editForm);
    if (mouvementStock.id !== null) {
      this.subscribeToSaveResponse(this.mouvementStockService.update(mouvementStock));
    } else {
      this.subscribeToSaveResponse(this.mouvementStockService.create(mouvementStock));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMouvementStock>>): void {
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

  protected updateForm(mouvementStock: IMouvementStock): void {
    this.mouvementStock = mouvementStock;
    this.mouvementStockFormService.resetForm(this.editForm, mouvementStock);

    this.produitsSharedCollection = this.produitService.addProduitToCollectionIfMissing<IProduit>(
      this.produitsSharedCollection,
      mouvementStock.produit,
    );
    this.entrepotsSharedCollection = this.entrepotService.addEntrepotToCollectionIfMissing<IEntrepot>(
      this.entrepotsSharedCollection,
      mouvementStock.entrepot,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.produitService
      .query()
      .pipe(map((res: HttpResponse<IProduit[]>) => res.body ?? []))
      .pipe(
        map((produits: IProduit[]) =>
          this.produitService.addProduitToCollectionIfMissing<IProduit>(produits, this.mouvementStock?.produit),
        ),
      )
      .subscribe((produits: IProduit[]) => (this.produitsSharedCollection = produits));

    this.entrepotService
      .query()
      .pipe(map((res: HttpResponse<IEntrepot[]>) => res.body ?? []))
      .pipe(
        map((entrepots: IEntrepot[]) =>
          this.entrepotService.addEntrepotToCollectionIfMissing<IEntrepot>(entrepots, this.mouvementStock?.entrepot),
        ),
      )
      .subscribe((entrepots: IEntrepot[]) => (this.entrepotsSharedCollection = entrepots));
  }
}
