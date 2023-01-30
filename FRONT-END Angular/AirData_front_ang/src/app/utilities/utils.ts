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