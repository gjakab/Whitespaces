import { Component, OnInit } from '@angular/core';
import {
  ReactiveFormsModule,
  FormsModule,
  FormGroup,
  FormControl,
  Validators,
  FormBuilder
} from '@angular/forms';

import { NewQuizDTO } from '../../../../model/newQuizDTO.model';
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
  error: boolean = false;
  success: boolean = false;
  successText: string = "Az új quiz bekerült az adatbázisba!";
  errorText: string = 'Nem megfelelő az adat(ok) formátuma vagy már van ilyen nevű teszt!';
  regexp: RegExp = new RegExp(/^[#]+$/)
  
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
    this.error = false;
    this.success = false;
    if (this.myform.valid) {      
      try {
        let questions = this.parseQuestions();
        this.createNewQuiz(new NewQuizDTO(this.name.value, questions));
        console.log("Form Submitted!");
      } catch (e) {
        console.log("Form not valid2");
        this.error = true;
      }
    } else {
      console.log("Form not valid1");
      this.error = true;
    }
  }

  onReset() {
    this.error = false;
    this.success = false;
    this.myform.reset();
  }

  parseQuestions() {
    let lines = this.content.value.split(/\r?\n/);
    let questions: Question[] = [];
    let answers: Answer[] = [];

    lines.forEach(line => {
      let hashMarks = line.substr(0, line.indexOf(' '));
      let content = line.substr(line.indexOf(' ') + 1); 

      if (content.trim().length === 0 || hashMarks.trim().length === 0 || !this.regexp.test(hashMarks)) {
        throw new Error();
      }

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
        default: {
          throw new Error();
        }
     } 
    })
    return questions;
  }

  createNewQuiz(newQuiz: NewQuizDTO) {
    this.quizService.createQuiz(newQuiz)
    .subscribe(
      (response) => {
        this.error = false;
        this.success = true;
        this.myform.reset();
      },
      (error) => { 
        this.error = true;
        this.success = false;
      }
    );
  }


}



