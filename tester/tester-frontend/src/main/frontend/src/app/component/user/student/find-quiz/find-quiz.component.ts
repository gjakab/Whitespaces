import { Component, OnInit, ViewEncapsulation, ViewChild } from '@angular/core';
import { DatatableComponent } from '@swimlane/ngx-datatable';
import { Quiz } from '../../../../model/quiz.model';
import { QuizService } from '../../../../service/quiz.service';

import * as moment from 'moment';

@Component({
  selector: 'app-quiz-list',
  templateUrl: './find-quiz.component.html',
  styleUrls: [
    '../../../../app.component.css',
    '../../../../../../node_modules/@swimlane/ngx-datatable/release/themes/bootstrap.css',
    '../../../../../../node_modules/@swimlane/ngx-datatable/release/index.css',
    '../../../../../../node_modules/@swimlane/ngx-datatable/release/assets/icons.css'
  ],
  encapsulation: ViewEncapsulation.None
})
export class FindQuizComponent implements OnInit {
  @ViewChild(DatatableComponent) table: DatatableComponent;
  private quizzes: Quiz[];
  private messages = {
    emptyMessage: `
      <div class="mt-15 mb-15 text-center">
        Nincs a keresésnek megfelelő quiz
      </div>
    `
  }
  temp: Quiz[];

  constructor(private quizService: QuizService) { }

  getDate(date: Date) {
    return moment(date).format('YYYY/MM/DD');
  }

  updateFilter(event) {
    const value = event.target.value.toLowerCase();

    const temp = this.temp.filter(function(quiz) {
      return quiz.name.toLowerCase().indexOf(value) !== -1 || !value;
    });

    this.quizzes = temp;
    this.table.offset = 0;
  }

  ngOnInit() {
    this.quizService.getAvailableQuizzesForUser()
      .subscribe(
        (quizzes: Quiz[]) => {
          this.quizzes = quizzes;
          this.temp = [...quizzes];
          console.log("RESPONSE", quizzes)
          },
        (error) => console.log(error)
      )
  }

}
