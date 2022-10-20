import { AirData } from "../airdata";
/**
 * class for creating its objects
 * java syntax -> Map<Map<Location, LeaderboarType>, List<AirData>>
 * 
 * _______________________________
 * EXAMPLE OUTPUT:
 * {
    "{FF=HIGHEST_AIRQ}": [
        {
            "id": 3,
            "arduinoTime": "13.10.2022",
            "receivedDataDateTime": "2022-10-13T23:46:08",
            "airQuality": 18.74,
            "location": "FF",
            "temperature": 42.00,
            "humidity": 57.55,
            "rndHash": "644cff07-e837-4497-81d2-4ba2eaa6354e",
            "reportedN": 1,
            "invalidData": false
        }
    ],
    "{PdF=HIGHEST_HUM}": [
        {
            "id": 5,
            "arduinoTime": "20.10.2022",
            "receivedDataDateTime": "2022-10-20T18:41:28",
            "airQuality": 11.74,
            "location": "PdF",
            "temperature": 10.00,
            "humidity": 40.55,
            "rndHash": "2d5e7bf1-54f8-4417-9731-c71901de8e12",
            "reportedN": 0,
            "invalidData": false
        },
        {
            "id": 6,
            "arduinoTime": "20.10.2022",
            "receivedDataDateTime": "2022-10-20T18:46:13",
            "airQuality": 11.74,
            "location": "PdF",
            "temperature": 12.00,
            "humidity": 40.55,
            "rndHash": "45eef218-1e48-4ac0-813a-8feb347cabec",
            "reportedN": 0,
            "invalidData": false
        },
        {
            "id": 7,
            "arduinoTime": "20.10.2022",
            "receivedDataDateTime": "2022-10-20T18:57:22",
            "airQuality": 11.74,
            "location": "PdF",
            "temperature": 18.00,
            "humidity": 40.55,
            "rndHash": "7dfbff8d-630d-4df0-bd69-99b51f555e76",
            "reportedN": 0,
            "invalidData": false
        }
    ],
    "{FZV=LOWEST_TEMP}": [
        {
            "id": 1,
            "arduinoTime": "08.10.2022",
            "receivedDataDateTime": "2022-10-13T22:13:32",
            "airQuality": 10.74,
            "location": "FZV",
            "temperature": 4.00,
            "humidity": 87.55,
            "rndHash": "b454d01c-fa90-48a3-afc2-70b1839ad50c",
            "reportedN": 29,
            "invalidData": false
        }
    ],
    "{FF=LOWEST_TEMP}": [
        {
            "id": 3,
            "arduinoTime": "13.10.2022",
            "receivedDataDateTime": "2022-10-13T23:46:08",
            "airQuality": 18.74,
            "location": "FF",
            "temperature": 42.00,
            "humidity": 57.55,
            "rndHash": "644cff07-e837-4497-81d2-4ba2eaa6354e",
            "reportedN": 1,
            "invalidData": false
        }
    ],
    "{PdF=HIGHEST_TEMP}": [
        {
            "id": 5,
            "arduinoTime": "20.10.2022",
            "receivedDataDateTime": "2022-10-20T18:41:28",
            "airQuality": 11.74,
            "location": "PdF",
            "temperature": 10.00,
            "humidity": 40.55,
            "rndHash": "2d5e7bf1-54f8-4417-9731-c71901de8e12",
            "reportedN": 0,
            "invalidData": false
        },
        {
            "id": 6,
            "arduinoTime": "20.10.2022",
            "receivedDataDateTime": "2022-10-20T18:46:13",
            "airQuality": 11.74,
            "location": "PdF",
            "temperature": 12.00,
            "humidity": 40.55,
            "rndHash": "45eef218-1e48-4ac0-813a-8feb347cabec",
            "reportedN": 0,
            "invalidData": false
        },
        {
            "id": 7,
            "arduinoTime": "20.10.2022",
            "receivedDataDateTime": "2022-10-20T18:57:22",
            "airQuality": 11.74,
            "location": "PdF",
            "temperature": 18.00,
            "humidity": 40.55,
            "rndHash": "7dfbff8d-630d-4df0-bd69-99b51f555e76",
            "reportedN": 0,
            "invalidData": false
        }
    ],
    "{FZV=HIGHEST_AIRQ}": [
        {
            "id": 1,
            "arduinoTime": "08.10.2022",
            "receivedDataDateTime": "2022-10-13T22:13:32",
            "airQuality": 10.74,
            "location": "FZV",
            "temperature": 4.00,
            "humidity": 87.55,
            "rndHash": "b454d01c-fa90-48a3-afc2-70b1839ad50c",
            "reportedN": 29,
            "invalidData": false
        }
    ],
    "{PdF=LOWEST_AIRQ}": [
        {
            "id": 4,
            "arduinoTime": "14.10.2022",
            "receivedDataDateTime": "2022-10-14T23:08:15",
            "airQuality": 1.74,
            "location": "PdF",
            "temperature": 10.00,
            "humidity": 40.55,
            "rndHash": "1349b02d-e17e-49ce-ae8a-d74e1b676918",
            "reportedN": 3,
            "invalidData": true
        },
        {
            "id": 2,
            "arduinoTime": "13.10.2022",
            "receivedDataDateTime": "2022-10-13T23:38:28",
            "airQuality": 7.74,
            "location": "PdF",
            "temperature": 40.00,
            "humidity": 47.55,
            "rndHash": "36210d3c-79a6-4d6b-a561-7ecd5c34b9ed",
            "reportedN": 2,
            "invalidData": false
        },
        {
            "id": 5,
            "arduinoTime": "20.10.2022",
            "receivedDataDateTime": "2022-10-20T18:41:28",
            "airQuality": 11.74,
            "location": "PdF",
            "temperature": 10.00,
            "humidity": 40.55,
            "rndHash": "2d5e7bf1-54f8-4417-9731-c71901de8e12",
            "reportedN": 0,
            "invalidData": false
        }
    ],
    "{PdF=LOWEST_HUM}": [
        {
            "id": 4,
            "arduinoTime": "14.10.2022",
            "receivedDataDateTime": "2022-10-14T23:08:15",
            "airQuality": 1.74,
            "location": "PdF",
            "temperature": 10.00,
            "humidity": 40.55,
            "rndHash": "1349b02d-e17e-49ce-ae8a-d74e1b676918",
            "reportedN": 3,
            "invalidData": true
        },
        {
            "id": 2,
            "arduinoTime": "13.10.2022",
            "receivedDataDateTime": "2022-10-13T23:38:28",
            "airQuality": 7.74,
            "location": "PdF",
            "temperature": 40.00,
            "humidity": 47.55,
            "rndHash": "36210d3c-79a6-4d6b-a561-7ecd5c34b9ed",
            "reportedN": 2,
            "invalidData": false
        },
        {
            "id": 5,
            "arduinoTime": "20.10.2022",
            "receivedDataDateTime": "2022-10-20T18:41:28",
            "airQuality": 11.74,
            "location": "PdF",
            "temperature": 10.00,
            "humidity": 40.55,
            "rndHash": "2d5e7bf1-54f8-4417-9731-c71901de8e12",
            "reportedN": 0,
            "invalidData": false
        }
    ],
    "{FF=HIGHEST_TEMP}": [
        {
            "id": 3,
            "arduinoTime": "13.10.2022",
            "receivedDataDateTime": "2022-10-13T23:46:08",
            "airQuality": 18.74,
            "location": "FF",
            "temperature": 42.00,
            "humidity": 57.55,
            "rndHash": "644cff07-e837-4497-81d2-4ba2eaa6354e",
            "reportedN": 1,
            "invalidData": false
        }
    ],
    "{FF=LOWEST_AIRQ}": [
        {
            "id": 3,
            "arduinoTime": "13.10.2022",
            "receivedDataDateTime": "2022-10-13T23:46:08",
            "airQuality": 18.74,
            "location": "FF",
            "temperature": 42.00,
            "humidity": 57.55,
            "rndHash": "644cff07-e837-4497-81d2-4ba2eaa6354e",
            "reportedN": 1,
            "invalidData": false
        }
    ],
    "{FF=LOWEST_HUM}": [
        {
            "id": 3,
            "arduinoTime": "13.10.2022",
            "receivedDataDateTime": "2022-10-13T23:46:08",
            "airQuality": 18.74,
            "location": "FF",
            "temperature": 42.00,
            "humidity": 57.55,
            "rndHash": "644cff07-e837-4497-81d2-4ba2eaa6354e",
            "reportedN": 1,
            "invalidData": false
        }
    ],
    "{FZV=HIGHEST_HUM}": [
        {
            "id": 1,
            "arduinoTime": "08.10.2022",
            "receivedDataDateTime": "2022-10-13T22:13:32",
            "airQuality": 10.74,
            "location": "FZV",
            "temperature": 4.00,
            "humidity": 87.55,
            "rndHash": "b454d01c-fa90-48a3-afc2-70b1839ad50c",
            "reportedN": 29,
            "invalidData": false
        }
    ],
    "{PdF=LOWEST_TEMP}": [
        {
            "id": 4,
            "arduinoTime": "14.10.2022",
            "receivedDataDateTime": "2022-10-14T23:08:15",
            "airQuality": 1.74,
            "location": "PdF",
            "temperature": 10.00,
            "humidity": 40.55,
            "rndHash": "1349b02d-e17e-49ce-ae8a-d74e1b676918",
            "reportedN": 3,
            "invalidData": true
        },
        {
            "id": 2,
            "arduinoTime": "13.10.2022",
            "receivedDataDateTime": "2022-10-13T23:38:28",
            "airQuality": 7.74,
            "location": "PdF",
            "temperature": 40.00,
            "humidity": 47.55,
            "rndHash": "36210d3c-79a6-4d6b-a561-7ecd5c34b9ed",
            "reportedN": 2,
            "invalidData": false
        },
        {
            "id": 5,
            "arduinoTime": "20.10.2022",
            "receivedDataDateTime": "2022-10-20T18:41:28",
            "airQuality": 11.74,
            "location": "PdF",
            "temperature": 10.00,
            "humidity": 40.55,
            "rndHash": "2d5e7bf1-54f8-4417-9731-c71901de8e12",
            "reportedN": 0,
            "invalidData": false
        }
    ],
    "{FF=HIGHEST_HUM}": [
        {
            "id": 3,
            "arduinoTime": "13.10.2022",
            "receivedDataDateTime": "2022-10-13T23:46:08",
            "airQuality": 18.74,
            "location": "FF",
            "temperature": 42.00,
            "humidity": 57.55,
            "rndHash": "644cff07-e837-4497-81d2-4ba2eaa6354e",
            "reportedN": 1,
            "invalidData": false
        }
    ],
    "{PdF=HIGHEST_AIRQ}": [
        {
            "id": 5,
            "arduinoTime": "20.10.2022",
            "receivedDataDateTime": "2022-10-20T18:41:28",
            "airQuality": 11.74,
            "location": "PdF",
            "temperature": 10.00,
            "humidity": 40.55,
            "rndHash": "2d5e7bf1-54f8-4417-9731-c71901de8e12",
            "reportedN": 0,
            "invalidData": false
        },
        {
            "id": 6,
            "arduinoTime": "20.10.2022",
            "receivedDataDateTime": "2022-10-20T18:46:13",
            "airQuality": 11.74,
            "location": "PdF",
            "temperature": 12.00,
            "humidity": 40.55,
            "rndHash": "45eef218-1e48-4ac0-813a-8feb347cabec",
            "reportedN": 0,
            "invalidData": false
        },
        {
            "id": 7,
            "arduinoTime": "20.10.2022",
            "receivedDataDateTime": "2022-10-20T18:57:22",
            "airQuality": 11.74,
            "location": "PdF",
            "temperature": 18.00,
            "humidity": 40.55,
            "rndHash": "7dfbff8d-630d-4df0-bd69-99b51f555e76",
            "reportedN": 0,
            "invalidData": false
        }
    ],
    "{FZV=HIGHEST_TEMP}": [
        {
            "id": 1,
            "arduinoTime": "08.10.2022",
            "receivedDataDateTime": "2022-10-13T22:13:32",
            "airQuality": 10.74,
            "location": "FZV",
            "temperature": 4.00,
            "humidity": 87.55,
            "rndHash": "b454d01c-fa90-48a3-afc2-70b1839ad50c",
            "reportedN": 29,
            "invalidData": false
        }
    ],
    "{FZV=LOWEST_AIRQ}": [
        {
            "id": 1,
            "arduinoTime": "08.10.2022",
            "receivedDataDateTime": "2022-10-13T22:13:32",
            "airQuality": 10.74,
            "location": "FZV",
            "temperature": 4.00,
            "humidity": 87.55,
            "rndHash": "b454d01c-fa90-48a3-afc2-70b1839ad50c",
            "reportedN": 29,
            "invalidData": false
        }
    ],
    "{FZV=LOWEST_HUM}": [
        {
            "id": 1,
            "arduinoTime": "08.10.2022",
            "receivedDataDateTime": "2022-10-13T22:13:32",
            "airQuality": 10.74,
            "location": "FZV",
            "temperature": 4.00,
            "humidity": 87.55,
            "rndHash": "b454d01c-fa90-48a3-afc2-70b1839ad50c",
            "reportedN": 29,
            "invalidData": false
        }
    ]
}
 */
export interface LeaderboardData {
    //java syntax -> Map<Map<Location(enum), LeaderboarType(enum)>, List<AirData>>
    allData: Map<Map<string, string>, AirData[]>;
}   