import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IStructure } from 'app/entities/structure/structure.model';
import { StructureService } from 'app/entities/structure/service/structure.service';
import { IPlansAbonnement } from 'app/entities/plans-abonnement/plans-abonnement.model';
import { PlansAbonnementService } from 'app/entities/plans-abonnement/service/plans-abonnement.service';
import { IPaiement } from 'app/entities/paiement/paiement.model';
import { PaiementService } from 'app/entities/paiement/service/paiement.service';
import { StatutAbonnement } from 'app/entities/enumerations/statut-abonnement.model';
import { AbonnementService } from '../service/abonnement.service';
import { IAbonnement } from '../abonnement.model';
import { AbonnementFormService, AbonnementFormGroup } from './abonnement-form.service';

@Component({
  standalone: true,
  selector: 'jhi-abonnement-update',
  templateUrl: './abonnement-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AbonnementUpdateComponent implements OnInit {
  isSaving = false;
  abonnement: IAbonnement | null = null;
  statutAbonnementValues = Object.keys(StatutAbonnement);

  structuresSharedCollection: IStructure[] = [];
  plansAbonnementsSharedCollection: IPlansAbonnement[] = [];
  paiementsSharedCollection: IPaiement[] = [];

  protected abonnementService = inject(AbonnementService);
  protected abonnementFormService = inject(AbonnementFormService);
  protected structureService = inject(StructureService);
  protected plansAbonnementService = inject(PlansAbonnementService);
  protected paiementService = inject(PaiementService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AbonnementFormGroup = this.abonnementFormService.createAbonnementFormGroup();

  compareStructure = (o1: IStructure | null, o2: IStructure | null): boolean => this.structureService.compareStructure(o1, o2);

  comparePlansAbonnement = (o1: IPlansAbonnement | null, o2: IPlansAbonnement | null): boolean =>
    this.plansAbonnementService.comparePlansAbonnement(o1, o2);

  comparePaiement = (o1: IPaiement | null, o2: IPaiement | null): boolean => this.paiementService.comparePaiement(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ abonnement }) => {
      this.abonnement = abonnement;
      if (abonnement) {
        this.updateForm(abonnement);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const abonnement = this.abonnementFormService.getAbonnement(this.editForm);
    if (abonnement.id !== null) {
      this.subscribeToSaveResponse(this.abonnementService.update(abonnement));
    } else {
      this.subscribeToSaveResponse(this.abonnementService.create(abonnement));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAbonnement>>): void {
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

  protected updateForm(abonnement: IAbonnement): void {
    this.abonnement = abonnement;
    this.abonnementFormService.resetForm(this.editForm, abonnement);

    this.structuresSharedCollection = this.structureService.addStructureToCollectionIfMissing<IStructure>(
      this.structuresSharedCollection,
      abonnement.structure,
    );
    this.plansAbonnementsSharedCollection = this.plansAbonnementService.addPlansAbonnementToCollectionIfMissing<IPlansAbonnement>(
      this.plansAbonnementsSharedCollection,
      abonnement.plansAbonnement,
    );
    this.paiementsSharedCollection = this.paiementService.addPaiementToCollectionIfMissing<IPaiement>(
      this.paiementsSharedCollection,
      abonnement.paiement,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.structureService
      .query()
      .pipe(map((res: HttpResponse<IStructure[]>) => res.body ?? []))
      .pipe(
        map((structures: IStructure[]) =>
          this.structureService.addStructureToCollectionIfMissing<IStructure>(structures, this.abonnement?.structure),
        ),
      )
      .subscribe((structures: IStructure[]) => (this.structuresSharedCollection = structures));

    this.plansAbonnementService
      .query()
      .pipe(map((res: HttpResponse<IPlansAbonnement[]>) => res.body ?? []))
      .pipe(
        map((plansAbonnements: IPlansAbonnement[]) =>
          this.plansAbonnementService.addPlansAbonnementToCollectionIfMissing<IPlansAbonnement>(
            plansAbonnements,
            this.abonnement?.plansAbonnement,
          ),
        ),
      )
      .subscribe((plansAbonnements: IPlansAbonnement[]) => (this.plansAbonnementsSharedCollection = plansAbonnements));

    this.paiementService
      .query()
      .pipe(map((res: HttpResponse<IPaiement[]>) => res.body ?? []))
      .pipe(
        map((paiements: IPaiement[]) =>
          this.paiementService.addPaiementToCollectionIfMissing<IPaiement>(paiements, this.abonnement?.paiement),
        ),
      )
      .subscribe((paiements: IPaiement[]) => (this.paiementsSharedCollection = paiements));
  }
}
