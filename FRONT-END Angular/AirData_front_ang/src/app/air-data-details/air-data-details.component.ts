import { Component, ElementRef, OnInit, OnDestroy } from "@angular/core";
import { ActivatedRoute } from '@angular/router';
import { AirDataDetailsService } from './air-data-details.service';
import { AirData } from "../airdata";
import { formatDate } from '@angular/common';
import { CookieService } from 'ngx-cookie-service';
import { LocationData } from '../objects/LocationData';
import { openSnackBarCannotReport } from '../errors/custom in-page errors/snack-bar/cannot_report_airdata/custom-error-snackbar';
import { openSnackBar } from '../errors/custom in-page errors/snack-bar/server_error/custom-error-snackbar';

import { MatSnackBar } from "@angular/material/snack-bar";
import { HttpErrorResponse } from "@angular/common/http";
import { SubSink } from "subsink";
import { openCustomSnackBar } from "../errors/custom in-page errors/snack-bar/fullycustomizable_snackbar/custom-error-snackbar";
import { popUpSnackBar } from "../utilities/utils";

@Component({
  selector: 'app-air-data-details',
  templateUrl: './air-data-details.component.html',
  styleUrls: ['./air-data-details.component.scss']
})
export class AirDataDetailsComponent implements OnInit, OnDestroy {
  private subs = new SubSink()
  public airdatas: AirData[] = [];

  public isDisabled = '';

  public userReported = "";
  public validity = "";

  public reportedNumber: number = 0;

  constructor(private cookieService: CookieService, private route: ActivatedRoute,
    private service: AirDataDetailsService, private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    const ID_FROM_PARAMS: number = this.route.snapshot.params['id'];
    this.subs.add(this.service.getAirDataFromIdAndHash(ID_FROM_PARAMS, this.route.snapshot.params['hash']).subscribe({
      next: (response: AirData) => {
        response.receivedDataDateTime = this.formatDate(new Date(response.receivedDataDateTime))
        this.reportedNumber = response.reportedN;
        this.airdatas.push(response);

        if (this.existsCookie(ID_FROM_PARAMS.toString())) {
          this.isDisabled = 'disabled';
          this.userReported = " Tvoje nahlášení jsme již obdrželi!";
        }
        if (this.airdatas[0].invalidData) {
          this.isDisabled = 'disabled';
          this.validity = "<b>Tyto data byly vyhodnoceny jako nesprávné!</b>";
        }
      },
      error: () => {
        const errorAirData = {
          id: -1,
          rndHash: "n / a",
          airQuality: -1,
          temperature: -1,
          humidity: -1,
          locationId: { id: -1, name: "n / a", outofservice: true } as LocationData,
          arduinoTime: "nikdy",
          receivedDataDateTime: "nikdy"
        } as AirData;
        this.airdatas.push(errorAirData);
        this.validity = "<b>Tento záznam neexistuje!</b>";
        openSnackBar(this.snackBar)
      }
    }
    ));
  }

  ngOnDestroy(): void {
    this.subs.unsubscribe()
  }



  /**
   * @param date Date taken from back-end
   * @returns formatted date as string
   */
  private formatDate(date: Date): string {
    return formatDate(date, 'dd.MM.YYYY v HH:mm:ss', "en-US");
  }

  public reportAirData(id: number) {
    if (!this.existsCookie(id.toString())) {
      this.subs.add(this.service.reportAirData(id).subscribe({
        next: () => {
          this.isDisabled = 'disabled'
          this.setCookie(id.toString());
          popUpSnackBar(this.snackBar, "Nahlášení proběhlo úspěšně", 5, true)
          ++this.reportedNumber;
        },
        error: (e: HttpErrorResponse) => {
          this.isDisabled = ''
          if (e.status === 400)
            openSnackBarCannotReport(this.snackBar)
          else
            openSnackBar(this.snackBar)
        }
      })
      )
    }
    else {
      popUpSnackBar(this.snackBar, "Nelze nahlásit stejný záznam dvakrát!", 5, false)
    }
  };

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
