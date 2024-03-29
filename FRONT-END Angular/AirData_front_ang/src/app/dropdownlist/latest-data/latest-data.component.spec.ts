import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LatestDataComponent } from './latest-data.component';

describe('LatestDataComponent', () => {
  let component: LatestDataComponent;
  let fixture: ComponentFixture<LatestDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LatestDataComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LatestDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
