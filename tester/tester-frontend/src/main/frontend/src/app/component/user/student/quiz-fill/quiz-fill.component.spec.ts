import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuizFillComponent } from './quiz-fill.component';

describe('QuizFillComponent', () => {
  let component: QuizFillComponent;
  let fixture: ComponentFixture<QuizFillComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuizFillComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuizFillComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
