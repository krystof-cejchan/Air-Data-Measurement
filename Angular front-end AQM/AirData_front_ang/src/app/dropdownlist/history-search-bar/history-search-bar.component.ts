import { formatDate } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, SimpleChanges } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MomentDateAdapter, MAT_MOMENT_DATE_ADAPTER_OPTIONS } from '@angular/material-moment-adapter';
import { DateAdapter, MAT_DATE_LOCALE, MAT_DATE_FORMATS } from '@angular/material/core';
import { ActivatedRoute, Router } from '@angular/router';

import * as _moment from 'moment';
import { default as _rollupMoment } from 'moment';
import { AirData } from 'src/app/airdata';
import { AirDataAverage } from 'src/app/airdata_average';
import { HistorySearchBarService } from './history-search--bar.service';

const moment = _rollupMoment || _moment;

export const MY_FORMATS = {
  parse: {
    dateInput: 'DD / MM / YYYY',
  },
  display: {
    dateInput: 'DD / MM / YYYY',
    monthYearLabel: 'DD / MM / YYYY',
    dateA11yLabel: 'DD / MM / YYYY',
    monthYearA11yLabel: 'DD / MM / YYYY',
  },
};

@Component({
  selector: 'app-history-search-bar',
  templateUrl: './history-search-bar.component.html',
  styleUrls: ['./history-search-bar.component.css'],
  providers: [
    {
      provide: DateAdapter,
      useClass: MomentDateAdapter,
      deps: [MAT_DATE_LOCALE, MAT_MOMENT_DATE_ADAPTER_OPTIONS],
    },

    { provide: MAT_DATE_FORMATS, useValue: MY_FORMATS },
  ]
})

export class HistorySearchBarComponent implements OnInit {
  date = new FormControl(moment()); //WHEN SENDING REQUEST TO BACK-END THE DATE FORMAT MUST BE AS FOLLOWING: YYYY-MM-DD
  minDate: Date;
  maxDate: Date;

  private pickedDate: string | undefined;
  public choosenDate = '';

  public avgDatas: AirDataAverage[] = [];
  public airDataForDay: AirData[] = [];

  htmlToAdd: string = '';

  constructor(private route: ActivatedRoute, private router: Router, private service: HistorySearchBarService) {
    this.minDate = new Date(2022, 8, 23);
    this.maxDate = new Date();//new Date(date.getFullYear().toPrecision(1), date.getMonth(), date.getDay() - 1);
  }

  ngOnInit(): void {
    this.updateChoosenDate();
  }

  //on button click function called from html component
  public showData() {
    if (this.pickedDate) {
      // this.updateChoosenDate();
      this.router.navigate(['/nekdejsi-data/' + this.pickedDate], { relativeTo: this.route });
      this.updateChoosenDate(this.pickedDate);

      //calling data from back-end
      this.getAvgAirData(this.pickedDate);
      this.getAirDataForDate(this.pickedDate);

    }

  }
  updateDOB(dateObject: { value: string; }) {
    this.pickedDate = formatDate(new Date(dateObject.value), 'yyyy-MM-dd', 'en_US');
    this.updateChoosenDate();
  }

  private updateChoosenDate(prePickedDate?: string) {
    let date = prePickedDate ? prePickedDate : this.route.snapshot.params['date'];
    if (date) {
      if (moment(new Date(date), "YYYY-MM-DD").isValid()) {
        this.choosenDate = formatDate(new Date(date), 'dd-MM-yyyy', 'en_US');
      }
      else
        this.router.navigate(['/nekdejsi-data'], { relativeTo: this.route });
    }

  }


  //getting data from back-end
  public async getAvgAirData(date: string): Promise<void> {
    this.service.getAverageData(date!).subscribe(
      (response: AirDataAverage) => {
        if (this.isNull(response) === false) {
          this.avgDatas.push(response);
          this.htmlToAdd = '';
        } else {
          this.htmlToAdd = 'Server did not respond succesfully!<br>There is no data for this date';
        }
      },
      (error: HttpErrorResponse) => {
        this.htmlToAdd = 'Server did not respond succesfully!<br>' + error.name;
      }
    );
  }

  private isNull(airdata_average: AirDataAverage): boolean {
    return (airdata_average.avgAirQuality === null || airdata_average.avgHumidity === null || airdata_average.avgTemperature === null);
  }

  public async getAirDataForDate(date: string): Promise<void> {
    this.service.getAllDataForDay(date!).subscribe(
      (response: AirData[]) => {
        this.airDataForDay = response;
      },
      (error: HttpErrorResponse) => {
        this.htmlToAdd = 'Server did not respond succesfully!<br>' + error.name;
      }
    );
  }
}
