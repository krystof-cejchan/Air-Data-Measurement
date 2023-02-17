import { TestBed } from '@angular/core/testing';

import { NewsletterSubscriptionService } from './newsletter-subscription.service';

describe('NewsletterSubscriptionService', () => {
  let service: NewsletterSubscriptionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NewsletterSubscriptionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
