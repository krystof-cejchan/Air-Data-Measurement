import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AirData } from 'src/app/objects/airdata';
import { environment } from 'src/environments/environment';
import { LeaderboardData } from "../../objects/Leaderboard";

@Injectable({
  providedIn: 'root'
})
export class LeaderboardService {
  private apiServerUrl = environment.leaderboard;

  constructor(private http: HttpClient) { }
  
   /**
   * gets All LeaderBoardData
   * @returns LeaderBoardData
   */
    public getAllLeaderboardData(): Observable<LeaderboardData[]> {
      return this.http.get<LeaderboardData[]>(`${this.apiServerUrl}/getAllDataFromLeaderboard`);
    }

}
