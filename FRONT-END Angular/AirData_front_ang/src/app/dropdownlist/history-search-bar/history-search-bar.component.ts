import { formatDate } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, Directive, Inject, Injectable, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MomentDateAdapter, MAT_MOMENT_DATE_ADAPTER_OPTIONS } from '@angular/material-moment-adapter';
import { DateAdapter, MAT_DATE_LOCALE, MAT_DATE_FORMATS } from '@angular/material/core';
import { Sort } from '@angular/material/sort';
import { ActivatedRoute, Router } from '@angular/router';
import _moment from 'moment';
import { default as _rollupMoment } from 'moment';
import { AirData } from 'src/app/airdata';
import { AirDataAverage } from 'src/app/airdata_average';
import { HistorySearchBarService } from './history-search--bar.service';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { MatDateRangeSelectionStrategy, DateRange, MAT_DATE_RANGE_SELECTION_STRATEGY } from '@angular/material/datepicker';
import { openSnackBar } from 'src/app/errors/custom in-page errors/snack-bar/custom-error-snackbar';
import { MatSnackBar } from "@angular/material/snack-bar";


const moment = _rollupMoment || _moment;
export interface PeriodicElement {
  no: number;
  time: string;
  place: string;
  temperature: number;
  humidity: number;
  airQuality: number;
}
export const MY_FORMATS = {
  parse: {
    dateInput: 'DD/MM/YYYY',
  },
  display: {
    dateInput: 'DD/MM/YYYY',
    monthYearLabel: 'DD/MM/YYYY',
    dateA11yLabel: 'DD/MM/YYYY',
    monthYearA11yLabel: 'DD/MM/YYYY',
  },
};

@Injectable()
export class MaxRangeSelectionStrategy<D>
  implements MatDateRangeSelectionStrategy<D> {
  start: any;
  /**
   * maximum picked days //minus 1 serves as optimizer as the first index starts at 0
   */
  public delta: number = 7 - 1;
  constructor(private _dateAdapter: DateAdapter<D>) { }

  selectionFinished(date: D, currentRange: DateRange<D>) {
    let { start, end } = currentRange;
    if (start == null || (start && end)) {
      start = date;
      end = null;
    } else if (end == null) {
      const maxDate = this._dateAdapter.addCalendarDays(start, this.delta);
      end = date ? (date > maxDate ? maxDate : date) : null;
    }

    return new DateRange<D>(start, end);
  }
  createPreview(
    activeDate: D | null,
    currentRange: DateRange<D>
  ): DateRange<D> {
    if (currentRange.start && !currentRange.end) {
      const maxDate = this._dateAdapter.addCalendarDays(
        currentRange.start,
        this.delta
      );
      const rangeEnd = activeDate
        ? activeDate > maxDate
          ? maxDate
          : activeDate
        : null;

      return new DateRange(currentRange.start, rangeEnd);
    }

    return new DateRange<D>(null, null);
  }
}

@Directive({
  selector: "[maxRange]",
  providers: [
    {
      provide: MAT_DATE_RANGE_SELECTION_STRATEGY,
      useClass: MaxRangeSelectionStrategy
    }
  ]
})
export class MaxRangeDirective {
  constructor(
    @Inject(MAT_DATE_RANGE_SELECTION_STRATEGY)
    private maxRangeStrategy: MaxRangeSelectionStrategy<any>
  ) { }
  @Input() set maxRange(value: number) {
    this.maxRangeStrategy.delta = +value || 7;
  }
}

@Component({
  selector: 'app-history-search-bar',
  templateUrl: './history-search-bar.component.html',
  styleUrls: ['./history-search-bar.component.scss'],
  providers: [
    {
      provide: DateAdapter,
      useClass: MomentDateAdapter,
      deps: [MAT_DATE_LOCALE, MAT_MOMENT_DATE_ADAPTER_OPTIONS],
    },
    { provide: MAT_DATE_FORMATS, useValue: MY_FORMATS },
    { provide: 'rangeMax', useValue: 5 },
    {
      provide: MAT_DATE_RANGE_SELECTION_STRATEGY,
      useClass: MaxRangeSelectionStrategy
    }
  ]
})

export class HistorySearchBarComponent implements OnInit, IComponent {
  // private elementData: PeriodicElement[] = [];
  displayedColumns: string[] = ['no', 'time', 'place', 'temperature', 'humidity', 'airQuality'];
  sortedData: PeriodicElement[] = [];
  sortedDataCopy: PeriodicElement[] = [];
  separatorKeysCodes: number[] = [ENTER, COMMA];
  locationCtrl = new FormControl('');


  activeLocations: string[] = [];
  allLocations: string[] = [/* is loaded from the back-end */];

  date = new FormControl(moment()); //WHEN SENDING REQUEST TO BACK-END THE DATE FORMAT MUST BE AS FOLLOWING: YYYY-MM-DD
  minDate: Date;
  maxDate: Date;

  private pickedDate: {
    start: string, end: string
  } = { start: new Date().toISOString().substring(0, 10), end: new Date().toISOString().substring(0, 10) };

  public choosenDate: {
    start?: string | Date, end?: string | Date
  } = { start: new Date().toISOString(), end: new Date().toISOString() };

  public tableShown: boolean = false;

  public avgDatas: AirDataAverage[] = [];

  public showLoading: boolean = false;

  private asc_direction: boolean = true;

  constructor(private route: ActivatedRoute, private router: Router, private service: HistorySearchBarService, private snackBar: MatSnackBar) {
    this.minDate = new Date(2023, 0, 1);
    this.maxDate = new Date();//new Date(date.getFullYear().toPrecision(1), date.getMonth(), date.getDay() - 1);
  }

  range = new FormGroup({
    start: new FormControl<Date | null>(null),
    end: new FormControl<Date | null>(null),
  });

  getTitle(): string {
    return "Historie";
  }

  ngOnInit(): void {
    this.updateChoosenDate();
    this.showData(this.route.snapshot.params['start'], this.route.snapshot.params['end']);
  }

  sortData(sort: Sort) {
    const data = this.sortedData.slice();
    if (sort.direction === '') {
      sort.direction = 'asc';
      sort.active = "no";
    }
    if (!sort.active) {
      this.sortedData = data;
      return;
    }

    this.sortedData = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'time':
          return this.compare(a.time, b.time, isAsc);
        case 'no':
          return this.compare(a.no, b.no, isAsc);
        case 'airQuality':
          return this.compare(a.airQuality, b.airQuality, isAsc);
        case 'temperature':
          return this.compare(a.temperature, b.temperature, isAsc);
        case 'place':
          return this.compare(a.place, b.place, isAsc);
        case 'humidity':
          return this.compare(a.humidity, b.humidity, isAsc);
        default:
          return 0;
      }
    });
    this.sortedDataCopy = this.sortedData;
  }
  protected compare(a: number | string, b: number | string, isAsc: boolean) {
    return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
  }

  //on button click function called from html component
  public showData(prePickedDateStart?: string, prePickedDateEnd?: string) {
    this.showLoading = true;
    let date = (prePickedDateStart && prePickedDateEnd) ? { start: prePickedDateStart, end: prePickedDateEnd } : this.pickedDate;
    if (date) {
      // this.updateChoosenDate();
      this.router.navigate(['/nekdejsi-data/' + date.start + '/' + date.end], { relativeTo: this.route });
      this.updateChoosenDate(date);

      //calling data from back-end
      // this.getAvgAirData(formatDate(new Date(date), 'yyyy-MM-dd', 'en_US'));
      this.getAirDataForDateRange(new Date(date.start), new Date(date.end));
    }
    else this.showLoading = false;

  }

  updateDOB(dateObject: Partial<{ start: Date | null; end: Date | null; }>) {

    if (dateObject.start) {
      this.pickedDate = {
        start: formatDate(new Date(dateObject.start), 'yyyy-MM-dd', 'en_US'),
        end: formatDate(this.pickedDate.end, 'yyyy-MM-dd', 'en_US')
      };
    }
    if (dateObject.end) {
      this.pickedDate = {
        start: formatDate(this.pickedDate.start, 'yyyy-MM-dd', 'en_US'),
        end: formatDate(new Date(dateObject.end), 'yyyy-MM-dd', 'en_US')
      };
    }

    this.updateChoosenDate();
  }

  private updateChoosenDate(prePickedDate?: { 'start': string, 'end': string }) {
    let dateS = prePickedDate?.start ? prePickedDate.start : this.route.snapshot.params['start'];
    let dateE = prePickedDate?.end ? prePickedDate.end : this.route.snapshot.params['end'];
    if (dateS && dateE) {
      if (moment(new Date(dateS), "YYYY-MM-DD").isValid() && moment(new Date(dateE), "YYYY-MM-DD").isValid()) {
        this.choosenDate.start = formatDate(new Date(dateS), 'dd-MM-yyyy', 'en_US');
        this.choosenDate.end = formatDate(new Date(dateE), 'dd-MM-yyyy', 'en_US');
      }
      else
        this.router.navigate(['/nekdejsi-data'], { relativeTo: this.route });
    }

  }


  //getting data from back-end
  public async getAirDataAvgForDate(date: string): Promise<void> {
    this.avgDatas = [];
    this.service.getAverageData(date!).subscribe(
      (response: AirDataAverage) => {
        if (this.isNull(response) === false) {
          this.avgDatas.push(response);

        }
      }
    );
  }

  private isNull(airdata_average: AirDataAverage): boolean {
    return (airdata_average.avgAirQuality === null || airdata_average.avgHumidity === null || airdata_average.avgTemperature === null);
  }

  public async getAirDataForDateRange(startDate: Date, endDate: Date): Promise<void> {
    this.sortedData = [];
    let airDataForDay: AirData[] = [];
    this.service.getAllDataForDayRange(formatDate(startDate, "YYYY-MM-dd", "en-US").toString()!,
      formatDate(endDate, "YYYY-MM-dd", "en-US").toString()!).subscribe(
        async (response: AirData[]) => {

          let formattedAirDatas: AirData[] = response;
          try {
            formattedAirDatas.forEach(airdata => {
              let formattedAitDate = airdata;
              //formatting date to more humanly readable date
              formattedAitDate.receivedDataDateTime = formatDate(new Date(airdata.receivedDataDateTime), 'dd.MM.y HH:mm:ss', "en-US")
              airDataForDay.push(formattedAitDate);
            })

            var no = 0;
            airDataForDay.forEach(data => {
              this.sortedData.push({
                no: ++no,
                time: data.receivedDataDateTime,
                place: data.locationId.name_short,
                temperature: data.temperature,
                humidity: data.humidity,
                airQuality: data.airQuality
              })
            });

            // filter => to delete duplicate values
            this.allLocations = response
              .map(sortedD => sortedD.locationId.name_short)
              .filter((value, index, array) => array.indexOf(value) === index);
            this.activeLocations = response
              .map(sortedD => sortedD.locationId.name_short)
              .filter((value, index, array) => array.indexOf(value) === index);

            this.sortedDataCopy = this.sortedData;
            this.tableShown = true;
            this.showLoading = false;
          } catch (error) {
            response.forEach(data => {
              this.sortedData.push({
                no: ++no,
                time: data.receivedDataDateTime,
                place: data.locationId.name_short,
                temperature: data.temperature,
                humidity: data.humidity,
                airQuality: data.airQuality
              });
            })
            this.showLoading = false;
          }
        },
        () => {
          this.sortedData = [], this.activeLocations = [], this.allLocations = [];
          this.tableShown = false;
          this.showLoading = false;
          openSnackBar(this.snackBar)

        }
      );
  }

  showIfTableEmpty(): boolean {
    return this.sortedData.length < 1;
  }
  showIfAnyLocations(): boolean {
    return this.allLocations.length > 0;
  }

  /**
   * sorts table based on the clicked column; switches between ascending and descending automatically
   * @param col number of the clicked column the sorting is based on
   */
  public sortTable(col: number) {
    this.asc_direction = !this.asc_direction;
    let table = document.getElementById('data_table') as HTMLTableElement;
    let tbody = table.tBodies[0];
    let rows = Array.from(tbody.querySelectorAll('tr'));

    let comparer = (a: HTMLTableRowElement, b: HTMLTableRowElement) => {
      let valA = a.cells[col].textContent;
      let valB = b.cells[col].textContent;

      if (valA == null || valB == null)
        return 0;

      if (!isNaN(Number(valA)) && !isNaN(Number(valB))) {
        return Number(valA) - Number(valB);
      } else {
        if (valA < valB) {
          return -1;
        } else if (valA > valB) {
          return 1;
        } else {
          return 0;
        }
      }
    };

    if (this.asc_direction) {
      rows.sort(comparer);
    } else {
      rows.sort(comparer).reverse();
    }

    rows.forEach(row => tbody.appendChild(row));
  }

  public async onChipClick(location: string) {
    if (this.activeLocations.includes(location))
      this.activeLocations = this.activeLocations.filter(element => element != location)
    else
      this.activeLocations.push(location);

    this.sortedData = this.sortedDataCopy.filter(filter => this.activeLocations.includes(filter.place))
  }
}
