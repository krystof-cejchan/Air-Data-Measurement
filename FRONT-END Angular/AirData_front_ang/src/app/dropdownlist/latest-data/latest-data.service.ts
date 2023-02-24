import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AirData } from 'src/app/objects/airdata';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LatestDataService {
  private serverUrl = environment.baseAirDataUrl;

  constructor(private http: HttpClient) { }
  /**
   * gets one latest AirData for each available location
   * @returns latest AirData
   */
  public getLatestData(): Observable<AirData[]> {
    return this.http.get<AirData[]>(`${this.serverUrl}/latest`);
  }
}
