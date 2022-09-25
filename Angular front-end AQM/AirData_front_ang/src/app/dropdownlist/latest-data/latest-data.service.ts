import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AirData } from 'src/app/airdata';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LatestDataService {
  private apiServerUrl = environment.baseUrlAPI;

  constructor(private http: HttpClient) { }

  public getLatestData(): Observable<AirData[]> {
    return this.http.get<AirData[]>(`${this.apiServerUrl}/latest`);
  }
}
