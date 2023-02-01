import { Component } from "@angular/core";
import { MatSnackBar } from "@angular/material/snack-bar";

export function openCustomSnackBar(snackBar: MatSnackBar, durationInSeconds: number = 5, message: string) {
  snackBar.openFromComponent(SnackBarErrorComponent, {
    duration: durationInSeconds * 1000,
    announcementMessage: message,
    data:message
  });
}


@Component({
  selector: 'snack-bar-component-example-snack',
  template: '',
  styles: [
    `
    .snackbar-text {
      color: #ffffff;
    }
  `,
  ],
})
export class SnackBarErrorComponent { }
