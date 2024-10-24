import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TraderEditFormComponent } from './trader-edit-form.component';

describe('TraderEditFormComponent', () => {
  let component: TraderEditFormComponent;
  let fixture: ComponentFixture<TraderEditFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TraderEditFormComponent]
    });
    fixture = TestBed.createComponent(TraderEditFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
