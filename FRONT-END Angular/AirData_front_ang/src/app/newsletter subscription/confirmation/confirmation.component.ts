import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MatSnackBar } from "@angular/material/snack-bar";
import { popUpSnackBar } from 'src/app/utilities/utils';
import { NewsletterSubscriptionService } from "../newsletter-subscription.service";

@Component({
  selector: 'app-confirmation',
  templateUrl: './confirmation.component.html',
  styleUrls: ['./confirmation.component.scss']
})
export class ConfirmationComponent implements OnInit {
  id = this.route.snapshot.params['id'];
  hash = this.route.snapshot.params['hash'];

  constructor(private route: ActivatedRoute, private snackBar: MatSnackBar, private service: NewsletterSubscriptionService) { }
  ngOnInit(): void {
    if (!this.id || !this.hash) {
      popUpSnackBar(this.snackBar, "Neplatné ID nebo Hash", 5, false);
      return;
    }
  }

  confirm() {
    this.service.confirmSubscriber(this.id, this.hash).subscribe({
      next: () => {
        popUpSnackBar(this.snackBar, "Potvrzení proběhlo úspěšně!", 10, true);
      },
      error: () => {
        popUpSnackBar(this.snackBar, "Chyba! Neplatné ID nebo Hash", 10, true);
      }
    });
  }

}
