import { Component, OnInit, OnDestroy } from "@angular/core";
import { AirData } from '../objects/airdata';
import { LatestDataService } from '../dropdownlist/latest-data/latest-data.service';
import { DeviceDetectorService } from 'ngx-device-detector';
import { round } from '../utilities/utils';
import { openSnackBar } from '../errors/custom in-page errors/snack-bar/server_error/custom-error-snackbar';
import { MatSnackBar } from "@angular/material/snack-bar";
import { SubSink } from "subsink";


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
    private snackBar: MatSnackBar) {
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

  public openLink(url: string) {
    window.open(url)
  }

  public getCurrYear(): number {
    return new Date().getFullYear();
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
}
