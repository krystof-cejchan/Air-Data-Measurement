import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AirData } from './airdata';

@Injectable({ providedIn: 'root' })
export class AirDataService {
    private baseUrl = environment.baseUrl;

    constructor(private http: HttpClient) { }
    /**
     * ALL AirData
     * @returns an array of AirData
     */
    public getAirData(): Observable<AirData[]> {
        return this.http.get<AirData[]>(`${this.baseUrl}/airdata/all`);
    }
    /**
     * SAVES one AirData to the database - may be subject to rejection by back-end → need to check it
     * @param airdata AirData to be added
     * @returns AirData that was actually saved (see back-end)
     */
    public addAirData(airdata: AirData): Observable<AirData> {
        return this.http.post<AirData>(`${this.baseUrl}/airdata/add`, airdata);
    }
    /**
     * UPDATES one AirData if found
     * @param airdata to be updated
     * @returns updated AirData
     */
    public updateAirData(airdata: AirData): Observable<AirData> {
        return this.http.put<AirData>(`${this.baseUrl}/airdata/update`, airdata);
    }
    /**
     * DELETES one AirData with provided id in params
     * @param id id of the record which would be deleted
     * @returns Observable<void> (basically nothing)
     */
    public deleteAirData(id: number): Observable<void> {
        return this.http.delete<void>(`${this.baseUrl}/airdata/delete/${id}`);
    }
}