import { Component, OnInit, OnDestroy } from "@angular/core";
import { AirData } from '../objects/airdata';
import { LatestDataService } from '../dropdownlist/latest-data/latest-data.service';
import { DeviceDetectorService } from 'ngx-device-detector';
import { convertCelsiusToFahrenheit, convertFahrenheitToCelsius, round } from '../utilities/utils';
import { openSnackBar } from '../errors/custom in-page errors/snack-bar/server_error/custom-error-snackbar';
import { MatSnackBar } from "@angular/material/snack-bar";
import { SubSink } from "subsink";
import { CookieService } from "ngx-cookie-service";
import { PrefOpt } from "../users-preferences/PrefOpt";


@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.scss']
})
export class MainPageComponent implements OnInit, OnDestroy {
  private subs = new SubSink()
  public current_temperature: number = NaN;
  public temp_sentence_show: boolean = false;

  constructor(private latestDataService: LatestDataService, private deviceService: DeviceDetectorService,
    private snackBar: MatSnackBar, private cookieService: CookieService) {
  }
  ngOnDestroy(): void {
    this.subs.unsubscribe()
  }

  ngOnInit(): void {
    this.getAirDatas();
  }

  public getAirDatas(): void {
    this.subs.add(this.latestDataService.getLatestData().subscribe({
      next: async (response: AirData[]) => {
        var counter = 0;
        const msToWait = 400, msMaxToWait = 5000;
        while (response.length === 0 && counter < (msMaxToWait / msToWait)) {
          await new Promise(f => setTimeout(f, msToWait));
          counter++;
        }
        this.current_temperature = this.round((response
          .map(airData => airData.temperature)
          .reduce((sum, current) => sum + current, 0) / response.length), 1);
      },
      error: () => {
        this.current_temperature = this.round(this.current_temperature, 1);
        openSnackBar(this.snackBar)
      }
    }));
    
    if (!this.isCelsiusPreferred() && !Number.isNaN(this.current_temperature)) {
      //(2°C × 9/5) + 32 = 35.6°F
      this.current_temperature = convertCelsiusToFahrenheit(this.current_temperature);
    }
  }

  getVideoSource(): string {
    if (this.isMobile())
      return "./assets/videos/olomouc-drone-bcg-r-cropped-to-mobile-apect_ratio.webm"
    return "./assets/videos/olomouc bcg s rr.webm"
  }
  getVideoFormat(): string {
    const vs = this.getVideoSource();
    return vs.substring(vs.lastIndexOf('.') + 1)
  }
  private round(value: number, precision: number) {
    this.temp_sentence_show = true;
    return round(value, precision);
  }

  public isMobile(): boolean {
    return this.deviceService.isMobile();
  }

  isVideoPreferred(): boolean {
    const cookieValue = this.getCookieValueFromEnum(PrefOpt.VIDEO_BACKGROUND);
    return (cookieValue === '' || cookieValue === "false");
  }

  isCelsiusPreferred(): boolean {
    const cookieValue = this.getCookieValueFromEnum(PrefOpt.FAHRENHEIT);
    return (cookieValue === '' || cookieValue === "false");
  }
  private getCookieValueFromEnum(prefOptEnum: PrefOpt): string {
    return this.cookieService.get(Object.keys(PrefOpt)[Object.values(PrefOpt).indexOf(prefOptEnum)])
  }

  public openLink(url: string) {
    window.open(url)
  }

  public getCurrYear(): number {
    return new Date().getFullYear();
  }

  public getColourBasedOnTemp(): string {
    const snapshotOfTemp = this.isCelsiusPreferred() ? this.current_temperature : convertFahrenheitToCelsius(this.current_temperature);
    if (snapshotOfTemp <= -15)
      return "#535eba"
    // 1
    if (snapshotOfTemp <= -10)
      return "#187ac3"
    // 2
    if (snapshotOfTemp <= 0)
      return "#1cb3d0";
    // 3
    if (snapshotOfTemp <= 10)
      return "#25d3f5";
    // 4
    if (snapshotOfTemp <= 15)
      return "#fdf121";
    // 5
    if (snapshotOfTemp <= 20)
      return "#facd32";
    // 6
    if (snapshotOfTemp <= 25)
      return "#f6ac43";
    // 7
    if (snapshotOfTemp <= 30)
      return "#fb7832"
    // 8
    if (snapshotOfTemp <= 35)
      return "#ff4929";
    // 9
    if (snapshotOfTemp > 35)
      return "#d2449a";
    else return "black";
  }
}
