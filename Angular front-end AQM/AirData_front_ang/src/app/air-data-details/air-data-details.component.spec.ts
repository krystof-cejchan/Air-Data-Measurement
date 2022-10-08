import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AirDataDetailsComponent } from './air-data-details.component';

describe('AirDataDetailsComponent', () => {
  let component: AirDataDetailsComponent;
  let fixture: ComponentFixture<AirDataDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AirDataDetailsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AirDataDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
