import { TestBed } from '@angular/core/testing';

import { QuoteListService } from './quote-list.service';

describe('QuoteListService', () => {
  let service: QuoteListService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(QuoteListService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
