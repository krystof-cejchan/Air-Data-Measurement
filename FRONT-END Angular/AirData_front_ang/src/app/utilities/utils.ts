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
 * open a new snackbar on the current page
 * @param snackBar MatSnackBar reff
 * @param message text to be displayed
 * @param durationInSeconds seconds for which the message will be shown
 * @param withConfirmationButton true=>message can be dismissed earlier than desired seconds; false=>a user won't be able to dismiss the message
 */
export function popUpSnackBar(snackBar: MatSnackBar, message: string, durationInSeconds: number = 5, withConfirmationButton: boolean = true) {
    snackBar.open(message, withConfirmationButton ? 'OK' : undefined, {
        duration: durationInSeconds * 1_000
    });
}