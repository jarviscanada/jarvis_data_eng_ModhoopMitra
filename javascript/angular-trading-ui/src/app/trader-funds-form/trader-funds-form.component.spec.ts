import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TraderFundsFormComponent } from './trader-funds-form.component';

describe('TraderFundsFormComponent', () => {
  let component: TraderFundsFormComponent;
  let fixture: ComponentFixture<TraderFundsFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TraderFundsFormComponent]
    });
    fixture = TestBed.createComponent(TraderFundsFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
