import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IStructure } from '../structure.model';
import { StructureService } from '../service/structure.service';

@Component({
  standalone: true,
  templateUrl: './structure-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class StructureDeleteDialogComponent {
  structure?: IStructure;

  protected structureService = inject(StructureService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.structureService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
