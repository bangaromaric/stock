import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { PlansAbonnementService } from '../service/plans-abonnement.service';
import { IPlansAbonnement } from '../plans-abonnement.model';
import { PlansAbonnementFormService, PlansAbonnementFormGroup } from './plans-abonnement-form.service';

@Component({
  standalone: true,
  selector: 'jhi-plans-abonnement-update',
  templateUrl: './plans-abonnement-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PlansAbonnementUpdateComponent implements OnInit {
  isSaving = false;
  plansAbonnement: IPlansAbonnement | null = null;

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected plansAbonnementService = inject(PlansAbonnementService);
  protected plansAbonnementFormService = inject(PlansAbonnementFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PlansAbonnementFormGroup = this.plansAbonnementFormService.createPlansAbonnementFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ plansAbonnement }) => {
      this.plansAbonnement = plansAbonnement;
      if (plansAbonnement) {
        this.updateForm(plansAbonnement);
      }
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('stockApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const plansAbonnement = this.plansAbonnementFormService.getPlansAbonnement(this.editForm);
    if (plansAbonnement.id !== null) {
      this.subscribeToSaveResponse(this.plansAbonnementService.update(plansAbonnement));
    } else {
      this.subscribeToSaveResponse(this.plansAbonnementService.create(plansAbonnement));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlansAbonnement>>): void {
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

  protected updateForm(plansAbonnement: IPlansAbonnement): void {
    this.plansAbonnement = plansAbonnement;
    this.plansAbonnementFormService.resetForm(this.editForm, plansAbonnement);
  }
}
