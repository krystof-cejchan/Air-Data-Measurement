import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Router } from '@angular/router';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  constructor(private router: Router) {
  }
  ngOnInit(): void {

  }
  getHamburger() {
    return "hamburger_" + (this.router.url === '/' ? 'white' : 'black')
  }
}
