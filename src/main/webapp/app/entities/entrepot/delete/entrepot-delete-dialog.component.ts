import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IEntrepot } from '../entrepot.model';
import { EntrepotService } from '../service/entrepot.service';

@Component({
  standalone: true,
  templateUrl: './entrepot-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class EntrepotDeleteDialogComponent {
  entrepot?: IEntrepot;

  protected entrepotService = inject(EntrepotService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.entrepotService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
