import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AirData } from '../airdata';
import { AirDataApiService } from './air-data-api.service';

@Component({
  selector: 'app-api-data',
  templateUrl: './api-data.component.html',
  styleUrls: ['./api-data.component.css']
})
export class ApiDataComponent implements OnInit {

  public airdatas: AirData[] = [];

  constructor(private airdataApiService: AirDataApiService) { }

  ngOnInit(): void {
    this.getAirDatas();
  }

  public getAirDatas(): void {
    this.airdataApiService.getLatestData().subscribe(
      (response: AirData[]) => {
        this.airdatas = response;
      },
      (error: HttpErrorResponse) => {
        console.log(error.message);
        alert('Server did not respond succesfully!');
      }
    );
  }

}
