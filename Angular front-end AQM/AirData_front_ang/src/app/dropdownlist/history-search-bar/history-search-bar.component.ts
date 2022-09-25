import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MomentDateAdapter, MAT_MOMENT_DATE_ADAPTER_OPTIONS } from '@angular/material-moment-adapter';
import { DateAdapter, MAT_DATE_LOCALE, MAT_DATE_FORMATS } from '@angular/material/core';
import * as _moment from 'moment';
import { default as _rollupMoment } from 'moment';

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

  constructor() {
    var date = new Date();
    this.minDate = new Date(2022, 8, 23);
    this.maxDate = date;//new Date(date.getFullYear().toPrecision(1), date.getMonth(), date.getDay() - 1);
  }

  ngOnInit(): void {

  }

}
