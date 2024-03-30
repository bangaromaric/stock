import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPlansAbonnement } from '../plans-abonnement.model';
import { PlansAbonnementService } from '../service/plans-abonnement.service';

@Component({
  standalone: true,
  templateUrl: './plans-abonnement-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PlansAbonnementDeleteDialogComponent {
  plansAbonnement?: IPlansAbonnement;

  protected plansAbonnementService = inject(PlansAbonnementService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.plansAbonnementService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
