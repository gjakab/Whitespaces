import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FindQuizComponent } from './find-quiz.component';

describe('FindQuizComponent', () => {
  let component: FindQuizComponent;
  let fixture: ComponentFixture<FindQuizComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FindQuizComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FindQuizComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
