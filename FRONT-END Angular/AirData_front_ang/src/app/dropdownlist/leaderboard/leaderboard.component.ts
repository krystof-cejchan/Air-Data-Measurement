import { formatDate } from '@angular/common';
import { Component, OnInit, OnDestroy } from "@angular/core";
import { ActivatedRoute, Router } from '@angular/router';
import { PrePreparedLeaderboardData } from 'src/app/objects/LeaderboardDataForHtml';
import { LeaderboardData } from "../../objects/Leaderboard";
import { LeaderboardService } from "./leaderboard.service";
import { LeaderboardDataExtended } from "../../objects/LeaderboardDataExtended";
import { round } from 'src/app/utilities/utils';
import { MatSnackBar } from "@angular/material/snack-bar";
import { openSnackBar } from 'src/app/errors/custom in-page errors/snack-bar/server_error/custom-error-snackbar';
import { SubSink } from "subsink";

export interface leaderboardDifferenceData {
  value: number,
  PN0: 'positive' | 'negative' | 'zero';
}

@Component({
  selector: 'app-leaderboard',
  templateUrl: './leaderboard.component.html',
  styleUrls: ['./leaderboard.component.scss']
})

export class LeaderboardComponent implements OnInit, IComponent, OnDestroy {
  private subs = new SubSink()
  public leaderboard_datas?: LeaderboardData[] = undefined;
  public leaderboard_data_preparedExtended: LeaderboardDataExtended[] = [];
  public leaderboard_data_prepared: PrePreparedLeaderboardData[] = [];
  public showLoading: boolean = true;

  constructor(private service: LeaderboardService, private router: Router, private route: ActivatedRoute, private snackBar: MatSnackBar) { }
  ngOnDestroy(): void {
    this.subs.unsubscribe()
  }

  getTitle(): string {
    return "Žebříčky";
  }

  ngOnInit(): void {
    this.subs.add(this.service.getAllLeaderboardData().subscribe({
      next: async (response: LeaderboardData[]) => {

        this.leaderboard_datas = response;
        var counter = 0;
        const msToWait = 400, msMaxToWait = 5000;
        while (this.leaderboard_datas?.length === 0 && counter < (msMaxToWait / msToWait)) {
          await new Promise(f => setTimeout(f, msToWait));
          counter++;
        }

        this.leaderboard_datas!
          .map(data => data.leaderboardType)
          .filter((value: string, index: number, array: string[]) => array.indexOf(value) === index)
          .sort((t1, t2) => t1.substring(t1.indexOf('_')).localeCompare(t2.substring(t2.indexOf('_'))))
          .forEach(lType => {
            this.leaderboard_data_prepared
              .push({
                type: lType, leaderboardData: this.leaderboard_datas!.filter(data => data.leaderboardType === lType)
                  .sort((a, b) => a.position - b.position)
              } as PrePreparedLeaderboardData);
          });

        this.leaderboard_data_prepared.forEach(data => {
          this.leaderboard_data_preparedExtended.push({
            type: data.type.substring(data.type.indexOf('_') + 1) as string,
            mapSubTypeToLeaderboardData: new Map<string, LeaderboardData[]>().set(data.type, data.leaderboardData.sort((A, B) => A.position - B.position))
          })
        });
      },
      error: () => {
        openSnackBar(this.snackBar)
      },
      complete: () => this.showLoading = false
    }));
  }

  public getUniqueTypesFrom__leaderboard_data_preparedExtended(): string[] {
    return this.leaderboard_data_preparedExtended.sort((a, b) => b.type.localeCompare(a.type))
      .map(list => list.type)
      .filter((item, i, ar) => ar.indexOf(item) === i);
  }

  public getModified__leaderboard_data_preparedExtended(type: string | 'AIRQ' | 'HUM' | 'TEMP'): Map<string, LeaderboardData[]>[] {
    return this.leaderboard_data_preparedExtended.filter(leaderboard_data => leaderboard_data.type === type)
      .map(m => m.mapSubTypeToLeaderboardData)
  }

  /**
   * 2022-11-08T13:18:55.01 to dd.MM.YYYY as string, not date
   * @param longDate long date with day and time
   * @returns short date as dd.MM.YYYY
   */
  public shortenDate(longDate: string): string {
    return formatDate(longDate, 'dd.MM.YYYY', "en-US");
  }

  /**
   * navigates a user to a new route showing detailed info about airdataInfo
   * @param airdataInfo aid data consisting of id and hash code - these data are used to get a record from database matching the id and hash
   */
  public showDetails(airdataInfo: { id: number, hash: string }) {
    this.router.navigate([`/data-detaily/mereni/${airdataInfo.id}/${airdataInfo.hash}`], { relativeTo: this.route });
  }

  private generateValueInMap(conf: { position: number, leaderboardType: string }, previous: LeaderboardData, behind: LeaderboardData): leaderboardDifferenceData {
    if (conf.position <= 1) {
      return { value: 0, PN0: 'zero' };
    }

    switch (conf.leaderboardType.substring(conf.leaderboardType.indexOf('_') + 1, conf.leaderboardType.length)) {
      case "AIRQ": {
        const roundedValue = round((behind.airDataId.airQuality - previous.airDataId.airQuality), 2);
        return { value: roundedValue, PN0: (roundedValue === 0 ? 'zero' : (roundedValue < 0 ? 'negative' : 'positive')) }
      }
      case "TEMP": {
        const roundedValue = round((behind.airDataId.temperature - previous.airDataId.temperature), 2);
        return { value: roundedValue, PN0: (roundedValue === 0 ? 'zero' : (roundedValue < 0 ? 'negative' : 'positive')) }
      }
      case "HUM": {
        const roundedValue = round((behind.airDataId.humidity - previous.airDataId.humidity), 2);
        return { value: roundedValue, PN0: (roundedValue === 0 ? 'zero' : (roundedValue < 0 ? 'negative' : 'positive')) }
      }
      default:
        return { value: NaN, PN0: 'zero' };
    }
  }

  /**
   * calculates the difference between airData values in leaderboard_data_preparedExtended 
   * @param conf position of the LeaderboardData to be calculated(1,2,3); leaderboardType of the LeaderboardData to be calculated(HIGHEST_AIRQ, lOWEST_HUM etc.)
   * @param fromFirstOne true->the difference is canculated from the first data; false->the difference is calculated from the previous data
   * @returns number value of the calculated difference
   */
  public getDifferenceFromMap(conf: { position: number, leaderboardType: string }, fromFirstOne: boolean): leaderboardDifferenceData {

    if (conf.position <= 1) {
      return { value: 0, PN0: 'zero' };
    }

    const arrOfLeaderboardDataFilteredToMatchLeaderboardType = this.leaderboard_data_preparedExtended
      .map(it => it.mapSubTypeToLeaderboardData).filter(it => it.has(conf.leaderboardType)).map(it => it.get(conf.leaderboardType))[0];
    const calculatedDiff = (arrOfLeaderboardDataFilteredToMatchLeaderboardType ?
      (this.generateValueInMap(conf, arrOfLeaderboardDataFilteredToMatchLeaderboardType
        .filter(it => it.position === (fromFirstOne ? 1 : conf.position - 1))[0], arrOfLeaderboardDataFilteredToMatchLeaderboardType
          .filter(it => it.position === conf.position)[0]))
      : {
        value: NaN, PN0: 'zero'
      } as leaderboardDifferenceData);
    return calculatedDiff;
  }
}
