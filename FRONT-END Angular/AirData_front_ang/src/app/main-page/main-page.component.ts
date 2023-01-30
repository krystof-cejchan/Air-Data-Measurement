import { Component, HostListener, OnInit } from '@angular/core';
import { AirData } from '../airdata';
import { LatestDataService } from '../dropdownlist/latest-data/latest-data.service';
import { DeviceDetectorService } from 'ngx-device-detector';
import { round } from '../utilities/utils';
import { openSnackBar } from '../errors/custom in-page errors/snack-bar/custom-error-snackbar';
import { MatSnackBar } from "@angular/material/snack-bar";


@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.scss']
})
export class MainPageComponent implements OnInit {
  public current_temperature: number = NaN;
  public temp_sentence_show: boolean = false;

  constructor(private latestDataService: LatestDataService, private deviceService: DeviceDetectorService,
    private snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.getAirDatas();
  }

  public getAirDatas(): void {
    this.latestDataService.getLatestData().subscribe(
      async (response: AirData[]) => {
        var counter = 0;
        const msToWait = 400, msMaxToWait = 5000;
        while (response.length === 0 && counter < (msMaxToWait / msToWait)) {
          await new Promise(f => setTimeout(f, msToWait));
          counter++;
        }
        this.current_temperature = this.round((response
          .map(airData => airData.temperature)
          .reduce((sum, current) => sum + current, 0) / response.length), 1);
      }, () => {
        this.current_temperature = this.round(this.current_temperature, 1);
        openSnackBar(this.snackBar)
      }
    );
  }
  public getColourBasedOnTemp(): string {
    if (this.current_temperature <= -15)
      return "#535eba"
    // 1
    if (this.current_temperature <= -10)
      return "#187ac3"
    // 2
    if (this.current_temperature <= 0)
      return "#1cb3d0";
    // 3
    if (this.current_temperature <= 10)
      return "#25d3f5";
    // 4
    if (this.current_temperature <= 15)
      return "#fdf121";
    // 5
    if (this.current_temperature <= 20)
      return "#facd32";
    // 6
    if (this.current_temperature <= 25)
      return "#f6ac43";
    // 7
    if (this.current_temperature <= 30)
      return "#fb7832"
    // 8
    if (this.current_temperature <= 35)
      return "#ff4929";
    // 9
    if (this.current_temperature > 35)
      return "#d2449a";
    else return "black";
  }
  private round(value: number, precision: number) {
    this.temp_sentence_show = true;
    return round(value, precision);
  }

  /*@HostListener('window:resize', ['$event'])
  onResize(event: any) {
    this.isMobile();
  }*/

  public isMobile(): boolean {
    // console.log(this.deviceService.isMobile());

    return this.deviceService.isMobile();
  }

  public openLink(url: string) {
    window.open(url)
  }

  public getCurrYear(): number {
    return new Date().getFullYear();
  }
}
