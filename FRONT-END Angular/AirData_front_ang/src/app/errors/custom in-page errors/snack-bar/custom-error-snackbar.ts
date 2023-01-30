import { Component } from "@angular/core";
import { MatSnackBar } from "@angular/material/snack-bar";

export function openSnackBar(snackBar: MatSnackBar, durationInSeconds: number = 10) {
    snackBar.openFromComponent(SnackBarErrorComponent, {
        duration: durationInSeconds * 1000,
    });
}


@Component({
    selector: 'snack-bar-component-example-snack',
    templateUrl: './custom-error-snackbar.html',
    styles: [
        `
    .snackbar-text {
      color: white;
    }
  `,
    ],
})
export class SnackBarErrorComponent { }
