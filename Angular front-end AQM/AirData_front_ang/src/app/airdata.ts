export interface AirData {
    id: number;
    location: string;
    arduinoTime: string;
    receivedDataDateTime: string;
    airQuality: number;
    temperature: number;
    humidity: number;
    rndHash: string;

    //beyond java back-end object properties =>
    bcgPictureUrl: string;
}   