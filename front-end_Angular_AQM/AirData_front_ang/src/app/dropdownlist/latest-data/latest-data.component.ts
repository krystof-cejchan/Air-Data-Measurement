import { formatDate } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AirData } from 'src/app/airdata';
import { LatestDataService } from './latest-data.service';

@Component({
  selector: 'app-latest-data',
  templateUrl: './latest-data.component.html',
  styleUrls: ['./latest-data.component.css']
})
export class LatestDataComponent implements OnInit {

  public airdatas: AirData[] = [];
  private formattedAirDatas: AirData[] = [];

  constructor(private latestDataService: LatestDataService) {

  }

  ngOnInit(): void {
    this.getAirDatas();
  }

  public getAirDatas(): void {
    this.latestDataService.getLatestData().subscribe(
      (response: AirData[]) => {
        this.formattedAirDatas = response;
        try {
          this.formattedAirDatas.forEach(airdata => {
            let formattedAitDate = airdata;
            //formatting date to more humanly readable date
            formattedAitDate.receivedDataDateTime = this.formatDate(new Date(airdata.receivedDataDateTime));
            //background picture set to folder "assets/imgs/faculties/" where images are stored
            formattedAitDate.bcgPictureUrl = "assets/imgs/faculties/" + airdata.location + "_cover.jpg";
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
