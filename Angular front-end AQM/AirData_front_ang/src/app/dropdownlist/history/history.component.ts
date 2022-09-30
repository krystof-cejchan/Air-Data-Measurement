import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AirDataAverage } from 'src/app/airdata_average';
import { HistoryDataService } from './history-data.service';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {

  public htmlToAdd = '';

  public avgDatas: AirDataAverage[] = [];
  private dateFromParamM: string | undefined | null;
  constructor(
    private route: ActivatedRoute, private service: HistoryDataService
  ) { }

  ngOnInit(): void {

    this.route.url.subscribe(() => {
      if (this.route.snapshot.parent?.paramMap.get('date')) {
        this.dateFromParamM = this.route.snapshot.parent?.paramMap.get('date');
      }
    });

    this.getAvgAirData();
  }

  public async getAvgAirData(): Promise<void> {
    this.service.getHistoryData(this.dateFromParamM!).subscribe(
      (response: AirDataAverage) => {
        if (this.isNull(response) === false) {
          this.avgDatas.push(response);
        } else {
          console.log(response);
          this.htmlToAdd = '<div class="errMsg">Server did not respond succesfully!<br>There is no data for this date</div>';
        }
      },
      (error: HttpErrorResponse) => {
        this.htmlToAdd = '<div class="errMsg">Server did not respond succesfully!<br>' + error.name + '</div>';
      }
    );
  }

  private isNull(airdata_average: AirDataAverage): boolean {
    return (airdata_average.avgAirQuality === null || airdata_average.avgHumidity === null || airdata_average.avgTemperature === null);
  }

}