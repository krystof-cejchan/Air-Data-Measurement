import { Component, OnInit } from '@angular/core';
import { AirData } from '../airdata';
import { LatestDataService } from '../dropdownlist/latest-data/latest-data.service';
import { MainPageService } from './main-page.service';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.scss']
})
export class MainPageComponent implements OnInit {
  public current_temperature: number = NaN;
  constructor(private latestDataService: LatestDataService) { }

  ngOnInit(): void {
    this.getAirDatas();
  }
  public getAirDatas(): void {
    this.latestDataService.getLatestData().subscribe(
      async (response: AirData[]) => {
        console.log(response)
        var counter = 0;
        const msToWait = 400, msMaxToWait = 5000;
        while (response.length === 0 && counter < (msMaxToWait / msToWait)) {
          await new Promise(f => setTimeout(f, msToWait));
          counter++;
        }
        this.current_temperature = this.round((response.map(airData => airData.temperature).reduce((sum, current) => sum + current, 0) / response.length), 1);
      }, () => {
        this.current_temperature = this.round(this.current_temperature, 1);
      }
    );
   /* this.mainPageService.getAverageTemperatureFromLatestData().subscribe(
      async (response: Number) => {

        var counter = 0;
        const msToWait = 400, msMaxToWait = 5000;
        while (!response && counter < (msMaxToWait / msToWait)) {
          await new Promise(f => setTimeout(f, msToWait));
          counter++;
        }
        this.current_temperature = this.round(response.valueOf(), 1);
      }, () => {
        this.current_temperature = this.round(this.current_temperature, 1);
      }
    );*/
  }
  private round(value: number, precision: number) {
    var multiplier = Math.pow(10, precision || 0);
    return Math.round(value * multiplier) / multiplier;
  }
}
