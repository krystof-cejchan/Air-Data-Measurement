import { LeaderboardData } from "./Leaderboard";
export interface LeaderboardDataExtended {
    type: 'AIRQ' | 'TEMP' | 'HUM' | string;
    mapSubTypeToLeaderboardData: Map<string, LeaderboardData[]>
}