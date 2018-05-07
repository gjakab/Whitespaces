import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import {
  ReactiveFormsModule,
  FormsModule,
  FormGroup,
  FormControl,
  Validators,
  FormBuilder,
  FormArray
} from '@angular/forms';

import { Quiz } from '../../../../model/quiz.model';
import { QuizService } from '../../../../service/quiz.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { EditDataModalComponent } from '../../edit-data-modal/edit-data-modal.component';
import { Question } from '../../../../model/question.model';
import { Answer } from '../../../../model/answer.model';
import { QuestionService } from '../../../../service/question.service';

@Component({
  selector: 'app-view-quiz',
  templateUrl: './view-quiz.component.html',
  styleUrls: ['../../../../app.component.css']
})
export class ViewQuizComponent implements OnInit {
  quizId: number;
  quiz: Quiz;
  typesEnum = Object.freeze({"QUESTION": "Kérdés", "ANSWER": "Válasz"})

  constructor(
    private quizService: QuizService,
    private questionService: QuestionService,
    private activatedRoute: ActivatedRoute,
    private modalService: NgbModal
  ) { }

  ngOnInit() {
    this.quizId = parseInt(this.activatedRoute.snapshot.paramMap.get('quizId'));
    this.quizService.getQuizById(this.quizId)
    .subscribe(
      (quiz: Quiz) => {
        console.log("QUIZ", quiz);
        this.quiz = quiz;
      },
      (error) => console.log(error)
    );
  }

  openModal(question: Question, answer: Answer, i: number, j: number = null) {
    const modalRef = this.modalService.open(EditDataModalComponent, { centered: true });

    modalRef.componentInstance.title = (answer === null) ? this.typesEnum.QUESTION : this.typesEnum.ANSWER;
    modalRef.componentInstance.defaultCategory = (answer === null) ? question.category : null;
    modalRef.componentInstance.defaultValue = (answer === null) ? question.question : answer.answer;
    modalRef.componentInstance.trueAnswer = (answer === null) ? null : answer.rightAnswer;

    modalRef.componentInstance.output.subscribe((output) => {
      console.log("OUTPUT", output)
      this.updateData(question, answer, output, i , j);
    });
  }

  updateData(question: Question, answer: Answer, newData: string[], i: number, j: number) {
    if (answer === null) {
      this.quiz.questions[i].question = newData[0];
      this.updateQuestion(question.id, newData[0], newData[1]);
    } else {
      this.quiz.questions[i].answers[j].answer = newData[0];
      this.quiz.questions[i].answers[j].rightAnswer = newData[2] === "Igen" ? true : false;
    }
  }

  updateQuestion(id: number, question: string, category: string) {
    this.questionService.updateQuestion(this.quizId, new Question(question, null, null, id))
  }

  deleteQuestion(index: number, questionId: number) {
    this.quiz.questions.splice(index, 1);
    
/*     this.questionService.deleteQuestion(this.quizId, questionId).subscribe(() => {
      this.quiz.questions.splice(index, 1);
    }); */
  }

  deleteAnswer(i: number, j: number, questionId: number, answerId: number) {
    this.quiz.questions[i].answers.splice(j, 1);
    
/*     this.questionService.deleteQuestion(this.quizId, questionId).subscribe(() => {
      this.quiz.questions.splice(index, 1);
    }); */
  }



}
