import { Component, Input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IStock } from '../stock.model';

@Component({
  standalone: true,
  selector: 'jhi-stock-detail',
  templateUrl: './stock-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class StockDetailComponent {
  @Input() stock: IStock | null = null;

  previousState(): void {
    window.history.back();
  }
}