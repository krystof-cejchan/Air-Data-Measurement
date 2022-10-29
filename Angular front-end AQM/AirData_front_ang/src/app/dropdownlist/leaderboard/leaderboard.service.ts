import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { LeaderboardData } from "../../objects/Leaderboard";

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
  public getAllLeaderboardData(): Observable<LeaderboardData> {
    return this.http.get<LeaderboardData>(`${this.apiServerUrl}/getLeaderboard`);
  }

}
