import { LocationData } from "./objects/LocationData";

export interface AirData {
    id: number;
    locationId: LocationData;
    arduinoTime: string;
    receivedDataDateTime: string;
    airQuality: number;
    temperature: number;
    humidity: number;
    rndHash: string;
    reportedN: number;
    invalidData:boolean;


    //beyond java back-end object properties =>
    bcgPictureUrl: string;
}   