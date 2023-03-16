import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { environment } from "../../environments/environment";
import { LocationData } from "../objects/LocationData";
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LocationDetailsService {
  private locationUrl = environment.locationUrl;
  constructor(private http: HttpClient) { }

  public getLocationById(id: number): Observable<LocationData> {
    return this.http.get<LocationData>(`${this.locationUrl}/get`, { headers: { 'id': id.toString() } });
  }
}
