import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AirData } from '../airdata';

@Injectable({
  providedIn: 'root'
})
export class AirDataDetailsService {
  private serverUrl = environment.baseAirDataUrl;

  constructor(private http: HttpClient) { }
  /**
   * gets data with provided id and hash
   * @param id of the data
   * @param hash of the data
   * @returns AirData if data were found, else returns an error BAD_REQUESTING
   */
  public getAirDataFromIdAndHash(id: number, hash: string): Observable<AirData> {
    return this.http.get<AirData>(`${this.serverUrl}/getByIdAndHash`, { headers: { 'id': id.toString(), 'hash': hash } });
  }
  /**
   * sends a report to the back-end where the report shall be decided whether it is a valid one or not
   * @param id for the data to be reported
   * @returns void
   */
  public reportAirData(id: number): Observable<void> {
    return this.http.patch<void>(`${this.serverUrl}/update_reportN`, { headers: { 'id': id } });
  }

}
