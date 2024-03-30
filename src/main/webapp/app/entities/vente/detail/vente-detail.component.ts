import { Component, Input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IVente } from '../vente.model';

@Component({
  standalone: true,
  selector: 'jhi-vente-detail',
  templateUrl: './vente-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class VenteDetailComponent {
  @Input() vente: IVente | null = null;

  previousState(): void {
    window.history.back();
  }
}
