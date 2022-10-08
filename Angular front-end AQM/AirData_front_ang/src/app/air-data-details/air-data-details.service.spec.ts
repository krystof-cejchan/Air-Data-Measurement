import { TestBed } from '@angular/core/testing';

import { AirDataDetailsService } from './air-data-details.service';

describe('AirDataDetailsService', () => {
  let service: AirDataDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AirDataDetailsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
