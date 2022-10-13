import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AirDataDetailsService } from './air-data-details.service';
import { AirData } from "../airdata";
import { formatDate } from '@angular/common';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-air-data-details',
  templateUrl: './air-data-details.component.html',
  styleUrls: ['./air-data-details.component.scss']
})
export class AirDataDetailsComponent implements OnInit {
  public airdatas: AirData[] = [];

  public isDisabled = '';

  public userReported = "";
  public reportedNumber: number = 0;

  constructor(private cookieService: CookieService, private route: ActivatedRoute, private service: AirDataDetailsService) { }

  async ngOnInit(): Promise<void> {
    const ID_FROM_PARAMS: number = this.route.snapshot.params['id'];
    this.service.getAirDataFromIdAndHash(ID_FROM_PARAMS, this.route.snapshot.params['hash']).subscribe(
      (response: AirData) => {
        response.receivedDataDateTime = this.formatDate(new Date(response.receivedDataDateTime))
        this.reportedNumber = response.reportedN;
        this.airdatas.push(response);

        if (this.existsCookie(ID_FROM_PARAMS.toString())) {
          this.isDisabled = 'disabled';
          this.userReported = " Tvoje nahlášení jsme již obdrželi!";
        }
      },
      () => {
        alert('No data available for this input data');
      }
    );
    var counter = 0;
    //400ms to wait | max wait for 5s
    const msToWait = 400, msMaxToWait = 5000;
    while (this.airdatas.length === 0 && counter < msMaxToWait / msToWait) {
      await new Promise(f => setTimeout(f, msToWait!));
      counter++;
    }
  }


  /**
   * @param date Date taken from back-end
   * @returns formatted date as string
   */
  private formatDate(date: Date): string {
    return formatDate(date, 'dd.MM.YYYY v HH:mm:ss', "en-US");
  }

  public reportAirData(id: number) {
    if (this.existsCookie(id.toString()) === false) {
      this.service.reportAirData(id).subscribe(
        (/*success*/) => {
          this.isDisabled = 'disabled'
          this.setCookie(id.toString());
          this.userReported = " Tvoje nahlášení jsme již obdrželi!";
          ++this.reportedNumber;
        }
        ,
        (/*failure*/) => {
          this.isDisabled = ''
        }
      );
    }
    else {
      alert('Tvoje nahlášení jsme již obdrželi!')
    }
  }


  setCookie(cookieName: string) {
    this.cookieService.set(cookieName, 'true');
  }

  getCookie(cookieName: string): string {
    return this.cookieService.get(cookieName);
  }
  existsCookie(cookieName: string): boolean {
    return !((this.getCookie(cookieName) === ""));
  }
  deleteCookie(cookieName: string) {
    this.cookieService.delete(cookieName);
  }

  deleteAll() {
    this.cookieService.deleteAll();
  }

}
