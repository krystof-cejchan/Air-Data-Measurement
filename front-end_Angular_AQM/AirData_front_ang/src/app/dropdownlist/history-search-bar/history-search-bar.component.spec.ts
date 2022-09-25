import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HistorySearchBarComponent } from './history-search-bar.component';

describe('HistorySearchBarComponent', () => {
  let component: HistorySearchBarComponent;
  let fixture: ComponentFixture<HistorySearchBarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HistorySearchBarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HistorySearchBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
