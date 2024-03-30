import { Component, Input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IStructure } from '../structure.model';

@Component({
  standalone: true,
  selector: 'jhi-structure-detail',
  templateUrl: './structure-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class StructureDetailComponent {
  @Input() structure: IStructure | null = null;

  previousState(): void {
    window.history.back();
  }
}
