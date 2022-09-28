import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AirDataAverage } from 'src/app/airdata_average';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class HistoryDataService {
  private apiServerUrl = environment.baseUrlAPI;

  constructor(private http: HttpClient) { }

  public getHistoryData(date: string): Observable<AirDataAverage> {
    const headers = new HttpHeaders().set("date", date);
    return this.http.get<AirDataAverage>(`${this.apiServerUrl}/time/day`, { headers });
  }
}
