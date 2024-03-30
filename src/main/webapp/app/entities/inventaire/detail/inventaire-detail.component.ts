import { Component, Input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IInventaire } from '../inventaire.model';

@Component({
  standalone: true,
  selector: 'jhi-inventaire-detail',
  templateUrl: './inventaire-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class InventaireDetailComponent {
  @Input() inventaire: IInventaire | null = null;

  previousState(): void {
    window.history.back();
  }
}
