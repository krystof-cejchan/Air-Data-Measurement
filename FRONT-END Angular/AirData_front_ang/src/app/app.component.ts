import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { NewsletterComponent } from './newsletter subscription/newsletter/newsletter.component';
import { CookieService } from "ngx-cookie-service";
import { NgcCookieConsentService } from 'ngx-cookieconsent';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  constructor(private router: Router, public dialog: MatDialog,
    private cookieConsentService: NgcCookieConsentService, public cookieService: CookieService) {

    if (!(this.existsCookie() && this.getCookie() === 'true')) {
      setTimeout(() => {
        if (!window.sessionStorage.getItem('newsletter')) {
          this.dialog.open(NewsletterComponent, {
            width: '600px',
            data: ''
          });
        }
      }, 4_000);
    }
  }

  ngOnInit(): void { }

  setCookie(cookieName: string = NewsletterComponent.COOKIE_NAME_FOR_NEWSLETTER) {
    this.cookieService.set(cookieName, 'true');
  }
  getCookie(cookieName: string = NewsletterComponent.COOKIE_NAME_FOR_NEWSLETTER): string {
    return this.cookieService.get(cookieName);
  }
  existsCookie(cookieName: string = NewsletterComponent.COOKIE_NAME_FOR_NEWSLETTER): boolean {
    return !((this.getCookie(cookieName) === ""));
  }

  getHamburger() {
    return "hamburger_" + (this.router.url === '/' ? 'white' : 'black')
  }
}
