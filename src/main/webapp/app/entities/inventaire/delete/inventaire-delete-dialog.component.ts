import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IInventaire } from '../inventaire.model';
import { InventaireService } from '../service/inventaire.service';

@Component({
  standalone: true,
  templateUrl: './inventaire-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class InventaireDeleteDialogComponent {
  inventaire?: IInventaire;

  protected inventaireService = inject(InventaireService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.inventaireService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
