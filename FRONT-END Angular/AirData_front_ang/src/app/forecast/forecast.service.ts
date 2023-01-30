import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ForecastData } from './forecastobject';

@Injectable({
  providedIn: 'root'
})
export class ForecastService {

  private forecastUrl = environment.forecastUrl;

  constructor(private http: HttpClient) { }

  /**
   * ALL forecast data
   * @returns forecast object
   */
  public getAllForecastData(): Observable<ForecastData[]> {
    return this.http.get<ForecastData[]>(`${this.forecastUrl}/all`);
  }

}
