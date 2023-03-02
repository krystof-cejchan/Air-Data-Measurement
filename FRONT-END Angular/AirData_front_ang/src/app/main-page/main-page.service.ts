import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MainPageService {
  private baseAirDataUrl = environment.baseAirDataUrl;

  constructor(private http: HttpClient) { }
  /**
   * Gets calculated average temperature from the lastest data from all the locations
   * @returns average temperature
   */
  public getAverageTemperatureFromLatestData(): Observable<Number> {
    return this.http.get<Number>(`${this.baseAirDataUrl}/average_temperature`);
  }
}
