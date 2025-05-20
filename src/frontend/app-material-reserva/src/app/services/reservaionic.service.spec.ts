import { TestBed } from '@angular/core/testing';

import { ReservaionicService } from './reservaionic.service';

describe('ReservaionicService', () => {
  let service: ReservaionicService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReservaionicService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
