import { Component, OnInit } from '@angular/core';
import {
  ReactiveFormsModule,
  FormsModule,
  FormGroup,
  FormControl,
  Validators,
  FormBuilder
} from '@angular/forms';
import { newQuizDTO } from '../../../../model/newQuizDTO.model';
import { Question } from '../../../../model/question.model';
import { Answer } from '../../../../model/answer.model';
import { QuizService } from '../../../../service/quiz.service';


@Component({
  selector: 'app-new-quiz',
  templateUrl: './new-quiz.component.html',
  styleUrls: ['../../../../app.component.css']
})
export class NewQuizComponent implements OnInit {
  langs: string[] = [
    'English',
    'French',
    'German',
  ];
  myform: FormGroup;
  name: FormControl; 
  content: FormControl;
  
  constructor(private quizService: QuizService) { }

  ngOnInit() {
    this.createFormControls();
    this.createForm();
  }

  createFormControls() { 
    this.name = new FormControl('', [
      Validators.required,
      Validators.minLength(2),
      Validators.pattern(/^[A-Z][A-Za-z0-9 ]+/)
    ]);
    this.content = new FormControl('', [
      Validators.required,
      Validators.minLength(5)
    ])
  }

  createForm() { 
    this.myform = new FormGroup({
      name: this.name,
      content: this.content
    });
  }

  onSubmit() {
    if (this.myform.valid) {
      console.log("Form Submitted!");
      let questions = this.parseQuestions();
      this.createNewQuiz(new newQuizDTO(this.name.value, questions));

      this.myform.reset();
    } else {
      console.log("Form not valid");
    }

  }

  onReset() {
    this.myform.reset();
  }

  parseQuestions() {
    let lines = this.content.value.split(/\r?\n/);
    let questions: Question[] = [];
    let answers: Answer[] = [];
    
    lines.forEach(line => {
      let hashMarks = line.substr(0, line.indexOf(' '));
      let content = line.substr(line.indexOf(' ') + 1); 

      switch(hashMarks.length) { 
        case 1: {
          answers = [];
          questions.push(new Question(content, null, answers))
          break; 
        } 
        case 2: {
          questions[questions.length - 1].category = content;
          break; 
        } 
        case 3: {
          let answer = new Answer(content, false);
          questions[questions.length - 1].answers.push(answer);
          break;
        }
        case 4: {
          let answer = new Answer(content, true);
          questions[questions.length - 1].answers.push(answer);
          break;
        }
     } 
    })
    return questions;
  }

  createNewQuiz(newQuiz: newQuizDTO) {
    this.quizService.createQuiz(newQuiz)
    .subscribe(
      (error) => console.log(error)
    );
  }


}



