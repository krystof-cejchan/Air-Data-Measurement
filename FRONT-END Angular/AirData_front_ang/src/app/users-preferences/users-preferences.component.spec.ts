import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsersPreferencesComponent } from './users-preferences.component';

describe('UsersPreferencesComponent', () => {
  let component: UsersPreferencesComponent;
  let fixture: ComponentFixture<UsersPreferencesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UsersPreferencesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UsersPreferencesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
