import { Component, OnInit } from '@angular/core';
import { LeaderboardData } from "../../objects/Leaderboard";
import { LeaderboardService } from "./leaderboard.service";

@Component({
  selector: 'app-leaderboard',
  templateUrl: './leaderboard.component.html',
  styleUrls: ['./leaderboard.component.scss']
})
export class LeaderboardComponent implements OnInit {
  public leaderboard_datas?: LeaderboardData[] = undefined;
  public showLoading: boolean = true;
  constructor(private service: LeaderboardService) { }

  ngOnInit(): void {
    this.service.getAllLeaderboardData().subscribe(async (response: LeaderboardData[]) => {
      //  console.log(response)
      this.leaderboard_datas = response;
      var counter = 0;
      const msToWait = 400, msMaxToWait = 5000;
      while (this.leaderboard_datas?.length === 0 && counter < (msMaxToWait / msToWait)) {
        await new Promise(f => setTimeout(f, msToWait));
        counter++;
      }
      this.showLoading = false;
      // console.log(this.leaderboard_datas)
    },
      () => {
        this.showLoading = false;
      });
  }
}
