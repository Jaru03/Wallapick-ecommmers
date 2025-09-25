import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Opinions } from './opinions';

describe('Opinions', () => {
  let component: Opinions;
  let fixture: ComponentFixture<Opinions>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Opinions]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Opinions);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
