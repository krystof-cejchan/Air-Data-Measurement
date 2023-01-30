import { Component } from '@angular/core';

@Component({
  selector: 'app-dev',
  templateUrl: './dev.component.html',
  styleUrls: ['./dev.component.scss']
})
export class DevComponent implements IComponent {
  getTitle(): string {
    return "Pro vývojáře a Dokumentace"
  }

}
