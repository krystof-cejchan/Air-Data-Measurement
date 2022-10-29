import { Component, OnInit } from '@angular/core';
import { LocalStoreService } from "./local-store.service";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  private locations: string[] = [];
  private leaderboard: string[] = [];

  constructor(private localStore: LocalStoreService) {

  }

  async ngOnInit(): Promise<void> {
    this.localStore.clearData();
    this.localStore.getAllLocations().subscribe(
      (response: string[]) => {
        this.locations = response;
      }
    );
    this.localStore.getAllLeaderboard().subscribe(
      (response: string[]) => {
        this.leaderboard = response;
      }
    );


    var counter = 0;
    const msToWait = 400, msMaxToWait = 5000;
    while ((this.locations.length === 0 && this.leaderboard.length === 0) && counter < (msMaxToWait / msToWait)) {
      await new Promise(f => setTimeout(f, msToWait));
      counter++;
    }
    this.localStore.saveData('locations', this.locations.join(';'));
    this.localStore.saveData('leaderboard', this.leaderboard.join(';'));

    // console.log(this.localStore.getData('locations'));

  }
}
