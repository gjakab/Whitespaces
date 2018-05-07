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

  openModal(type: string, data: Question | Answer, i: number, j: number = null) {
    const modalRef = this.modalService.open(EditDataModalComponent, { centered: true });

    var tmp;
    if (type === this.typesEnum.QUESTION) {
      tmp = data as Question
    } else {
      tmp = data as Answer;
    }
    modalRef.componentInstance.title = (type == this.typesEnum.QUESTION) ? this.typesEnum.QUESTION : this.typesEnum.ANSWER;
    modalRef.componentInstance.defaultValue = (type == this.typesEnum.QUESTION) ? tmp.question : tmp.answer;
    modalRef.componentInstance.trueAnswer = (type == this.typesEnum.QUESTION) ? null : tmp.rightAnswer;

    modalRef.componentInstance.output.subscribe((output) => {
      console.log("OUTPUT", output)
      this.updateData(type, output, i, j);
    });
  }

  updateData(type: string, newData: string[], i: number, j: number = null) {
    if (type === this.typesEnum.QUESTION) {
      this.quiz.questions[i].question = newData[0];
    } else {
      this.quiz.questions[i].answers[j].answer = newData[0];
      this.quiz.questions[i].answers[j].rightAnswer = newData[1] === "Igen" ? true : false;
    }
  }

  deleteQuestion(index: number) {
    this.quiz.questions.splice(index, 1);
  }



}
