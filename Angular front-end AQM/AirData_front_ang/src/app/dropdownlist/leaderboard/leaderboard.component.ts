import { Component, OnInit } from '@angular/core';
import { AirData } from 'src/app/airdata';
import { LeaderboardData } from "../../objects/Leaderboard";
import { LeaderboardService } from "./leaderboard.service";

@Component({
  selector: 'app-leaderboard',
  templateUrl: './leaderboard.component.html',
  styleUrls: ['./leaderboard.component.scss']
})
export class LeaderboardComponent implements OnInit {
  public leaderboard_data?: LeaderboardData = undefined;
  public LeaderboardData_string: string = "";
  public showLoading: boolean = true;
  constructor(private service: LeaderboardService) { }

  ngOnInit(): void {
    this.service.getAllLeaderboardData().subscribe(async (response: LeaderboardData) => {
      this.leaderboard_data = response;
      var counter = 0;
      const msToWait = 400, msMaxToWait = 5000;
      while (!this.leaderboard_data.allData && counter < (msMaxToWait / msToWait)) {
        await new Promise(f => setTimeout(f, msToWait));
        counter++;
      }
      let mapping = new Map<string, string>();
      mapping.set("FF", "LOWEST_AIRQ");

      console.log(this.leaderboard_data.allData.entries());

      this.showLoading = false;
      this.LeaderboardData_string = this.leaderboard_data.toString();
    },
      () => {
        this.showLoading = false;
      });
  }

}
