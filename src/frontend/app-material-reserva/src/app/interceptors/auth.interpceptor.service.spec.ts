import { TestBed } from '@angular/core/testing';

import { AuthInterpceptorService } from './auth.interpceptor.service';

describe('AuthInterpceptorService', () => {
  let service: AuthInterpceptorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AuthInterpceptorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
