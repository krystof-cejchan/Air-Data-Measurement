import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AirData } from '../airdata';

@Injectable({
  providedIn: 'root'
})
export class AirDataDetailsService {
  private apiServerUrl = environment.baseUrlAPI;
  private serverUrl = environment.baseUrl;

  constructor(private http: HttpClient) { }

  public getAirDataFromIdAndHash(id: number, hash: string): Observable<AirData> {
    return this.http.get<AirData>(`${this.apiServerUrl}/getByIdAndHash?id=${id}&hash=${hash}`);
  }

  public reportAirData(id: number): Observable<void> {
    return this.http.put<void>(`${this.serverUrl}/airdata/update_reportN`, id);
  }

}
