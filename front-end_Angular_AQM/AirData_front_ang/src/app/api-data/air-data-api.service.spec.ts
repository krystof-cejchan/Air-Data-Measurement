import { TestBed } from '@angular/core/testing';

import { AirDataApiService } from './air-data-api.service';

describe('AirDataAPIService', () => {
  let service: AirDataApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AirDataApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
