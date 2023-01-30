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
  private serverUrl = environment.baseAirDataUrl;

  constructor(private http: HttpClient) { }
  /**
   * gets all the average data calculated at midnight each day
   * @param date day for which average data should be returned
   * @returns AirDataAverage
   */
  public getAverageData(date: string): Observable<AirDataAverage> {
    return this.http.get<AirDataAverage>(`${this.serverUrl}/time/day_avg`, { headers: { 'date': date } });
  }
  /**
   * gets all data for a specific day
   * @param date | string
   * @returns AirDatas for the day
   */
  public getAllDataForDay(date: string): Observable<AirData[]> {
    return this.http.get<AirData[]>(`${this.serverUrl}/time/day`, { headers: { 'day': date } });
  }
  /**
   * gets all data for date range
   * @param start date
   * @param end date
   * @returns AirDate[] 
   */
  public getAllDataForDayRange(start: string, end: string): Observable<AirData[]> {
    return this.http.get<AirData[]>(`${this.serverUrl}/time/day_range`, { headers: { 'start': start, 'end': end } });
  }
}
