import { AirData } from "../airdata";
import { LocationData } from "./LocationData";
/**    {
        "id": 187,
        "airDataId": {
            "id": 14,
            "arduinoTime": "04.11.2022",
            "receivedDataDateTime": "2022-11-04T19:44:00",
            "airQuality": 1.74,
            "location": "FF",
            "temperature": 172.00,
            "humidity": 404.55,
            "rndHash": "7308f215-5dfd-46c3-800c-062f07e2e427",
            "reportedN": 0,
            "invalidData": false
        },
        "leaderboardType": "LOWEST_AIRQ",
        "location": "FF",
        "position": 2
    }
]
 */
export interface LeaderboardData {
    id: number,
    airDataId: AirData,
    leaderboardType: string,
    location: LocationData,
    position: number,
    differenceFromTheAbove: Map<{ position: number, leaderboardType: string }, string | number>
}   