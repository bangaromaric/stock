import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IStructure } from 'app/entities/structure/structure.model';
import { StructureService } from 'app/entities/structure/service/structure.service';
import { EntrepotService } from '../service/entrepot.service';
import { IEntrepot } from '../entrepot.model';
import { EntrepotFormService, EntrepotFormGroup } from './entrepot-form.service';

@Component({
  standalone: true,
  selector: 'jhi-entrepot-update',
  templateUrl: './entrepot-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EntrepotUpdateComponent implements OnInit {
  isSaving = false;
  entrepot: IEntrepot | null = null;

  structuresSharedCollection: IStructure[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected entrepotService = inject(EntrepotService);
  protected entrepotFormService = inject(EntrepotFormService);
  protected structureService = inject(StructureService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: EntrepotFormGroup = this.entrepotFormService.createEntrepotFormGroup();

  compareStructure = (o1: IStructure | null, o2: IStructure | null): boolean => this.structureService.compareStructure(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ entrepot }) => {
      this.entrepot = entrepot;
      if (entrepot) {
        this.updateForm(entrepot);
      }

      this.loadRelationshipsOptions();
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
    const entrepot = this.entrepotFormService.getEntrepot(this.editForm);
    if (entrepot.id !== null) {
      this.subscribeToSaveResponse(this.entrepotService.update(entrepot));
    } else {
      this.subscribeToSaveResponse(this.entrepotService.create(entrepot));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEntrepot>>): void {
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

  protected updateForm(entrepot: IEntrepot): void {
    this.entrepot = entrepot;
    this.entrepotFormService.resetForm(this.editForm, entrepot);

    this.structuresSharedCollection = this.structureService.addStructureToCollectionIfMissing<IStructure>(
      this.structuresSharedCollection,
      entrepot.structure,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.structureService
      .query()
      .pipe(map((res: HttpResponse<IStructure[]>) => res.body ?? []))
      .pipe(
        map((structures: IStructure[]) =>
          this.structureService.addStructureToCollectionIfMissing<IStructure>(structures, this.entrepot?.structure),
        ),
      )
      .subscribe((structures: IStructure[]) => (this.structuresSharedCollection = structures));
  }
}
