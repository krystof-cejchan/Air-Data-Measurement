import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AirData } from '../airdata';

@Injectable({ providedIn: 'root' })
export class AirDataApiService {
    private apiServerUrl = environment.baseUrlAPI;

    constructor(private http: HttpClient) { }

    public getLatestData(): Observable<AirData[]> {
        return this.http.get<AirData[]>(`${this.apiServerUrl}/latest`);
    }
}