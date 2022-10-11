import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AirDataDetailsService } from './air-data-details.service';
import { AirData } from "../airdata";
import { formatDate } from '@angular/common';
declare const dataTableSet_js: any;

@Component({
  selector: 'app-air-data-details',
  templateUrl: './air-data-details.component.html',
  styleUrls: ['./air-data-details.component.css']
})
export class AirDataDetailsComponent implements OnInit {
  public airdatas: AirData[] = [];

  public isDisabled = '';

  constructor(private route: ActivatedRoute, private service: AirDataDetailsService) { }

  async ngOnInit(): Promise<void> {
    this.service.getAirDataFromIdAndHash(this.route.snapshot.params['id'], this.route.snapshot.params['hash']).subscribe(
      (response: AirData) => {
        response.receivedDataDateTime = this.formatDate(new Date(response.receivedDataDateTime))
        this.airdatas.push(response);
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
    this.service.reportAirData(id).subscribe(
      (response: void) => {
        this.isDisabled = 'disabled'
      }
      ,
      () => {
        this.isDisabled = ''
      }
    );
  }
}
