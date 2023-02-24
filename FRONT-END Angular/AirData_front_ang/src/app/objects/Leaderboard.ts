import { AirData } from "./airdata";
import { LocationData } from "./LocationData";

export interface LeaderboardData {
    id: number,
    airDataId: AirData,
    leaderboardType: string,
    location: LocationData,
    position: number,
    differenceFromTheAbove: Map<{ position: number, leaderboardType: string }, string | number>
}   