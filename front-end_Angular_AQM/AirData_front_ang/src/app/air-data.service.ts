import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AirData } from './airdata';

@Injectable({ providedIn: 'root' })
export class AirDataService {
    private apiServerUrl = environment.apiBaseUrl;

    constructor(private http: HttpClient) { }

    public getAirData(): Observable<AirData[]> {
        return this.http.get<AirData[]>(`${this.apiServerUrl}/airdata/all`);
    }

    public addAirData(airdata: AirData): Observable<AirData> {
        return this.http.post<AirData>(`${this.apiServerUrl}/airdata/add`, airdata);
    }

    public updateAirData(airdata: AirData): Observable<AirData> {
        return this.http.put<AirData>(`${this.apiServerUrl}/airdata/update`, airdata);
    }

    public deleteAirData(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiServerUrl}/airdata/delete/${id}`);
    }
}