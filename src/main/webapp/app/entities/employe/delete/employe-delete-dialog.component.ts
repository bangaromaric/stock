import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IEmploye } from '../employe.model';
import { EmployeService } from '../service/employe.service';

@Component({
  standalone: true,
  templateUrl: './employe-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class EmployeDeleteDialogComponent {
  employe?: IEmploye;

  protected employeService = inject(EmployeService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.employeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
