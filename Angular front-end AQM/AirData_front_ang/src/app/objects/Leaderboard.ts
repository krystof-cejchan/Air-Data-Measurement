import { AirData } from "../airdata";
/**
 * class for creating its objects
 * java syntax -> Map<Map<Location, LeaderboarType>, List<AirData>>
 * 
 * _______________________________
 * EXAMPLE OUTPUT:
 * [
    {
        "id": 170,
        "airDataId": {
            "id": 13,
            "arduinoTime": "04.11.2022",
            "receivedDataDateTime": "2022-11-04T19:39:27",
            "airQuality": -13.74,
            "location": "FF",
            "temperature": 182.00,
            "humidity": 400.55,
            "rndHash": "5f0965d0-bcf5-4df2-afdf-493450dcd32c",
            "reportedN": 0,
            "invalidData": false
        },
        "leaderboardType": "HIGHEST_TEMP",
        "location": "FF",
        "position": 1
    },
    {
        "id": 171,
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
        "leaderboardType": "HIGHEST_TEMP",
        "location": "FF",
        "position": 2
    },
    {
        "id": 172,
        "airDataId": {
            "id": 13,
            "arduinoTime": "04.11.2022",
            "receivedDataDateTime": "2022-11-04T19:39:27",
            "airQuality": -13.74,
            "location": "FF",
            "temperature": 182.00,
            "humidity": 400.55,
            "rndHash": "5f0965d0-bcf5-4df2-afdf-493450dcd32c",
            "reportedN": 0,
            "invalidData": false
        },
        "leaderboardType": "LOWEST_HUM",
        "location": "FF",
        "position": 1
    },
    {
        "id": 173,
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
        "leaderboardType": "LOWEST_HUM",
        "location": "FF",
        "position": 2
    },
    {
        "id": 174,
        "airDataId": {
            "id": 15,
            "arduinoTime": "04.11.2022",
            "receivedDataDateTime": "2022-11-04T19:56:29",
            "airQuality": 10.00,
            "location": "PdF",
            "temperature": 13.95,
            "humidity": 99.00,
            "rndHash": "169f7101-7313-4a3e-af20-a924127a1eb4",
            "reportedN": 0,
            "invalidData": false
        },
        "leaderboardType": "LOWEST_AIRQ",
        "location": "PdF",
        "position": 1
    },
    {
        "id": 175,
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
        "leaderboardType": "HIGHEST_HUM",
        "location": "FF",
        "position": 1
    },
    {
        "id": 176,
        "airDataId": {
            "id": 13,
            "arduinoTime": "04.11.2022",
            "receivedDataDateTime": "2022-11-04T19:39:27",
            "airQuality": -13.74,
            "location": "FF",
            "temperature": 182.00,
            "humidity": 400.55,
            "rndHash": "5f0965d0-bcf5-4df2-afdf-493450dcd32c",
            "reportedN": 0,
            "invalidData": false
        },
        "leaderboardType": "HIGHEST_HUM",
        "location": "FF",
        "position": 2
    },
    {
        "id": 177,
        "airDataId": {
            "id": 15,
            "arduinoTime": "04.11.2022",
            "receivedDataDateTime": "2022-11-04T19:56:29",
            "airQuality": 10.00,
            "location": "PdF",
            "temperature": 13.95,
            "humidity": 99.00,
            "rndHash": "169f7101-7313-4a3e-af20-a924127a1eb4",
            "reportedN": 0,
            "invalidData": false
        },
        "leaderboardType": "HIGHEST_TEMP",
        "location": "PdF",
        "position": 1
    },
    {
        "id": 178,
        "airDataId": {
            "id": 15,
            "arduinoTime": "04.11.2022",
            "receivedDataDateTime": "2022-11-04T19:56:29",
            "airQuality": 10.00,
            "location": "PdF",
            "temperature": 13.95,
            "humidity": 99.00,
            "rndHash": "169f7101-7313-4a3e-af20-a924127a1eb4",
            "reportedN": 0,
            "invalidData": false
        },
        "leaderboardType": "LOWEST_TEMP",
        "location": "PdF",
        "position": 1
    },
    {
        "id": 179,
        "airDataId": {
            "id": 15,
            "arduinoTime": "04.11.2022",
            "receivedDataDateTime": "2022-11-04T19:56:29",
            "airQuality": 10.00,
            "location": "PdF",
            "temperature": 13.95,
            "humidity": 99.00,
            "rndHash": "169f7101-7313-4a3e-af20-a924127a1eb4",
            "reportedN": 0,
            "invalidData": false
        },
        "leaderboardType": "HIGHEST_AIRQ",
        "location": "PdF",
        "position": 1
    },
    {
        "id": 180,
        "airDataId": {
            "id": 15,
            "arduinoTime": "04.11.2022",
            "receivedDataDateTime": "2022-11-04T19:56:29",
            "airQuality": 10.00,
            "location": "PdF",
            "temperature": 13.95,
            "humidity": 99.00,
            "rndHash": "169f7101-7313-4a3e-af20-a924127a1eb4",
            "reportedN": 0,
            "invalidData": false
        },
        "leaderboardType": "HIGHEST_HUM",
        "location": "PdF",
        "position": 1
    },
    {
        "id": 181,
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
        "leaderboardType": "HIGHEST_AIRQ",
        "location": "FF",
        "position": 1
    },
    {
        "id": 182,
        "airDataId": {
            "id": 13,
            "arduinoTime": "04.11.2022",
            "receivedDataDateTime": "2022-11-04T19:39:27",
            "airQuality": -13.74,
            "location": "FF",
            "temperature": 182.00,
            "humidity": 400.55,
            "rndHash": "5f0965d0-bcf5-4df2-afdf-493450dcd32c",
            "reportedN": 0,
            "invalidData": false
        },
        "leaderboardType": "HIGHEST_AIRQ",
        "location": "FF",
        "position": 2
    },
    {
        "id": 183,
        "airDataId": {
            "id": 15,
            "arduinoTime": "04.11.2022",
            "receivedDataDateTime": "2022-11-04T19:56:29",
            "airQuality": 10.00,
            "location": "PdF",
            "temperature": 13.95,
            "humidity": 99.00,
            "rndHash": "169f7101-7313-4a3e-af20-a924127a1eb4",
            "reportedN": 0,
            "invalidData": false
        },
        "leaderboardType": "LOWEST_HUM",
        "location": "PdF",
        "position": 1
    },
    {
        "id": 184,
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
        "leaderboardType": "LOWEST_TEMP",
        "location": "FF",
        "position": 1
    },
    {
        "id": 185,
        "airDataId": {
            "id": 13,
            "arduinoTime": "04.11.2022",
            "receivedDataDateTime": "2022-11-04T19:39:27",
            "airQuality": -13.74,
            "location": "FF",
            "temperature": 182.00,
            "humidity": 400.55,
            "rndHash": "5f0965d0-bcf5-4df2-afdf-493450dcd32c",
            "reportedN": 0,
            "invalidData": false
        },
        "leaderboardType": "LOWEST_TEMP",
        "location": "FF",
        "position": 2
    },
    {
        "id": 186,
        "airDataId": {
            "id": 13,
            "arduinoTime": "04.11.2022",
            "receivedDataDateTime": "2022-11-04T19:39:27",
            "airQuality": -13.74,
            "location": "FF",
            "temperature": 182.00,
            "humidity": 400.55,
            "rndHash": "5f0965d0-bcf5-4df2-afdf-493450dcd32c",
            "reportedN": 0,
            "invalidData": false
        },
        "leaderboardType": "LOWEST_AIRQ",
        "location": "FF",
        "position": 1
    },
    {
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
    location: string,
    position: number;
}   