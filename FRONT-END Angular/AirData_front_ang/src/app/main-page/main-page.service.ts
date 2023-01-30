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
  public getAverageTemperatureFromLatestData(): Observable<Number> {
    return this.http.get<Number>(`${this.baseAirDataUrl}/average_temperature`);
  }
}
