import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AirDataAverageForDay } from 'src/app/objects/airDataAverageForDay';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class GraphsService {
  private apiServerUrl = environment.baseUrl;

  constructor(private http: HttpClient) { }

  public getAllAirDataAverage(): Observable<AirDataAverageForDay[]> {
    return this.http.get<AirDataAverageForDay[]>(`${this.apiServerUrl}/airdata/avg/all`);
  }
}