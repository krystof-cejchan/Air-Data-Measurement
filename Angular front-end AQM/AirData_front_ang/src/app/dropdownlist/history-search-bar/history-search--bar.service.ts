import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AirData } from 'src/app/airdata';
import { AirDataAverage } from 'src/app/airdata_average';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class HistorySearchBarService {
  private apiServerUrl = environment.baseUrlAPI;

  constructor(private http: HttpClient) { }

  public getAverageData(date: string): Observable<AirDataAverage> {
    return this.http.get<AirDataAverage>(`${this.apiServerUrl}/time/day_avg?date=${date}`);
  }

  public getAllDataForDay(date: string): Observable<|AirData[]> {
    return this.http.get<AirData[]>(`${this.apiServerUrl}/time/day?date=${date}`);
  }
}
