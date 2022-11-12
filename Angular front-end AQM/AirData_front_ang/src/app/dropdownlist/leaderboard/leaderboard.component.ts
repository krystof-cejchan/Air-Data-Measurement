import { formatDate } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PrePreparedLeaderboardData } from 'src/app/objects/LeaderboardDataForHtml';
import { LeaderboardData } from "../../objects/Leaderboard";
import { LatestDataComponent } from '../latest-data/latest-data.component';
import { LeaderboardService } from "./leaderboard.service";

@Component({
  selector: 'app-leaderboard',
  templateUrl: './leaderboard.component.html',
  styleUrls: ['./leaderboard.component.scss']
})
export class LeaderboardComponent implements OnInit {
  public leaderboard_datas?: LeaderboardData[] = undefined;
  public leaderboard_types?: string[] = undefined;
  public leaderboard_data_prepared: PrePreparedLeaderboardData[] = [];
  public showLoading: boolean = true;
  constructor(private service: LeaderboardService, private router: Router,private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.service.getAllLeaderboardData().subscribe(async (response: LeaderboardData[]) => {

      this.leaderboard_datas = response;
      var counter = 0;
      const msToWait = 400, msMaxToWait = 5000;
      while (this.leaderboard_datas?.length === 0 && counter < (msMaxToWait / msToWait)) {
        await new Promise(f => setTimeout(f, msToWait));
        counter++;
      }
      this.showLoading = false;

      this.leaderboard_datas!
        .map(data => data.leaderboardType)
        .filter((value: string, index: number, array: string[]) => array.indexOf(value) === index)
        .sort((t1, t2) => t1.substring(t1.indexOf('_')).localeCompare(t2.substring(t2.indexOf('_'))))
        .forEach(lType => {
          this.leaderboard_data_prepared
            .push({ type: lType, leaderboardData: this.leaderboard_datas?.filter(data => data.leaderboardType === lType) } as PrePreparedLeaderboardData);
        });
    },
      () => {
        this.showLoading = false;
      });
  }

  /**
   * 2022-11-08T13:18:55.01 to dd.MM.YYYY as string, not date
   * @param longDate long date with day and time
   * @returns short date as dd.MM.YYYY
   */
  public shortenDate(longDate: string): string {
    return formatDate(longDate, 'dd.MM.YYYY', "en-US");
  }

  public showDetails(airdataInfo: { id: number, hash: string }) {
    this.router.navigate([`/data-detaily/${airdataInfo.id}/${airdataInfo.hash}`], { relativeTo: this.route });
  }
}
