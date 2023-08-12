import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-no-content',
  templateUrl: './no-content.component.html',
  styleUrls: ['./no-content.component.sass'],
})
export class NoContentComponent {
  @Input() hasCard!: boolean;
}
