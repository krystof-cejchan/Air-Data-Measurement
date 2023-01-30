import { LocationData } from "./LocationData";

/**
 * class for creating its objects
 */
export interface AirDataAverageForDay {
    id: number;
    location: LocationData;
    receivedDataDate: string;
    airQualityAvg: number;
    temperatureAvg: number;
    humidityAvg: number;
}   
