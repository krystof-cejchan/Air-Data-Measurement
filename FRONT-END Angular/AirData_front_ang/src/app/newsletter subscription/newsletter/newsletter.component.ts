import { Component, OnInit } from "@angular/core";
import { FormControl, Validators } from "@angular/forms";
import { MatDialogRef } from "@angular/material/dialog";
import { CookieService } from "ngx-cookie-service";
import { SnackBarErrorComponent } from "../../errors/custom in-page errors/snack-bar/cannot_report_airdata/custom-error-snackbar";
import { popUpSnackBar } from "../../utilities/utils";
import { MatSnackBar } from "@angular/material/snack-bar";
import { NewsletterService } from "./newsletter.service";

@Component({
  selector: 'app-newsletter',
  templateUrl: './newsletter.component.html',
  styleUrls: ['./newsletter.component.scss']
})
export class NewsletterComponent implements OnInit {

  newsletterForm!: FormControl;

  public static readonly COOKIE_NAME_FOR_NEWSLETTER: string = "newsletter"

  constructor(private dialogRef: MatDialogRef<SnackBarErrorComponent, unknown>,
    private cookieService: CookieService,
    private snackBar: MatSnackBar,
    private newsletterService: NewsletterService) { }

  ngOnInit(): void {

    this.newsletterForm = new FormControl('', [
      Validators.required,
      Validators.email,
    ]);
  }

  public closeDialog(remindLater: boolean = true) {
    if (!remindLater)
      this.setCookie()

    this.dialogRef.close();
  }

  subscribe() {
    const expression: RegExp = /^(?=.{1,254}$)(?=.{1,64}@)[-!#$%&'*+/0-9=?A-Z^_`a-z{|}~]+(\.[-!#$%&'*+/0-9=?A-Z^_`a-z{|}~]+)*@[A-Za-z0-9]([A-Za-z0-9-]{0,61}[A-Za-z0-9])?(\.[A-Za-z0-9]([A-Za-z0-9-]{0,61}[A-Za-z0-9])?)*$/;
    const formValue = this.newsletterForm.value;

    if (!this.newsletterForm.valid && !expression.test(formValue)) {
      popUpSnackBar(this.snackBar, "Nastala chyba při ověřování e-mailu. Zkuste to, prosím, znovu.", 4, true);
      return;
    }
    this.newsletterService.addNewNotifier(formValue).subscribe({
      next: () => {
        this.setCookie()
        popUpSnackBar(this.snackBar, "Úspěch! ― potvrďte odběr ve vaší e-mailové schránce.", 8, true);
      },
      error: () => {
        popUpSnackBar(this.snackBar, "Došlo k chybě při přihlašování se k odběru novinek", 4, true);
      }
    })


  }

  setCookie(cookieName: string = NewsletterComponent.COOKIE_NAME_FOR_NEWSLETTER) {
    this.cookieService.set(cookieName, 'true');
  }
  getCookie(cookieName: string = NewsletterComponent.COOKIE_NAME_FOR_NEWSLETTER): string {
    return this.cookieService.get(cookieName);
  }
  existsCookie(cookieName: string = NewsletterComponent.COOKIE_NAME_FOR_NEWSLETTER): boolean {
    return !((this.getCookie(cookieName) === ""));
  }
}
