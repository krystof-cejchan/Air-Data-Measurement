import { LeaderboardData } from "./Leaderboard";
/**
 * LeaderboardData prepared for html(*ngFor)
 */
export interface PrePreparedLeaderboardData {
    leaderboardData: LeaderboardData[],
    type: string
}   