import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AirData } from '../airdata';
import { AirDataApiService } from './air-data-api.service';
import { formatDate } from '@angular/common';


@Component({
  selector: 'app-api-data',
  templateUrl: './api-data.component.html',
  styleUrls: ['./api-data.component.css']
})
export class ApiDataComponent implements OnInit {

  public airdatas: AirData[] = [];
  private formattedAirDatas: AirData[] = [];

  constructor(private airdataApiService: AirDataApiService) {

  }

  ngOnInit(): void {
    this.getAirDatas();
  }

  public getAirDatas(): void {
    this.airdataApiService.getLatestData().subscribe(
      (response: AirData[]) => {
        this.formattedAirDatas = response;
        try {
          this.formattedAirDatas.forEach(airdata => {
            let formattedAitDate = airdata;
            formattedAitDate.receivedDataDateTime = this.formatDate(new Date(airdata.receivedDataDateTime));
            this.airdatas.push(formattedAitDate);
          })
        } catch (error) {
          this.airdatas = response;
        }
      },
      (error: HttpErrorResponse) => {
        console.log(error.message);
        alert('Server did not respond succesfully!');
      }
    );
  }
  /**
   * @param date Date taken from back-end
   * @returns formatted date as string
   */
  private formatDate(date: Date): string {
    return formatDate(date, 'dd-MM • hh:mm:ss', "en-US");
  }
}

