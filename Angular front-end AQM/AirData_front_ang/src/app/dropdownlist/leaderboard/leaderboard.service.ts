import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AirData } from 'src/app/airdata';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LeaderboardService {
  private apiServerUrl = environment.baseUrlAPI;

  constructor(private http: HttpClient) { }
  /**
   * gets All LeaderBoardData
   * @returns LeaderBoardData
   */
  public getAllLeaderboardData(location: string, leaderboardType: string): Observable<AirData[]> {
    return this.http.get<AirData[]>(`${this.apiServerUrl}/getLeaderboard?location=${location}&leaderboardType=${leaderboardType}`);
  }

}
