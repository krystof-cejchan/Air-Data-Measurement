import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AirDataService } from './air-data.service';
import { AirData } from './airdata';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'AirData_front_ang';

  public airdatas: AirData[] = [];

  constructor(private airDataService: AirDataService) { }

  ngOnInit(): void {
    this.getAirDatas();
  }

  public getAirDatas(): void {
    this.airDataService.getAirData().subscribe(
      (response: AirData[]) => {
        this.airdatas = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

}
