import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AstrologHome } from './astrolog-home';

describe('AstrologHome', () => {
  let component: AstrologHome;
  let fixture: ComponentFixture<AstrologHome>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AstrologHome],
    }).compileComponents();

    fixture = TestBed.createComponent(AstrologHome);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
