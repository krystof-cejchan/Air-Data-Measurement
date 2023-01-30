import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AirDataAverageForDay } from 'src/app/objects/airDataAverageForDay';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class GraphsService {
  private serverUrl = environment.baseAirDataUrl;

  constructor(private http: HttpClient) { }
/**
 * gets all AirDataAverage for the graphs
 * also see getAverageData in HistorySearchBarService
 * @returns 
 */
  public getAllAirDataAverage(): Observable<AirDataAverageForDay[]> {
    return this.http.get<AirDataAverageForDay[]>(`${this.serverUrl}/avg/all`);
  }
}