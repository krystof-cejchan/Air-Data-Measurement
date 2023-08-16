import { MatSnackBar } from "@angular/material/snack-bar";
/**
 * rounds a number to decimal number with decimal points as precision
 * @param value number to be rounded
 * @param precision number of decimal points
 * @returns rounded number
 */
export function round(value: number, precision: number): number {
    const MULTIPLIER = Math.pow(10, precision || 0);
    return Math.round(value * MULTIPLIER) / MULTIPLIER;
}

export function uniqByFilter<T>(array: T[]) {
    return array.filter((value, index) => array.indexOf(value) === index);
}
/**
 * converts celsius to fahrenheit
 * @param celsius input value in °C
 * @returns fahrenheit calculated from calsius input value
 */
export function convertCelsiusToFahrenheit(celsius: number): number {
    return (celsius * 9 / 5) + 32;
}
/**
 * converts fahrenheit to celsisu
 * @param fahrenheit input value in °F
 * @returns celsisu calculated from fahrenheit input value
 */
export function convertFahrenheitToCelsius(fahrenheit: number): number {
    return (fahrenheit - 32) * 5 / 9;
}

/**
 * open a new snackbar on the current page
 * @param snackBar MatSnackBar reff
 * @param message text to be displayed
 * @param durationInSeconds seconds for which the message will be shown | if undefined, then 5
 * @param withConfirmationButton true=>message can be dismissed earlier than desired seconds; false=>a user won't be able to dismiss the message | if undefined, then true
 */
export function popUpSnackBar(snackBar: MatSnackBar, message: string, durationInSeconds: number = 5, withConfirmationButton: boolean = true) {
    snackBar.open(message, withConfirmationButton ? 'OK' : undefined, {
        duration: durationInSeconds * 1_000
    });
}