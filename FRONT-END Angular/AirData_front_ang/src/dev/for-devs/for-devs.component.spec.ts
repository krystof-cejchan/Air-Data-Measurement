import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ForDevsComponent } from './for-devs.component';

describe('ForDevsComponent', () => {
  let component: ForDevsComponent;
  let fixture: ComponentFixture<ForDevsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ForDevsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ForDevsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
