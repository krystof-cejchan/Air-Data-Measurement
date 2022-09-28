import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { AirDataAverage } from 'src/app/airdata_average';
import { HistoryDataService } from './history-data.service';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {

  public date: string | null | undefined;
  public avgData: AirDataAverage | undefined;

  constructor(
    private route: ActivatedRoute, private router: Router, private service: HistoryDataService
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params:ParamMap) => {
          this.date = params.get('date');
    });
    console.log(this.date);

    if (this.date) {
      this.service.getHistoryData(this.date).subscribe(
        (response: AirDataAverage) => {
          this.avgData = response;
        },
        (error: HttpErrorResponse) => {
          console.log(error.message)
        }
      );
    }
  }
  //showDataRelatedToTheDate() {
  //this.router.navigate(['prumer'], { relativeTo: this.route });
}





