import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { popUpSnackBar } from 'src/app/utilities/utils';
import { NewsletterSubscriptionService } from '../newsletter-subscription.service';

@Component({
  selector: 'app-cancellation',
  templateUrl: './cancellation.component.html',
  styleUrls: ['./cancellation.component.scss']
})
export class CancellationComponent implements OnInit {

  id: number = this.route.snapshot.params['id'];
  hash: string = this.route.snapshot.params['hash'];

  constructor(private route: ActivatedRoute, private snackBar: MatSnackBar, private service: NewsletterSubscriptionService) {

  }
  ngOnInit(): void {
    if (!this.id || !this.hash) {
      popUpSnackBar(this.snackBar, "Neplatné ID nebo Hash", 5, false);
      return;
    }
  }

  cancel() {
    this.service.deleteSubscriber(this.id, this.hash).subscribe({
      next: () => {
        popUpSnackBar(this.snackBar, "Zrušení proběhlo úspěšně!", 10, true);
      },
      error: () => {
        popUpSnackBar(this.snackBar, "Chyba! Neplatné ID nebo Hash", 10, true);
      }
    });
  }
}
