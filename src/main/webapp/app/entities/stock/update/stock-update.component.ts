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
import { StockService } from '../service/stock.service';
import { IStock } from '../stock.model';
import { StockFormService, StockFormGroup } from './stock-form.service';

@Component({
  standalone: true,
  selector: 'jhi-stock-update',
  templateUrl: './stock-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class StockUpdateComponent implements OnInit {
  isSaving = false;
  stock: IStock | null = null;

  entrepotsSharedCollection: IEntrepot[] = [];
  produitsSharedCollection: IProduit[] = [];

  protected stockService = inject(StockService);
  protected stockFormService = inject(StockFormService);
  protected entrepotService = inject(EntrepotService);
  protected produitService = inject(ProduitService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: StockFormGroup = this.stockFormService.createStockFormGroup();

  compareEntrepot = (o1: IEntrepot | null, o2: IEntrepot | null): boolean => this.entrepotService.compareEntrepot(o1, o2);

  compareProduit = (o1: IProduit | null, o2: IProduit | null): boolean => this.produitService.compareProduit(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ stock }) => {
      this.stock = stock;
      if (stock) {
        this.updateForm(stock);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const stock = this.stockFormService.getStock(this.editForm);
    if (stock.id !== null) {
      this.subscribeToSaveResponse(this.stockService.update(stock));
    } else {
      this.subscribeToSaveResponse(this.stockService.create(stock));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStock>>): void {
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

  protected updateForm(stock: IStock): void {
    this.stock = stock;
    this.stockFormService.resetForm(this.editForm, stock);

    this.entrepotsSharedCollection = this.entrepotService.addEntrepotToCollectionIfMissing<IEntrepot>(
      this.entrepotsSharedCollection,
      stock.entrepot,
    );
    this.produitsSharedCollection = this.produitService.addProduitToCollectionIfMissing<IProduit>(
      this.produitsSharedCollection,
      stock.produit,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.entrepotService
      .query()
      .pipe(map((res: HttpResponse<IEntrepot[]>) => res.body ?? []))
      .pipe(
        map((entrepots: IEntrepot[]) => this.entrepotService.addEntrepotToCollectionIfMissing<IEntrepot>(entrepots, this.stock?.entrepot)),
      )
      .subscribe((entrepots: IEntrepot[]) => (this.entrepotsSharedCollection = entrepots));

    this.produitService
      .query()
      .pipe(map((res: HttpResponse<IProduit[]>) => res.body ?? []))
      .pipe(map((produits: IProduit[]) => this.produitService.addProduitToCollectionIfMissing<IProduit>(produits, this.stock?.produit)))
      .subscribe((produits: IProduit[]) => (this.produitsSharedCollection = produits));
  }
}
