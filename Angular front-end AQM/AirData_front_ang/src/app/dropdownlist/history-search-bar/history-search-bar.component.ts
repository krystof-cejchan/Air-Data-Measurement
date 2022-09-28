import { formatDate } from '@angular/common';
import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MomentDateAdapter, MAT_MOMENT_DATE_ADAPTER_OPTIONS } from '@angular/material-moment-adapter';
import { DateAdapter, MAT_DATE_LOCALE, MAT_DATE_FORMATS } from '@angular/material/core';
import { ActivatedRoute, Router } from '@angular/router';

import * as _moment from 'moment';
import { default as _rollupMoment } from 'moment';
import { AirDataAverage } from 'src/app/airdata_average';

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
  public airDataAvg: AirDataAverage | undefined;
  public choosenDate = '';

  constructor(private route: ActivatedRoute, private router: Router) {
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
      this.updateChoosenDate();
    }

  }
  updateDOB(dateObject: { value: any; }) {
    this.pickedDate = formatDate(new Date(dateObject.value), 'yyyy-MM-dd', 'en_US');
    this.updateChoosenDate();
  }

  private async updateChoosenDate() {
    let date = this.route.snapshot.params['date'];
    if (date) {
      if (moment(new Date(date), "YYYY-MM-DD").isValid()) {
        console.log('now')
        this.choosenDate = '<div class="choosenDate">' + formatDate(new Date(date), 'dd-MM-yyyy', 'en_US') + '</div>';
      }
      else
        this.router.navigate(['/nekdejsi-data'], { relativeTo: this.route });
    }

  }
}
