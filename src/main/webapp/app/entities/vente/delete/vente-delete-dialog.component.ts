import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IVente } from '../vente.model';
import { VenteService } from '../service/vente.service';

@Component({
  standalone: true,
  templateUrl: './vente-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class VenteDeleteDialogComponent {
  vente?: IVente;

  protected venteService = inject(VenteService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.venteService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
