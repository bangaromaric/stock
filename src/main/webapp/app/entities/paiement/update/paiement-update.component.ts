import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPlansAbonnement } from 'app/entities/plans-abonnement/plans-abonnement.model';
import { PlansAbonnementService } from 'app/entities/plans-abonnement/service/plans-abonnement.service';
import { MethodePaiement } from 'app/entities/enumerations/methode-paiement.model';
import { StatutPaiement } from 'app/entities/enumerations/statut-paiement.model';
import { PaiementService } from '../service/paiement.service';
import { IPaiement } from '../paiement.model';
import { PaiementFormService, PaiementFormGroup } from './paiement-form.service';

@Component({
  standalone: true,
  selector: 'jhi-paiement-update',
  templateUrl: './paiement-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PaiementUpdateComponent implements OnInit {
  isSaving = false;
  paiement: IPaiement | null = null;
  methodePaiementValues = Object.keys(MethodePaiement);
  statutPaiementValues = Object.keys(StatutPaiement);

  plansAbonnementsSharedCollection: IPlansAbonnement[] = [];

  protected paiementService = inject(PaiementService);
  protected paiementFormService = inject(PaiementFormService);
  protected plansAbonnementService = inject(PlansAbonnementService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PaiementFormGroup = this.paiementFormService.createPaiementFormGroup();

  comparePlansAbonnement = (o1: IPlansAbonnement | null, o2: IPlansAbonnement | null): boolean =>
    this.plansAbonnementService.comparePlansAbonnement(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paiement }) => {
      this.paiement = paiement;
      if (paiement) {
        this.updateForm(paiement);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paiement = this.paiementFormService.getPaiement(this.editForm);
    if (paiement.id !== null) {
      this.subscribeToSaveResponse(this.paiementService.update(paiement));
    } else {
      this.subscribeToSaveResponse(this.paiementService.create(paiement));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaiement>>): void {
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

  protected updateForm(paiement: IPaiement): void {
    this.paiement = paiement;
    this.paiementFormService.resetForm(this.editForm, paiement);

    this.plansAbonnementsSharedCollection = this.plansAbonnementService.addPlansAbonnementToCollectionIfMissing<IPlansAbonnement>(
      this.plansAbonnementsSharedCollection,
      paiement.plansAbonnement,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.plansAbonnementService
      .query()
      .pipe(map((res: HttpResponse<IPlansAbonnement[]>) => res.body ?? []))
      .pipe(
        map((plansAbonnements: IPlansAbonnement[]) =>
          this.plansAbonnementService.addPlansAbonnementToCollectionIfMissing<IPlansAbonnement>(
            plansAbonnements,
            this.paiement?.plansAbonnement,
          ),
        ),
      )
      .subscribe((plansAbonnements: IPlansAbonnement[]) => (this.plansAbonnementsSharedCollection = plansAbonnements));
  }
}
