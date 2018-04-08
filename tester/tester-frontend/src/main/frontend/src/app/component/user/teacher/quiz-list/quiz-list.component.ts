import { Component, OnInit } from '@angular/core';
import { Quiz } from '../../../../model/quiz.model';
import { QuizService } from '../../../../service/quiz.service';
import { subscribeOn } from 'rxjs/operator/subscribeOn';

import * as moment from 'moment';

@Component({
  selector: 'app-quiz-list',
  templateUrl: './quiz-list.component.html',
  styleUrls: ['./quiz-list.component.css']
})
export class QuizListComponent implements OnInit {
  private quizzes: Quiz[];

  constructor(private quizService: QuizService) { }

  ngOnInit() {
    this.quizService.getQuizzesOfUser()
      .subscribe(
        (quizzes: Quiz[]) => this.quizzes = quizzes,
        (error) => console.log(error)
      )
      console.log(moment().toDate())
  }

  getDate(date: Date) {
    return moment(date).format('YYYY/MM/DD');
  }
}
