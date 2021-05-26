import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BnfSearchComponent } from './bnf-search.component';

describe('BnfSearchComponent', () => {
  let component: BnfSearchComponent;
  let fixture: ComponentFixture<BnfSearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BnfSearchComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BnfSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
