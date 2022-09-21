import { TestBed } from '@angular/core/testing';

import { AirDataService } from './air-data.service';

describe('AirDataService', () => {
  let service: AirDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AirDataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
