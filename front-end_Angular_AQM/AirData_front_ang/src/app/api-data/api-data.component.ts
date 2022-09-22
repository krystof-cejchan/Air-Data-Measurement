import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { empty } from 'rxjs';
import { AirData } from '../airdata';
import { AirDataApiService } from './air-data-api.service';

@Component({
  selector: 'app-api-data',
  templateUrl: './api-data.component.html',
  styleUrls: ['./api-data.component.css']
})
export class ApiDataComponent implements OnInit {

  public airdatas: AirData[] = [];

  scriptElement: HTMLScriptElement | undefined;

  constructor(private airdataApiService: AirDataApiService) {

  }

  ngOnInit(): void {
    this.getAirDatas();
    this.setJsScripts();
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

  private setJsScripts() {
    this.scriptElement = document.createElement("script");
    this.scriptElement.src = "scr/assets/js/airdata.receivedDataDateTime_editAndFormat.js"
  }

}

