import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IStructure } from '../structure.model';
import { StructureService } from '../service/structure.service';
import { StructureFormService, StructureFormGroup } from './structure-form.service';

@Component({
  standalone: true,
  selector: 'jhi-structure-update',
  templateUrl: './structure-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class StructureUpdateComponent implements OnInit {
  isSaving = false;
  structure: IStructure | null = null;

  protected structureService = inject(StructureService);
  protected structureFormService = inject(StructureFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: StructureFormGroup = this.structureFormService.createStructureFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ structure }) => {
      this.structure = structure;
      if (structure) {
        this.updateForm(structure);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const structure = this.structureFormService.getStructure(this.editForm);
    if (structure.id !== null) {
      this.subscribeToSaveResponse(this.structureService.update(structure));
    } else {
      this.subscribeToSaveResponse(this.structureService.create(structure));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStructure>>): void {
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

  protected updateForm(structure: IStructure): void {
    this.structure = structure;
    this.structureFormService.resetForm(this.editForm, structure);
  }
}
