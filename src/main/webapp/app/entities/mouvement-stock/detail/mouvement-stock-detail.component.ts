import { Component, Input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IMouvementStock } from '../mouvement-stock.model';

@Component({
  standalone: true,
  selector: 'jhi-mouvement-stock-detail',
  templateUrl: './mouvement-stock-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class MouvementStockDetailComponent {
  @Input() mouvementStock: IMouvementStock | null = null;

  previousState(): void {
    window.history.back();
  }
}