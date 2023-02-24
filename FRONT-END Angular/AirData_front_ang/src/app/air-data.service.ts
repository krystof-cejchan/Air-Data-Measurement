import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AirData } from './objects/airdata';

@Injectable({ providedIn: 'root' })
export class AirDataService {
    private baseAirDataUrl = environment.baseAirDataUrl;

    constructor(private http: HttpClient) { }
    /**
     * ALL AirData
     * @returns an array of AirData
     */
    public getAirData(): Observable<AirData[]> {
        return this.http.get<AirData[]>(`${this.baseAirDataUrl}/all`);
    }
    /**
     * SAVES one AirData to the database - may be subject to rejection by back-end → need to check it
     * @param airdata AirData to be added
     * @returns AirData that was actually saved (see back-end)
     */
    public addAirData(airdata: AirData): Observable<AirData> {
        return this.http.post<AirData>(`${this.baseAirDataUrl}/add`, airdata);
    }

}