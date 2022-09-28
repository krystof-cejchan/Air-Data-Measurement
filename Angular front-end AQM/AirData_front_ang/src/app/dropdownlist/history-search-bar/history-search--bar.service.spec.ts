import { TestBed } from '@angular/core/testing';

import { HistorySearchBarService } from './history-search--bar.service';

describe('HistorySearchBarService', () => {
  let service: HistorySearchBarService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HistorySearchBarService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
