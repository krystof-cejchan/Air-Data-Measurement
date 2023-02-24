import { Component, OnInit } from "@angular/core";
import { FormControl, Validators } from "@angular/forms";
import { MatSnackBar } from "@angular/material/snack-bar";
import { CookieService } from "ngx-cookie-service";
import { popUpSnackBar } from "src/app/utilities/utils";
import { NewsletterService } from "../newsletter/newsletter.service";
import { NewsletterComponent } from '../newsletter/newsletter.component';


@Component({
  selector: 'app-manager',
  templateUrl: './manager.component.html',
  styleUrls: ['./manager.component.scss']
})
export class ManagerComponent implements IComponent, OnInit {
  newsletterForm!: FormControl;

  getTitle(): string {
    return 'Správce odběru novinek'
  }

  constructor(private cookieService: CookieService,
    private snackBar: MatSnackBar,
    private newsletterService: NewsletterService) { }

  ngOnInit(): void {
    this.newsletterForm = new FormControl('', [
      Validators.required,
      Validators.email,
    ]);
  }


  subscribe() {
    const formValue = this.newsletterForm.value;

    if (!this.newsletterForm.valid) {
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

}
